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
            columnLabel + "=\'" + record +"\'");
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
}
