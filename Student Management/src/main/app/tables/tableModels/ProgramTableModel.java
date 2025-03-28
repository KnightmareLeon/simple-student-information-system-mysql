package main.app.tables.tableModels;

import java.sql.SQLException;

import main.database.DatabaseDriver;


/**
 * A {@link DatabaseHandlingTableModel} that handles {@link main.data.dataClass.Program 
 * Programs}' data. Its designated file is "programs.csv". It implements 
 * {@link OtherTableModelEditor}, with the {@code CSVHandlingTableModel} that it edits is 
 * the {@link StudentTableModel}.
 * <p>
 * It has three columns: "Code", "Name", and "College Code"
 * 
 */
public class ProgramTableModel extends DatabaseHandlingTableModel implements OtherTableModelEditor{
    private StudentTableModel stm;

    public ProgramTableModel(DatabaseDriver dbDriver){
        super(dbDriver);
        this.setColumnCount(3);
        this.setTableName("programs");
        this.setColumnIdentifiers(new String[]{
            "Code",
            "Name",
            "College Code"});
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
    public void editOtherTableModel(String prevData, String newData){
        for(int row = 0; row < this.stm.getRowCount(); row++){
            if(((String)this.stm.getValueAt(row, 5)).equals(prevData)){
                this.stm.setValueAt(newData, row, 5);
            }
        }
    }

    /**
     * Used to set the {@link StudentTableModel} object that the {@code
     * ProgramTableModel} will be editing. Should only be done after
     * the {@code StudentTableModel} object has already been initialized in 
     * the {@link main.app.tables.ManagementTable ManagementTable}.
     * @param stm - the {@code StudentTableModel} that will be set.
     */
    public void setSTM(StudentTableModel stm){this.stm = stm;}

    

    
}
