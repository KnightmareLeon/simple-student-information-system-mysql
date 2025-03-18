package main.app.tables.tableModels;

import main.data.maps.DataMap;


/**
 * A {@link CSVHandlingTableModel} that handles {@link main.data.dataClass.Program 
 * Programs}' data. Its designated file is "programs.csv". It implements 
 * {@link OtherTableModelEditor}, with the {@code CSVHandlingTableModel} that it edits is 
 * the {@link StudentTableModel}.
 * <p>
 * It has three columns: "Code", "Name", and "College Code"
 * 
 */
public class ProgramTableModel extends CSVHandlingTableModel implements OtherTableModelEditor{
    private StudentTableModel stm;

    public ProgramTableModel(DataMap dMap){
        this.setColumnCount(3);
        this.setFileName("programs.csv");
        this.setColumnIdentifiers(new String[]{
            "Code",
            "Name",
            "College Code"});
        this.getData(dMap);
    }

    @Override
    public String reformatData(String[] data){return "\n" + data[0] + "," + data[1] + "," + data[2];}

    @Override
    public void addToMap(String[] data, DataMap dMap) {dMap.addProgram(data);}

    @Override
    public void deleteFromMap(String code, DataMap dMap){
        int stdSize = dMap.getProgram(code).getTotalStudents();
        dMap.removeProgram(code);
        if(stdSize > 0){this.deleteFromOtherTableModel(code);}
    }

    @Override
    public void editDataOnMap(String prevCode, String[] newData, DataMap dMap) {
        dMap.editProgram(prevCode, newData);
        if(!prevCode.equals(newData[0])){this.editOtherTableModel(prevCode, newData[0]);}
    }

    @Override
    public void editOtherTableModel(String prevData, String newData){
        for(int row = 0; row < this.stm.getRowCount(); row++){
            if(((String)this.stm.getValueAt(row, 5)).equals(prevData)){
                this.stm.setValueAt(newData, row, 5);
            }
        }
    }

    @Override
    protected void multiEditDataOnMap(String[] keys, String[] newData, DataMap dMap) {dMap.editMultiProgram(keys, newData[0]);}

    @Override
    public void deleteFromOtherTableModel(String code) {
        int rowCount = this.stm.getRowCount();
        for(int row = 0; row < rowCount; row++){
            if(((String)this.stm.getValueAt(row, 5)).equals(code)){
                this.stm.removeRow(row);
                row--; rowCount--;
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
