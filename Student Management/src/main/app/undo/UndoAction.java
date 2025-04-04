package main.app.undo;

import main.database.DatabaseDriver;

public abstract class UndoAction {
    protected String tableName;
    protected DatabaseDriver dbDriver;
    public UndoAction(String tableName, DatabaseDriver dbDriver){
        this.tableName = tableName;
        this.dbDriver = dbDriver;
    }

    public abstract void undo();
}
