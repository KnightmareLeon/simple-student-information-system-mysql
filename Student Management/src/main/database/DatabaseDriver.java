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
        return resultSet.next();
    }
}
