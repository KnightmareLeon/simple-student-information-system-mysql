package main.app.input.fields;

import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JComboBox;

import main.app.tables.ManagementTable;

/**
 * Custom {@code JComboBox} that sets the list of columns names
 * that the {@link SearchBar} will filter {@link ManagementTable} by.
 * The choices set is based on the {@code TableModel} of the
 * {@code ManagementTable}.
 */
public class SearchFieldList extends JComboBox<String>{
    String[] studentColumns = {"Any", "ID", "First Name", "Last Name", 
                               "Year Level", "Gender", "Program Code"};
    String[] programColumns = {"Any", "Code", "Name", "College Code"};
    String[] collegeColumns = {"Any", "Code", "Name"};
    HashMap<String, Integer> columnIndices = new HashMap<>(10);
    ManagementTable mTable;
    public SearchFieldList(ManagementTable mTable){
        this.setPreferredSize(new Dimension(115,20));
        this.mTable = mTable;
        this.set();
        for(int i = -1; i < studentColumns.length - 1; i++){
            this.columnIndices.put(studentColumns[i + 1], i);
            if(i != -1 && i < 3){
                this.columnIndices.put(programColumns[i + 1], i);
            }
        }
    }

    /**
     * Private method that actually sets up what items the
     * dropdown list will have.
     * @param columns
     */
    private void setFields(String[] columns){
        this.removeAllItems();
        for(String column: columns){this.addItem(column);};
    }

    /**
     * Sets the items in the dropdown list based on the {@link ManagementTable}'s
     * {@code TableModel}.
     */
    public void set(){
        if(this.mTable.getModel() == this.mTable.getSTM()){
            this.setFields(this.studentColumns);
        } else if(this.mTable.getModel() == this.mTable.getPTM()){
            this.setFields(this.programColumns);
        } else if(this.mTable.getModel() == this.mTable.getCTM()){
            this.setFields(this.collegeColumns);
        }
    }

    /**
     * 
     * @return The column index based on the selected item by the user
     */
    public int getIndex(){
        return columnIndices.get((String) this.getSelectedItem());
    }
}
