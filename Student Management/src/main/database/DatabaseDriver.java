package main.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringJoiner;

public class DatabaseDriver {
    private Connection connection = null;
    private Statement statement;
    private final int ITEMS_PER_READ = 50;
    public DatabaseDriver(String username, String password) throws SQLException{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/ssis",
            username, password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
    }

    public ResultSet readFromTable(String tableName, int page) throws SQLException {
        ResultSet resultSet = statement.executeQuery(
            String.format("SELECT * from %s LIMIT %d OFFSET %d", 
            tableName, ITEMS_PER_READ, ITEMS_PER_READ * (page - 1)));
        return resultSet;
    }

    public ResultSet readFromTable(String tableName, int page, String columnLabel, String regex) throws SQLException{
        ResultSet resultSet = statement.executeQuery(
            String.format("SELECT * from %s WHERE %s REGEXP \'(?i)%s\' LIMIT %d OFFSET %d", 
            tableName, columnLabel, regex, ITEMS_PER_READ, ITEMS_PER_READ * (page - 1)));
        return resultSet;
    }

    public int totalRowsFromTable(String tableName) throws SQLException{
        ResultSet totalRowsSet = statement.executeQuery(
            "SELECT COUNT(*) FROM " + tableName
        );
        totalRowsSet.next();
        int total = totalRowsSet.getInt(1);
        totalRowsSet.close(); 
        return total;
    }

    public int totalRowsFromTable(String tableName, String columnLabel, String regex) throws SQLException{
        ResultSet totalRowsSet = statement.executeQuery(
            String.format("SELECT COUNT(*) FROM %s WHERE %s REGEXP \'(?i)%s\'" ,
            tableName, columnLabel, regex)
        );
        totalRowsSet.next();
        int total = totalRowsSet.getInt(1);
        totalRowsSet.close(); 
        return total;
    }

    public void addToTable(String[] data, String tableName) throws SQLException{
        StringJoiner valuesJoiner = new StringJoiner(",");
        for(String dataEntry : data){
            valuesJoiner.add("\'" + dataEntry + "\'");
        }
        String values = "(" + valuesJoiner.toString() + ")";
        statement.executeUpdate(
            "INSERT INTO " + tableName + " VALUES " + values + ";"
        );
    }

    public boolean ifRecordExists(String columnLabel, String tableName, String record) throws SQLException{
        ResultSet resultSet = statement.executeQuery(
            "SELECT " + columnLabel + " from " + tableName + " WHERE " + 
            columnLabel + "=\'" + record + "\'");
        boolean recordExists = resultSet.next();
        resultSet.close();
        return recordExists;
    }

    public boolean ifRecordExists(String columnLabel1, String columnLabel2, String tableName,
                                  String record1, String record2) throws SQLException{
        ResultSet resultSet = statement.executeQuery(
            "SELECT " + columnLabel1 + "," + columnLabel2 + " from " + tableName + " WHERE " + 
            columnLabel1 + "=\'" + record1 + "\' and " + columnLabel2 + "=\'" + record2 + "\'");
        boolean recordExists = resultSet.next();
        resultSet.close();
        return recordExists;
    }
    public String[] getArrayFromColumn(String columnLabel, String tableName) throws SQLException{

        int rows = this.totalRowsFromTable(tableName);
        
        String[] res = new String[rows];
        ResultSet resultSet = statement.executeQuery(
            "SELECT " + columnLabel + " from " + tableName
        );
        
        int i = 0;
        while(resultSet.next()){
            res[i++] = resultSet.getString(columnLabel);
        }
        resultSet.close();
        return res;
    }

    public String getData(String primaryKey, String columnLabel, String tableName) throws SQLException{
        String primaryColumn = (tableName.equals("students")) ? "ID" : "Code" ;
        ResultSet resultSet = statement.executeQuery(
            "SELECT " + columnLabel + " from " + tableName + " WHERE " + 
            primaryColumn + "=\'" + primaryKey +"\'"
        );
        resultSet.next();
        String res = resultSet.getString(columnLabel);
        resultSet.close();
        return res;
    }

    public void updateRecordInTable(String primaryKey, String[] columnLabels, String[] newData, String tableName) throws SQLException{
        String primaryColumn = (tableName.equals("students")) ? "ID" : "Code" ;
        StringJoiner columnsJoiner = new StringJoiner(",");
        byte c = 0;
        for(int i = 0; i < columnLabels.length; i++){
            if(newData[i] != null){
                columnsJoiner.add(columnLabels[i] + "=\'" + newData[i] + "\'");
            } else {c++;}
        }
        if(c < newData.length){
            statement.executeUpdate(
                "UPDATE " + tableName +
                " SET " + columnsJoiner.toString() +
                " WHERE " + primaryColumn + "=\'" + 
                primaryKey + "\'"
            );
        }
    }

    public void batchUpdateRecordsInTable(String[] primaryKeys, String[] columnLabels, String[] newData, String tableName) throws SQLException{
        String primaryColumn = (tableName.equals("students")) ? "ID" : "Code" ;
        StringJoiner columnsJoiner = new StringJoiner(",");
        StringJoiner primaryKeysJoiner = new StringJoiner(",");
        byte c = 0;
        for(int i = 0; i < columnLabels.length; i++){
            if(!newData[i].equals("-")){
                columnsJoiner.add(columnLabels[i] + "=\'" + newData[i] + "\'");
                
            } else {c++;}
        }
        for(int i = 0; i < primaryKeys.length; i++){
            primaryKeysJoiner.add("\'"  + primaryKeys[i] + "\'");
        }

        if(c < newData.length){
            statement.executeUpdate(
                "UPDATE " + tableName +
                " SET " + columnsJoiner.toString() +
                " WHERE " + primaryColumn + " IN (" + 
                primaryKeysJoiner.toString() + ")"
            );
        }
    }

    public void deleteRecordInTable(String primaryKey, String tableName) throws SQLException{
        String primaryColumn = (tableName.equals("students")) ? "ID" : "Code" ;
        statement.executeUpdate(
            "DELETE FROM " + tableName +
            " WHERE " + primaryColumn +
            "=\'" + primaryKey + "\';"
        );
    }

    public int matchesWithForeignKey(String foreignKey, String tableName) throws SQLException{
        String foreignKeyColumn = (tableName.equals("students")) ? "ProgramCode": "CollegeCode" ;
        ResultSet totalMatchesSet = statement.executeQuery(
            "SELECT COUNT(*) FROM " + tableName +
            " WHERE " + foreignKeyColumn + "=\'" +
            foreignKey + "\'"
        );
        totalMatchesSet.next();
        return totalMatchesSet.getInt(1);
    }

    public boolean isTableEmpty(String tableName) throws SQLException{
        ResultSet emptyCheckerSet = statement.executeQuery(
            "SELECT EXISTS (SELECT 1 FROM " + tableName + ")"
        );
        emptyCheckerSet.next();
        return emptyCheckerSet.getInt(1) == 0;
    }
}
