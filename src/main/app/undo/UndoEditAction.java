package main.app.undo;

import java.sql.SQLException;

import main.database.DatabaseDriver;

public class UndoEditAction extends UndoAction{

    private String primaryKey;
    private String[] prevData;
    private String[] columnLabels;
    public UndoEditAction(String primaryKey, 
                          String[] columnLabels, 
                          String[] prevData, 
                          String tableName, 
                          DatabaseDriver dbDriver) {
        super(tableName, dbDriver);
        this.primaryKey = primaryKey;
        this.prevData = prevData;
        this.columnLabels = columnLabels;
    }

    @Override
    public void undo() {
        try {
            this.dbDriver.updateRecordInTable(primaryKey, columnLabels, prevData, tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
