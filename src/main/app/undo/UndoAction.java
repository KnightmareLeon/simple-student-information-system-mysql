package main.app.undo;

import main.database.DatabaseDriver;

/**
 * Abstract class that handles the undo action of a {@link main.app.buttons.UndoButton UndoButton}.
 * The {@code UndoAction} class is used to undo the last action performed on the database.
 */
public abstract class UndoAction {
    protected String tableName;
    protected DatabaseDriver dbDriver;
    public UndoAction(String tableName, DatabaseDriver dbDriver){
        this.tableName = tableName;
        this.dbDriver = dbDriver;
    }

    public abstract void undo();
}
