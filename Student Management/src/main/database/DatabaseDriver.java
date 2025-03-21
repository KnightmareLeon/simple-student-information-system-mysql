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
    public DatabaseDriver(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/ssis",
            "root", "Solluna247sasaki@");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
    }

    public ResultSet readFromTable(String tableName) throws SQLException{
        ResultSet resultSet = statement.executeQuery(
            "SELECT * from " + tableName);
        return resultSet;
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
        
        ResultSet totalRowsSet = statement.executeQuery(
            "SELECT COUNT(*) FROM " + tableName
        );
        totalRowsSet.next();
        int rows = totalRowsSet.getInt(1);
        
        String[] res = new String[rows];
        ResultSet resultSet = statement.executeQuery(
            "SELECT " + columnLabel + " from " + tableName
        );
        
        int i = 0;
        while(resultSet.next()){
            res[i++] = resultSet.getString(columnLabel);
        }
        totalRowsSet.close(); resultSet.close();
        return res;
    }

    public String getData(String primaryKey, String columnLabel, String tableName) throws SQLException{
        String primaryColumn = (tableName.equals("students")) ? "id" : "code" ;
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
        String primaryColumn = (tableName.equals("students")) ? "id" : "code" ;
        StringJoiner valuesJoiner = new StringJoiner(",");
        byte c = 0;
        for(int i = 0; i < columnLabels.length; i++){
            if(newData[i] != null){
                valuesJoiner.add(columnLabels[i] + "=\'" + newData[i] + "\'");
            } else {c++;}
        }
        if(c < newData.length){
            statement.executeUpdate(
            "UPDATE " + tableName +
            " SET " + valuesJoiner.toString() +
            " WHERE " + primaryColumn + "=\'" + 
            primaryKey + "\'"
        );
        }
    }
}
