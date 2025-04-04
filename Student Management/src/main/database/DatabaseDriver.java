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
            "jdbc:mysql://localhost:3306",
            username, password);
            statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS uniflow_ssis");
            statement.executeUpdate("USE uniflow_ssis");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS colleges ( " +
                "Code VARCHAR(5) NOT NULL, " +
                "Name varchar(50) NOT NULL, " +
                "PRIMARY KEY (Code), " +
                "UNIQUE KEY name (Name))");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS programs ( " +
                "Code VARCHAR(20) NOT NULL, " +
                "Name VARCHAR(100) NOT NULL, " +
                "CollegeCode VARCHAR(5) DEFAULT NULL, " +
                "PRIMARY KEY (Code), " +
                "UNIQUE KEY Name (Name), " +
                "KEY CollegeCode (CollegeCode), " +
                "CONSTRAINT programs_ibfk_1 FOREIGN KEY (CollegeCode) " +
                "REFERENCES colleges (Code) " +
                "ON DELETE SET NULL ON UPDATE CASCADE)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS students ( " +
                "ID CHAR(9) NOT NULL, " +
                "FirstName VARCHAR(100) NOT NULL, " +
                "LastName VARCHAR(100) NOT NULL, " +
                "YearLevel ENUM(\'1\',\'2\',\'3\',\'4\',\'5\',\'5+\') NOT NULL, " +
                "Gender ENUM(\'M\',\'F\',\'LGBTQIA+\') NOT NULL, " +
                "ProgramCode VARCHAR(20) DEFAULT NULL, " +
                "PRIMARY KEY (ID), " +
                "KEY ProgramCode (ProgramCode), " +
                "CONSTRAINT students_ibfk_1 FOREIGN KEY (ProgramCode) " +
                "REFERENCES programs (Code) " +
                "ON DELETE SET NULL " +
                "ON UPDATE CASCADE, " +
                "CONSTRAINT chk_ID_format CHECK (" +
                "REGEXP_LIKE(ID, _utf8mb4\'^(19[6-9][0-9]|20[0-9]{2})-(?!0000)[0-9]{4}$\')))"
            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
    }

    public ResultSet readFromTable(String tableName, int page, String[] sortingOptions) throws SQLException {
        StringJoiner sortJoiner = new StringJoiner(",");
        for(String sortOption : sortingOptions){
            sortJoiner.add(sortOption);
        }
        ResultSet resultSet = statement.executeQuery(
            String.format("SELECT * from %s ORDER BY %s LIMIT %d OFFSET %d", 
            tableName, sortJoiner.toString(), ITEMS_PER_READ, ITEMS_PER_READ * (page - 1)));
        return resultSet;
    }

    public ResultSet readFromTable(String tableName, 
                                   int page, 
                                   String columnLabel, 
                                   String regex, 
                                   String[] sortingOptions) throws SQLException{
        String query;
        StringJoiner sortJoiner = new StringJoiner(",");
        for(String sortOption : sortingOptions){
            sortJoiner.add(sortOption);
        }
        if(columnLabel.equals("All")){
            ResultSet columns = statement.executeQuery(
                "SHOW COLUMNS FROM " + tableName
            );
            StringJoiner valuesJoiner = new StringJoiner(" OR ");
            String toBeSearched = (regex.equals("NULL")) ? " IS NULL" : " REGEXP \'" + regex + "\'" ;
            while(columns.next()){
                valuesJoiner.add(columns.getString(1) + 
                toBeSearched);
            }
            query = String.format("SELECT * from %s WHERE %s ORDER BY %s LIMIT %d OFFSET %d", 
            tableName, valuesJoiner.toString(), sortJoiner.toString(), ITEMS_PER_READ, ITEMS_PER_READ * (page - 1));
            columns.close();
        } else {
            query = (regex.equals("NULL")) ?
            String.format("SELECT * from %s WHERE %s ORDER BY %s IS NULL LIMIT %d OFFSET %d", 
            tableName, columnLabel, sortJoiner.toString(), ITEMS_PER_READ, ITEMS_PER_READ * (page - 1)) :
            String.format("SELECT * from %s WHERE %s REGEXP \'%s\' ORDER BY %s LIMIT %d OFFSET %d", 
            tableName, columnLabel, regex, sortJoiner.toString(), ITEMS_PER_READ, ITEMS_PER_READ * (page - 1));
        }
        ResultSet resultSet = statement.executeQuery(query);
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
        String query;
        if(columnLabel.equals("All")){
            ResultSet columns = statement.executeQuery(
                "SHOW COLUMNS FROM " + tableName
            );
            StringJoiner valuesJoiner = new StringJoiner(" OR ");
            String toBeSearched = (regex.equals("NULL")) ? " IS NULL" : " REGEXP \'" + regex + "\'" ;
            while(columns.next()){
                valuesJoiner.add(columns.getString(1) + 
                toBeSearched);
            }
            query = String.format("SELECT COUNT(*) from %s WHERE %s", 
            tableName, valuesJoiner.toString());
            columns.close();
        } else {
            query = (regex.equals("NULL")) ?
            String.format("SELECT COUNT(*) from %s WHERE %s IS NULL ", 
            tableName, columnLabel) :
            String.format("SELECT COUNT(*) from %s WHERE %s REGEXP \'%s\' ", 
            tableName, columnLabel, regex);
        }
        ResultSet totalRowsSet = statement.executeQuery(query);
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

    public void batchDeleteRecordsInTable(String[] primaryKeys, String tableName) throws SQLException{
        String primaryColumn = (tableName.equals("students")) ? "ID" : "Code" ;
        StringJoiner primaryKeysJoiner = new StringJoiner(",");
        for(int i = 0; i < primaryKeys.length; i++){
            primaryKeysJoiner.add("\'"  + primaryKeys[i] + "\'");
        }
        statement.executeUpdate(
            "DELETE FROM " + tableName +
            " WHERE " + primaryColumn + " IN (" + 
            primaryKeysJoiner.toString() + ")"
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
        int res = totalMatchesSet.getInt(1);
        totalMatchesSet.close();
        return res;
    }

    public String[] matchesWithChildTable(String primaryKey, String tableName) throws SQLException{
        
        String childTableName = (tableName.equals("colleges")) ? "programs": "students" ;
        String foreignKeyColumn = (childTableName.equals("students")) ? "ProgramCode": "CollegeCode" ;
        ResultSet totalMatchesSet = statement.executeQuery(
            "SELECT COUNT(*) FROM " + childTableName +
            " WHERE " + foreignKeyColumn + "=\'" +
            primaryKey + "\'"
        );
        totalMatchesSet.next();
        int totalMatches = totalMatchesSet.getInt(1);
        totalMatchesSet.close();
        
        ResultSet resultSet = statement.executeQuery(
            "SELECT * FROM " + childTableName +
            " WHERE " + foreignKeyColumn + "=\'" +
            primaryKey + "\'"
        );

        int i = 0;
        String[] res = new String[totalMatches];
        while(resultSet.next()){
            res[i++] = resultSet.getString(1);
        }
        resultSet.close();
        return res;
    }

    public boolean isTableEmpty(String tableName) throws SQLException{
        ResultSet emptyCheckerSet = statement.executeQuery(
            "SELECT EXISTS (SELECT 1 FROM " + tableName + ")"
        );
        emptyCheckerSet.next();
        boolean res = emptyCheckerSet.getInt(1) == 0;
        emptyCheckerSet.close();
        return res;
    }
}
