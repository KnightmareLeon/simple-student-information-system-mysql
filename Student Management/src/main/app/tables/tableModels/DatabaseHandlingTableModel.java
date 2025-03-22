package main.app.tables.tableModels;

import javax.swing.table.DefaultTableModel;

import main.data.maps.DataMap;
import main.database.DatabaseDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.IOException;

/**
 * An abstract class that is a custom {@code DefaultTableModel} that handles
 * the CSV files in which this application's data is stored. It can read and
 * save data to the csv file that is designated in its {@code fileName} field.
 * It also calls the {@code DataMap} that handles data during the application's 
 * runtime.
 * <p>
 * Can implement the {@code OtherTableModelEditor} interface to allow the 
 * {@code CSVHandlingTableModel} to handle data from another {@code CSVHandlingTableModel}
 * @see {@link StudentTableModel} - the child class that
 * handles the csv file and data for students.
 * @see {@link ProgramTableModel} - the child class that 
 * handles the csv file and data for programs.
 * @see {@link CollegeTableModel} - the child class that 
 * handles the csv file and data for colleges.
 * @see {@link OtherTableModelEditor}
 */
public abstract class DatabaseHandlingTableModel extends DefaultTableModel{

    /**
     * Sees if there are any changes to the table model.
     */
    private boolean changed = false;

    /**
     * Stores the file directory of the CSV files.
     */
    private final String FILE_DIRECTORY = "Student Management/src/resources/"
    + "database/";

    /**
     * Stores the file name that this table model will be handling. Child
     * classes will have to set the designated file names.
     * @see StudentTableModel {@code StudentTableModel} 
     * @see ProgramTableModel {@code ProgramTableModel} 
     * @see CollegeTableModel {@code CollegeTableModel} 
     */
    private String fileName;

    private String tableName;

    protected void getData(DatabaseDriver dbDriver){
        try {
            ResultSet resultSet = dbDriver.readFromTable(tableName);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int c = rsmd.getColumnCount();
            while(resultSet.next()){
                String[] data = new String[c];
                for(int i = 1; i <= c; i++){
                    data[i - 1] = (resultSet.getString(i) == null) ?
                    "NULL" : resultSet.getString(i);   
                }
                this.addRow(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    /**
     * Add an array of data to a new row in the 
     * {@code CSVHandlingTableModel} and to the {@link DataMap}.
     * Also changes the table model's {@link #changed} state to {@code true}.
     * @param data - array of data that is from the designated {@code DataInput} 
     * child class
     * @param dMap - {@link DataMap} that handles and maps all data during 
     * the application's runtime.
     * @see main.app.input.DataInput {@code DataInput}
     */
    public void addData(String[] data, DataMap dMap){
        this.addToMap(data, dMap);
        this.addRow(data);
        this.setChange(true);
    }

    public void addData(String[] data, DatabaseDriver dbDriver) throws SQLException{
        dbDriver.addToTable(data, tableName);
        this.addRow(data);
    }

    /**
     * Deletes a selected row in the {@code CSVHandlingTableModel} 
     * and in the {@link DataMap}.
     * Also changes the table model's {@link #changed} state to {@code true}.
     * @param row - selected row's index
     * @param dMap - {@link DataMap} that handles and maps all data during 
     * the application's runtime.
     */
    public void deleteData(int row, DataMap dMap){
        this.deleteFromMap((String) this.getValueAt(row, 0), dMap);
        this.removeRow(row);
        this.setChange(true);
    }

    public void deleteData(int row, DatabaseDriver dBDriver) throws SQLException{
        dBDriver.deleteRecordInTable((String) this.getValueAt(row, 0), tableName);
        this.removeRow(row);
    }
    /**
     * Edits data on a selected row in the {@code CSVHandlingTableModel} and in 
     * the {@link DataMap}.
     * Also changes the table model's {@link #changed} state to {@code true}.
     * @param row - selected row's index.
     * @param newData - an array of {@code String} values from 
     * {@link main.app.input.DataInput DataInput}.
     * @param dMap - {@link DataMap} that handles and maps all data during 
     * the application's runtime.
     */
    public void editData(int row, String[] newData, DataMap dMap){
        String prevCode = (String) this.getValueAt(row, 0);
        this.editDataOnMap(prevCode, newData, dMap);
        for(int i = 0; i < this.getColumnCount(); i++){
            this.setValueAt(newData[i], row, i);
        }
        this.setChange(true);
    }

    public void editData(int row, String[] newData, DatabaseDriver dBDriver) throws SQLException{
        String primaryKey = (String) this.getValueAt(row, 0);
        String[] record = new String[newData.length];
        String[] columnLabels = new String[newData.length];
        for(int i = 0; i < newData.length; i++){
            columnLabels[i] = this.getColumnName(i).replaceAll(" ", "");
            record[i] = (!newData[i].equals((String) this.getValueAt(row, i))) ?
                newData[i] : null;
        }
        dBDriver.updateRecordInTable(primaryKey, columnLabels, record, tableName);
        for(int i = 0; i < this.getColumnCount(); i++){
            this.setValueAt(newData[i], row, i);
        }
    }

    /**
     * Edits multiple data on selected rows in the {@code CSVHandlingTableModel}
     * and in the {@link DataMap}.
     * Also changes the table model's {@link #changed} state to {@code true}.
     * @param rows - {@code int} array containing the selected rows' indices.
     * @param newData - an array of {@code String} values from 
     * {@link main.app.input.DataInput DataInput}.
     * @param dMap - {@link DataMap} that handles and maps all data during 
     * the application's runtime.
     */
    public void multiEditData(int[] rows, String[] newData, DataMap dMap){
        String[] keys = new String[rows.length];
        for(int i = 0; i < keys.length; i++){
            keys[i] = (String) this.getValueAt(rows[i], 0);
        } 
        this.multiEditDataOnMap(keys, newData, dMap);
        int tableI = this.getColumnCount() - 1;
        for(int row: rows){
            for(int i = newData.length - 1; i > -1; i--){
                if(!newData[i].equals("-")){
                    this.setValueAt(newData[i], row, tableI--);
                } else {
                    tableI--;
                }
            }
            tableI = this.getColumnCount() - 1;
        }
        this.setChange(true);
    }

    public void batchEdit(int[] rows, String[] newData, DatabaseDriver dbDriver) throws SQLException{
        String[] primaryKeys = new String[rows.length];
        for(int i = 0; i < primaryKeys.length; i++){
            primaryKeys[i] = (String) this.getValueAt(rows[i], 0);
        } 
        String[] columnLabels = new String[newData.length];
        for(int i = newData.length ; i > 0; i--){
            columnLabels[newData.length - i] = this.getColumnName(this.getColumnCount() - i).replaceAll(" ", "");
        }
        dbDriver.batchUpdateRecordsInTable(primaryKeys, columnLabels, newData, tableName);
        int tableI = this.getColumnCount() - 1;
        for(int row: rows){
            for(int i = newData.length - 1; i > -1; i--){
                if(!newData[i].equals("-")){
                    this.setValueAt(newData[i], row, tableI--);
                } else {
                    tableI--;
                }
            }
            tableI = this.getColumnCount() - 1;
        }
    }

    /**
     * Saves the data stored in the {@code CSVHandlingTableModel} into its
     * designated CSV file.
     */
    public void saveData(){
        try {
            Files.deleteIfExists(Paths.get(this.getFile()));
            File file = new File(this.getFile());
            file.createNewFile();
            Writer csvWriter = new BufferedWriter(
                new FileWriter(this.getFile(), true));
            String[] data = new String[getColumnCount()];
            for(int i = 0; i < getRowCount(); i++){
                for(int j = 0; j < getColumnCount(); j++){
                    data[j] = (String) getValueAt(i, j);
                }
                String strData = (i == 0) ? 
                    this.reformatData(data).replace("\n", "") : 
                    this.reformatData(data); 
                csvWriter.append(strData);
            }
            
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setChange(false);
    }

    /**
     * Sets the CSV {@link #fileName} that the {@code CSVHandlingTableModel} will be
     * handling.
     * @param fileName - {@code String}
     */
    protected void setFileName(String fileName){this.fileName = fileName;}

    /**
     * Gets the CSV {@link #fileName} along with the {@link #FILE_DIRECTORY}.
     */
    private String getFile(){return this.FILE_DIRECTORY + this.fileName;}

    /**
     * Reformats data that will be written to the {@code CSVHandlingTableModel}'s
     * designated CSV file.
     * @param data - the array of {@code String} values that will be reformatted
     * @return {@code String} that is reformatted for CSV files.
     */
    protected abstract String reformatData(String[] data);

    /**
     * Adds data to the {@code DataMap} that the {@code CSVHandlingTableModel} is
     * designated to handle.
     * @param data - the array of {@code String} values that will be added to
     * the {@code DataMap}
     * @param dMap - {@link DataMap} that handles and maps all data during 
     * the application's runtime.
     */
    protected abstract void addToMap(String[] data, DataMap dMap);

    /**
     * Deletes data from the {@code DataMap} that the {@code CSVHandlingTableModel} is
     * designated to handle.
     * @param code - {@code String} key for getting the value from the
     * {@code CSVHandlingTableModel}'s designated {@code HashMap}
     * @param dMap - {@code DataMap} that handles and maps all data during 
     * the application's runtime.
     */
    protected abstract void deleteFromMap(String code, DataMap dMap);

    /**
     * Edits data from the {@link DataMap} that the {@code CSVHandlingTableModel} is
     * designated to handle.
     * @param prevCode - existing {@code String} key for getting the value from the
     * {@code CSVHandlingTableModel}'s designated {@code HashMap} will replace previous 
     * data.
     * @param newData - an array of {@code String} values from 
     * {@link main.app.input.DataInput DataInput}
     * @param dMap - {@link DataMap} that handles and maps all data during 
     * the application's runtime.
     */
    protected abstract void editDataOnMap(String prevCode, String[] newData, DataMap dMap);

    /**
     * Edits multiple data from the {@link DataMap} that the {@code CSVHandlingTableModel}
     * is designated to handle.
     * @param keys - {@code String} array that are keys for getting values from the
     * {@code CSVHandlingTableModel}'s designated {@code HashMap}.
     * @param newData - an array of {@code String} values from 
     * {@link main.app.input.DataInput DataInput} that will replace previous data.
     * @param dMap - {@link DataMap} that handles and maps all data during 
     * the application's runtime.
     */
    protected abstract void multiEditDataOnMap(String[] keys, String[] newData, DataMap dMap);

    /**
     * Checks if the {@code CSVHandlingTableModel} has changed
     * @return {@code true} if the {@code CSVHandlingTableModel} has changed,
     * otherwise {@code false}
     */
    public boolean isChanged(){return changed;}

    /**
     * Sets the state of {@link #changed} for this {@code CSVHandlingTableModel}
     * @param changed - {@code boolean} value
     */
    public void setChange(boolean changed){this.changed = changed;}

    protected void setTableName(String tableName){this.tableName = tableName;}

    public String getTableName(){return tableName;}

    @Override
    public boolean isCellEditable(int row, int column) {return false;}
}
