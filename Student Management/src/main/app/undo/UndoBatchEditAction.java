package main.app.undo;

import java.sql.SQLException;

import main.database.DatabaseDriver;

public class UndoBatchEditAction extends UndoAction{
    private String[] primaryKeys;
    private String[] columnLabels;
    private String[][] prevDataArr;
    public UndoBatchEditAction(String[] primaryKeys, String[] columnLabels, String[][] prevDataArr, String tableName, DatabaseDriver dbDriver) {
        super(tableName, dbDriver);
        this.primaryKeys = primaryKeys;
        this.columnLabels = columnLabels;
        this.prevDataArr = prevDataArr;
    }

    @Override
    public void undo() {
        for(int i = 0; i < primaryKeys.length; i++){
            try {
                this.dbDriver.updateRecordInTable(primaryKeys[i], columnLabels, prevDataArr[i], tableName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
