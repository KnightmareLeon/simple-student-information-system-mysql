package main.app.tables.tableModels;

import main.database.DatabaseDriver;

/**
 * A {@link DatabaseHandlingTableModel} that handles data for the students table for
 * this app's designated MySQL database. It has six columns: "ID", "First Name", "Last Name", 
 * "Year Level", "Gender", and "Program Code"
 */
public class StudentTableModel extends DatabaseHandlingTableModel{
    public StudentTableModel(DatabaseDriver dbDriver){
        super(dbDriver);
        this.setColumnCount(6);
        this.setTableName("students");
        this.setColumnIdentifiers(new String[]{
            "ID",
            "First Name",
            "Last Name",
            "Year Level",
            "Gender",
            "Program Code"});
        this.setSortingOptions(new String[]{"ID ASC"});
        this.getData();
    }

}
