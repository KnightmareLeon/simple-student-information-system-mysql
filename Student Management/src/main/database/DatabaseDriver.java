package main.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public ResultSet readFromTable(String table){
        try {
            ResultSet resultSet = statement.executeQuery(
                "SELECT * from " + table
            );
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
        
    }
}
