package main.app.undo;

import java.sql.SQLException;

import main.database.DatabaseDriver;

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
