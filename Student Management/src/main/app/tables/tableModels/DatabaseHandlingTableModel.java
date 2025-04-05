package main.app.tables.tableModels;

import javax.swing.table.DefaultTableModel;

import main.app.buttons.UndoButton;
import main.app.tables.pageHandler.PageHandler;
import main.app.undo.UndoAddAction;
import main.app.undo.UndoDeleteAction;
import main.app.undo.UndoEditAction;
import main.database.DatabaseDriver;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * An abstract class that is a custom {@code DefaultTableModel} that handles
 * its designated table from this app's designated MySQL database.
 * @see {@link StudentTableModel} - the child class that
 * handles the students table of this app's designated MySQL database.
 * @see {@link ProgramTableModel} - the child class that 
 * handles the programs table of this app's designated MySQL database.
 * @see {@link CollegeTableModel} - the child class that 
 * handles the colleges table of this app's designated MySQL database.
 */
public abstract class DatabaseHandlingTableModel extends DefaultTableModel{

    private DatabaseDriver dbDriver;
    private PageHandler pageHandler;
    private UndoButton undoButton;
    private String tableName;
    private String[] sortingOptions;

    public DatabaseHandlingTableModel(DatabaseDriver dbDriver){
        this.dbDriver = dbDriver;
    }

    protected void getData(){getData( 1);}

    public void getData(int page){
        this.setRowCount(0);
        try {
            ResultSet resultSet = dbDriver.readFromTable(tableName, page, sortingOptions);
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
    
    public void getData(int page, String columnLabel, String regex){
        this.setRowCount(0);
        String newRegex = "";
        for(char regexChar : regex.toCharArray()){
            newRegex += (Character.toString(regexChar).matches("[.+*?^$()\\[\\]{}\\|]")) ?
                "\\\\" + regexChar : regexChar;
        }
        try {
            ResultSet resultSet = dbDriver.readFromTable(tableName, page, 
            columnLabel.replaceAll(" ", ""), newRegex, sortingOptions);
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
        undoButton.addUndoAction(new UndoAddAction(data[0], tableName, dbDriver));
        pageHandler.setUpPageHandling();
        pageHandler.setPageText();
    }

    public void deleteData(int row) throws SQLException{
        String primaryKey = (String) this.getValueAt(row, 0);
        
        
        dbDriver.deleteRecordInTable(primaryKey, tableName);
        pageHandler.setUpPageHandling();
        pageHandler.setPageText();
    }

    public void deleteData(int[] rows) throws SQLException{
        String[] primaryKeys = new String[rows.length];
        for(int i = 0; i < primaryKeys.length; i++){
            primaryKeys[i] = (String) this.getValueAt(rows[i], 0);
            String[] data = new String[this.getColumnCount()];
            String[] affectedChildKeys = dbDriver.matchesWithChildTable(primaryKeys[i], tableName);
            for(int c = 0; c < this.getColumnCount(); c++){
                data[c] = (String) this.getValueAt(rows[i], c);
            }
            undoButton.addUndoAction(new UndoDeleteAction(data, affectedChildKeys, tableName, dbDriver));
        } 
        dbDriver.batchDeleteRecordsInTable(primaryKeys, tableName);
        pageHandler.setUpPageHandling();
        pageHandler.setPageText();
    }

    public void editData(int row, String[] newData) throws SQLException{
        String primaryKey = (String) this.getValueAt(row, 0);
        String newKey = newData[0];
        String[] record = new String[newData.length];
        String[] prev = new String[newData.length];
        String[] columnLabels = new String[newData.length];
        for(int i = 0; i < newData.length; i++){
            columnLabels[i] = this.getColumnName(i).replaceAll(" ", "");
            record[i] = (!newData[i].equals((String) this.getValueAt(row, i))) ?
                newData[i] : null;
            prev[i] = (!newData[i].equals((String) this.getValueAt(row, i))) ?
                (String) this.getValueAt(row, i) : null;
        }
        dbDriver.updateRecordInTable(primaryKey, columnLabels, record, tableName);
        undoButton.addUndoAction(new UndoEditAction(newKey, columnLabels, prev, tableName, dbDriver));
        pageHandler.setUpPageHandling();
        pageHandler.setPageText();
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
        pageHandler.setUpPageHandling();
        pageHandler.setPageText();
    }

    public int getTotalRows() throws SQLException{
        return dbDriver.totalRowsFromTable(tableName);
    }

    public int getTotalRows(String columnLabel, String regex) throws SQLException{
        String newRegex = "";
        for(char regexChar : regex.toCharArray()){
            newRegex += (Character.toString(regexChar).matches("[.+*?^$()\\[\\]{}\\|]")) ?
                "\\\\" + regexChar : regexChar;
        }
        return dbDriver.totalRowsFromTable(tableName, 
        columnLabel.replaceAll(" ", "")
        , newRegex);
    }

    protected void setTableName(String tableName){this.tableName = tableName;}

    public String getTableName(){return tableName;}

    public void setPageHandler(PageHandler pageHandler){
        this.pageHandler = pageHandler;
    }

    public void setUndoButton(UndoButton undoButton){
        this.undoButton = undoButton;
    }

    public void setSortingOptions(String[] sortingOptions){this.sortingOptions = sortingOptions;}

    public String[] getSortingOptions(){return this.sortingOptions;}

    @Override
    public boolean isCellEditable(int row, int column) {return false;}
}
