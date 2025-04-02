package main.app.tables.tableModels;

import main.database.DatabaseDriver;

/**
 * A {@link DatabaseHandlingTableModel} that handles data for the colleges table for
 * this app's designated MySQL database. It has two columns: "Code" and "Name"
 * 
 */
public class CollegeTableModel extends DatabaseHandlingTableModel{
    public CollegeTableModel(DatabaseDriver dbDriver){
        super(dbDriver);
        this.setColumnCount(2);
        this.setTableName("colleges");
        this.setColumnIdentifiers(new String[]{
            "Code",
            "Name"});
            this.setSortingOptions(new String[]{"Code ASC"});
        this.getData();
    }

}
