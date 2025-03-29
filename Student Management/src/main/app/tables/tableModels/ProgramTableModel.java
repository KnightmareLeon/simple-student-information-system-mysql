package main.app.tables.tableModels;

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
