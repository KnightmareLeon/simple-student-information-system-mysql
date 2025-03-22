package main.app.tables.tableModels;

import main.database.DatabaseDriver;

/**
 * A {@link DatabaseHandlingTableModel} that handles {@link main.data.dataClass.Student 
 * Students}' data. Its designated file is "students.csv".
 * <p>
 * It has six columns: "ID", "First Name", "Last Name", "Year Level", "Gender" 
 * ,and "Program Code"
 */
public class StudentTableModel extends DatabaseHandlingTableModel{
    public StudentTableModel(DatabaseDriver dbDriver){
        this.setColumnCount(6);
        this.setTableName("students");
        this.setColumnIdentifiers(new String[]{
            "ID",
            "First Name",
            "Last Name",
            "Year Level",
            "Gender",
            "Program Code"});
        this.getData(dbDriver);
    }

}
