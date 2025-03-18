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
 * {@link main.app.tables.tableModels.ProgramTableModel ProgramTableModel}
 */
public class ChangeToProgramTableButton extends ChangeToTableButton{
    public ChangeToProgramTableButton(JScrollPane sp, ManagementTable mTable,
        AddDataButton[] aDButtons, DeleteDataButton[] dDButtons, EditDataButton[] eDButtons,
        SearchBar searchBar, SearchFieldList searchFieldList){
        this.setText("Program Table");
        this.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                changeToTable(sp, mTable.getPTM(), mTable, aDButtons, dDButtons, eDButtons,
                              searchBar, searchFieldList);
            }
        });
    }
}