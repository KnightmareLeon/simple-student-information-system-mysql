package main.app.buttons.changeTable;

import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

import main.app.buttons.add.AddDataButton;
import main.app.buttons.delete.DeleteDataButton;
import main.app.buttons.edit.EditDataButton;
import main.app.input.fields.SearchBar;
import main.app.input.fields.SearchFieldList;
import main.app.tables.ManagementTable;
import main.app.tables.tableModels.CSVHandlingTableModel;

/**
 * Abstract {@link JToggleButton} that changes {@link ManagementTable}'s
 * model to what its child classes sets it to. Also changes the
 * relevant buttons to their related data type.
 * @see ChangeToStudentTableButton {@code ChangeToStudentTableButton}
 * @see ChangeToProgramTableButton {@code ChangeToProgramTableButton}
 * @see ChangeToCollegeTableButton {@code ChangeToCollegeTableButton}
 */
public abstract class ChangeToTableButton extends JToggleButton{
    public void changeToTable(JScrollPane sp, CSVHandlingTableModel tm, ManagementTable mTable, 
        AddDataButton[] aDButtons, DeleteDataButton[] dDButtons, EditDataButton[] eDButtons,
        SearchBar searchBar, SearchFieldList searchFieldList){
        mTable.setModel(tm);
        sp.setViewportView(mTable);

        aDButtons[0].setVisible(true);
        aDButtons[1].setVisible(false);
        aDButtons[2].setVisible(false);

        dDButtons[0].setVisible(true);
        dDButtons[1].setVisible(false);
        dDButtons[2].setVisible(false);
        
        eDButtons[0].setVisible(true);
        eDButtons[1].setVisible(false);
        eDButtons[2].setVisible(false);
        
        searchBar.setText("");
        searchFieldList.set();
    }
}
