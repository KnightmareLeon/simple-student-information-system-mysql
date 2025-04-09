package main.app.undo;

import java.sql.SQLException;

import main.database.DatabaseDriver;

public class UndoDeleteAction extends UndoAction{
    private String[][] dataArr;
    private String[][] affectedChildKeysArr;
    public UndoDeleteAction(String[][] dataArr, String[][] affectedChildKeysArr, String tableName, DatabaseDriver dbDriver) {
        super(tableName, dbDriver);
        this.dataArr = dataArr;
        this.affectedChildKeysArr = affectedChildKeysArr;
    }

    @Override
    public void undo() {
        try {
            for(int i = 0; i < dataArr.length; i++){
                String[] data = dataArr[i];
                String[] affectedChildKeys = affectedChildKeysArr[i];
                dbDriver.addToTable(data, tableName);
                if(!tableName.equals("students") && affectedChildKeys.length > 0){
                    String foreignKeyColumn = (tableName.equals("colleges")) ? 
                                            "CollegeCode": "ProgramCode" ;
                    String childTableName = (tableName.equals("colleges")) ? 
                                            "programs": "students" ;
                    dbDriver.batchUpdateRecordsInTable(affectedChildKeys, 
                                                    new String[]{foreignKeyColumn},
                                                    new String[]{data[0]},
                                                    childTableName);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
