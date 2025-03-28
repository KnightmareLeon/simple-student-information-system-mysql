package main.app.tables.tableModels;

import java.sql.SQLException;

import main.database.DatabaseDriver;

/**
 * A {@lnk CSVHandlingTableModel} that handles {@link main.data.dataClass.College Colleges}'
 * data. Its designated file is "colleges.csv". It implements {@link OtherTableModelEditor}, 
 * with the {@code CSVHandlingTableModel} that it edits is the {@link ProgramTableModel}.
 * <p>
 * It can also indirectly edit the {@link StudentTableModel} via the 
 * {@code ProgramTableModel}. It can only do this using the 
 * {@link #deleteFromOtherTableModel(String) deleteFromOtherTable} method.
 * <p>
 * It has two columns: "Code" and "Name"
 * 
 */
public class CollegeTableModel extends DatabaseHandlingTableModel implements OtherTableModelEditor{
    private ProgramTableModel ptm;
    public CollegeTableModel(DatabaseDriver dbDriver){
        super(dbDriver);
        this.setColumnCount(2);
        this.setTableName("colleges");
        this.setColumnIdentifiers(new String[]{
            "Code",
            "Name"});
        this.getData();
    }

    @Override
    public void deleteData(int row) throws SQLException {
        String code = (String) this.getValueAt(row, 0);
        super.deleteData(row);
        this.editOtherTableModel(code, "NULL");
    }

    @Override
    public void editData(int row, String[] newData) throws SQLException{
        String prevCode = (String) this.getValueAt(row, 0);
        super.editData(row, newData);
        if(!prevCode.equals(newData[0])){
            this.editOtherTableModel(prevCode, newData[0]);
        }
        
    }

    @Override
    public void editOtherTableModel(String prevData, String newData) {
        for(int row = 0; row < this.ptm.getRowCount(); row++){
            if(((String)this.ptm.getValueAt(row, 2)).equals(prevData)){
                this.ptm.setValueAt(newData, row, 2);
            }
        }
    }


    /**
     * Used to set the {@link ProgramTableModel} object that the {@code
     * CollegeTableModel} will be editing. Should only be done after
     * the {@code ProgramTableModel} object has already been initialized in 
     * the {@link main.app.tables.ManagementTable ManagementTable}.
     * @param ptm - the {@code ProgramTableModel} that will be set.
     */
    public void setPTM(ProgramTableModel ptm){this.ptm = ptm;}

}
