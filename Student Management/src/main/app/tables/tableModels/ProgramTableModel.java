package main.app.tables.tableModels;

import main.database.DatabaseDriver;

/**
 * A {@link DatabaseHandlingTableModel} that handles data for the students table for
 * this app's designated MySQL database. It has three columns: "Code", "Name", and "College Code"
 */
public class ProgramTableModel extends DatabaseHandlingTableModel{
    public ProgramTableModel(DatabaseDriver dbDriver){
        super(dbDriver);
        this.setColumnCount(3);
        this.setTableName("programs");
        this.setColumnIdentifiers(new String[]{
            "Code",
            "Name",
            "College Code"});
        this.setSortingOptions(new String[]{"Code ASC"});
        this.getData();
    }
    
}
