package main.app.tables.tableModels;

import main.data.maps.DataMap;

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
public class CollegeTableModel extends CSVHandlingTableModel implements OtherTableModelEditor{
    private ProgramTableModel ptm;
    public CollegeTableModel(DataMap dMap){
        this.setColumnCount(2);
        this.setFileName("colleges.csv");
        this.setColumnIdentifiers(new String[]{
            "Code",
            "Name"});
        this.getData(dMap);
    }

    @Override
    public String reformatData(String[] data){return "\n" + data[0] + "," + data[1];}

    @Override
    public void addToMap(String[] data, DataMap dMap){dMap.addCollege(data);}

    @Override
    public void deleteFromMap(String code, DataMap dMap){
        int prgSize = dMap.getCollege(code).getTotalProgram();
        dMap.removeCollege(code);
        if(prgSize > 0){this.deleteFromOtherTableModel(code);}
    }

    @Override
    public void editDataOnMap(String prevCode, String[] newData, DataMap dMap) {
        dMap.editCollege(prevCode, newData);
        if(!prevCode.equals(newData[0])){this.editOtherTableModel(prevCode, newData[0]);}
    }

    @Override
    public void editOtherTableModel(String prevData, String newData) {
        for(int row = 0; row < this.ptm.getRowCount(); row++){
            if(((String)this.ptm.getValueAt(row, 2)).equals(prevData)){
                this.ptm.setValueAt(newData, row, 2);
            }
        }
    }

    @Override
    public void deleteFromOtherTableModel(String code) {
        int rowCount = this.ptm.getRowCount();
        for(int row = 0; row < rowCount; row++){
            if(((String)this.ptm.getValueAt(row, 2)).equals(code)){
                this.ptm.deleteFromOtherTableModel((String)this.ptm.getValueAt(row, 0));
                this.ptm.removeRow(row);
                row--; rowCount--;
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

    @Override
    protected void multiEditDataOnMap(String[] keys, String[] newData, DataMap dMap) {
        throw new UnsupportedOperationException("Unimplemented method 'multiEditDataOnMap'");
    }

}
