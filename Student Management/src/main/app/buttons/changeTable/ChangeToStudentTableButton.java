package main.app.buttons.changeTable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;

import main.app.buttons.add.AddDataButton;
import main.app.buttons.delete.DeleteDataButton;
import main.app.buttons.edit.EditDataButton;
import main.app.input.fields.SearchBar;
import main.app.input.fields.SearchFieldList;
import main.app.tables.ManagementTable;

/**
 * Sets {@link ManagementTable}'s model to 
 * {@link main.app.tables.tableModels.StudentTableModel StudentTableModel}
 */
public class ChangeToStudentTableButton extends ChangeToTableButton {
    public ChangeToStudentTableButton(JScrollPane sp, ManagementTable mTable,
        AddDataButton[] aDButtons, DeleteDataButton[] dDButtons, EditDataButton[] eDButtons,
        SearchBar searchBar, SearchFieldList searchFieldList){
        this.setText("Student Table");
        this.setSelected(true);
        this.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                changeToTable(sp, mTable.getSTM(), mTable, aDButtons, dDButtons, eDButtons, 
                              searchBar, searchFieldList);  
            }
        });
    }
}
