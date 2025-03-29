package main.app.tables.tableModels;

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
public class CollegeTableModel extends DatabaseHandlingTableModel{
    public CollegeTableModel(DatabaseDriver dbDriver){
        super(dbDriver);
        this.setColumnCount(2);
        this.setTableName("colleges");
        this.setColumnIdentifiers(new String[]{
            "Code",
            "Name"});
        this.getData();
    }

}
