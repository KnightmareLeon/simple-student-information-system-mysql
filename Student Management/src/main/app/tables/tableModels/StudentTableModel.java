package main.app.tables.tableModels;

import main.data.maps.DataMap;

/**
 * A {@link CSVHandlingTableModel} that handles {@link main.data.dataClass.Student 
 * Students}' data. Its designated file is "students.csv".
 * <p>
 * It has six columns: "ID", "First Name", "Last Name", "Year Level", "Gender" 
 * ,and "Program Code"
 */
public class StudentTableModel extends CSVHandlingTableModel{
    public StudentTableModel(DataMap dMap){
        this.setColumnCount(6);
        this.setFileName("students.csv");
        this.setColumnIdentifiers(new String[]{
            "ID",
            "First Name",
            "Last Name",
            "Year Level",
            "Gender",
            "Program Code"});
        this.getData(dMap);
    }

    @Override
    public String reformatData(String[] data){return "\n" + data[0] + "," + data[1] + "," + data[2] + "," +  data[3] + "," + data[4] + "," +  data[5];}

    @Override
    public void addToMap(String[] data, DataMap dMap) {dMap.addStudent(data);}

    @Override
    public void deleteFromMap(String code, DataMap dMap){dMap.removeStudent(code);}

    @Override
    public void editDataOnMap(String prevCode, String[] newData, DataMap dMap){dMap.editStudent(prevCode, newData);}

    @Override
    protected void multiEditDataOnMap(String[] keys, String[] newData, DataMap dMap) {dMap.editMultiStudent(keys, newData);}
}
