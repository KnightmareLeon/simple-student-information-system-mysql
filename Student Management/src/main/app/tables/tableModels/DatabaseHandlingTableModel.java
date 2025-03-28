package main.app.tables.tableModels;

import javax.swing.table.DefaultTableModel;

import main.database.DatabaseDriver;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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
    private String tableName;
    private DatabaseDriver dbDriver;

    public DatabaseHandlingTableModel(DatabaseDriver dbDriver){
        this.dbDriver = dbDriver;
    }

    protected void getData(){getData( 1);}

    public void getData(int page){
        this.setRowCount(0);
        try {
            ResultSet resultSet = dbDriver.readFromTable(tableName, page);
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
    
    public void addData(String[] data) throws SQLException{
        dbDriver.addToTable(data, tableName);
        this.addRow(data);
    }

    public void deleteData(int row) throws SQLException{
        dbDriver.deleteRecordInTable((String) this.getValueAt(row, 0), tableName);
        this.removeRow(row);
    }

    public void editData(int row, String[] newData) throws SQLException{
        String primaryKey = (String) this.getValueAt(row, 0);
        String[] record = new String[newData.length];
        String[] columnLabels = new String[newData.length];
        for(int i = 0; i < newData.length; i++){
            columnLabels[i] = this.getColumnName(i).replaceAll(" ", "");
            record[i] = (!newData[i].equals((String) this.getValueAt(row, i))) ?
                newData[i] : null;
        }
        dbDriver.updateRecordInTable(primaryKey, columnLabels, record, tableName);
        for(int i = 0; i < this.getColumnCount(); i++){
            this.setValueAt(newData[i], row, i);
        }
    }

    public void batchEdit(int[] rows, String[] newData) throws SQLException{
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

    public int getTotalRows() throws SQLException{
        return dbDriver.totalRowsFromTable(tableName);
    }
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
