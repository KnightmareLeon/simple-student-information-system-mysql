package main.app.input.fields;

import java.awt.Dimension;

import javax.swing.JComboBox;

import main.app.tables.ManagementTable;

/**
 * Custom {@code JComboBox} that sets the list of columns names
 * that the {@link SearchBar} will filter {@link ManagementTable} by.
 * The choices set is based on the {@code TableModel} of the
 * {@code ManagementTable}.
 */
public class SearchFieldList extends JComboBox<String>{
    String[] studentColumns = {"All", "ID", "First Name", "Last Name", 
                               "Year Level", "Gender", "Program Code"};
    String[] programColumns = {"All", "Code", "Name", "College Code"};
    String[] collegeColumns = {"All", "Code", "Name"};
    ManagementTable mTable;
    public SearchFieldList(ManagementTable mTable){
        this.setPreferredSize(new Dimension(115,20));
        this.mTable = mTable;
        this.set();
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
}
