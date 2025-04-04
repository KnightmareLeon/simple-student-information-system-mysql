package main.app.undo;

import java.sql.SQLException;

import main.database.DatabaseDriver;

public class UndoDeleteAction extends UndoAction{
    private String[] data;
    private String[] affectedChildKeys;
    public UndoDeleteAction(String[] data, String[] affectedChildKeys, String tableName, DatabaseDriver dbDriver) {
        super(tableName, dbDriver);
        this.data = data;
        this.affectedChildKeys = affectedChildKeys;
        System.out.println(data[0]);
    }

    @Override
    public void undo() {
        try {
            dbDriver.addToTable(data, tableName);
            if(!tableName.equals("students")){
                String foreignKeyColumn = (tableName.equals("colleges")) ? 
                                          "CollegeCode": "ProgramCode" ;
                String childTableName = (tableName.equals("colleges")) ? 
                                          "programs": "students" ;
                dbDriver.batchUpdateRecordsInTable(affectedChildKeys, 
                                                   new String[]{foreignKeyColumn},
                                                   new String[]{data[0]},
                                                   childTableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
