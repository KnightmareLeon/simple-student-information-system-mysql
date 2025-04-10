package main.app.undo;

import java.sql.SQLException;

import main.database.DatabaseDriver;

/**
 * Handles the undo action of adding a record to the database.
 */
public class UndoAddAction extends UndoAction{
    private String primaryKey;
    public UndoAddAction(String primaryKey, String tableName, DatabaseDriver dbDriver) {
        super(tableName, dbDriver);
        this.primaryKey = primaryKey;
    }

    @Override
    public void undo() {
        try {
            dbDriver.deleteRecordInTable(primaryKey, tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
