package main.app.windows;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import main.app.buttons.SortingOptionsButton;
import main.app.buttons.UndoButton;
import main.app.buttons.add.*;
import main.app.buttons.changeTable.*;
import main.app.buttons.delete.*;
import main.app.buttons.edit.*;
import main.app.input.fields.SearchBar;
import main.app.input.fields.SearchFieldList;
import main.app.tables.ManagementTable;
import main.app.tables.pageHandler.PageHandler;
import main.database.DatabaseDriver;

/**
 * {@code JFrame} class that sets up all components needed to
 * display the main window of the application.
 */
public class MainFrame extends DefaultFrame{

    /**
     * Adds all components needed to the frame. 
     */
    public MainFrame(DatabaseDriver dbDriver){
        this.setResizable(true);

        final ManagementTable mTable = new ManagementTable(dbDriver);
        final JScrollPane sp = new JScrollPane(mTable);
    
        final SearchFieldList searchFieldList = new SearchFieldList(mTable);
        final SearchBar searchBar = new SearchBar();
        
        final UndoButton undoButton = new UndoButton();
        mTable.setUndoButton(undoButton);
        
        final AddDataButton addStdButton = new AddStudentButton(this, mTable);
        final AddDataButton addPrgButton = new AddProgramButton(this, mTable);
        final AddDataButton addClgButton = new AddCollegeButton(this, mTable);
    
        final DeleteDataButton delStdButton = new DeleteStudentButton(this, mTable);
        final DeleteDataButton delPrgButton = new DeleteProgramButton(this, mTable);
        final DeleteDataButton delClgButton = new DeleteCollegeButton(this, mTable);
    
        final EditDataButton editStdButton = new EditStudentButton(this, mTable);
        final EditDataButton editPrgButton = new EditProgramButton(this, mTable);
        final EditDataButton editClgButton = new EditCollegeButton(this, mTable);
        
        final PageHandler pageHandler = new PageHandler(mTable, searchBar, searchFieldList);
        searchBar.setPageHandler(pageHandler);
        mTable.setPageHandler(pageHandler);
        undoButton.setPageHandler(pageHandler);
        final SortingOptionsButton sortingFormButton = new SortingOptionsButton(mTable, pageHandler);

        final ChangeToTableButton cStdTblButton = new ChangeToStudentTableButton(pageHandler, sp, mTable, 
            new AddDataButton[]{addStdButton, addPrgButton, addClgButton}, 
            new DeleteDataButton[]{delStdButton, delPrgButton, delClgButton},
            new EditDataButton[]{editStdButton, editPrgButton, editClgButton},
            searchBar, searchFieldList);
        final ChangeToTableButton cPrgTblButton = new ChangeToProgramTableButton(pageHandler, sp, mTable,
            new AddDataButton[]{addPrgButton, addStdButton, addClgButton}, 
            new DeleteDataButton[]{delPrgButton, delStdButton, delClgButton},
            new EditDataButton[]{editPrgButton, editStdButton, editClgButton},
            searchBar, searchFieldList);
        final ChangeToTableButton cClgTblButton = new ChangeToCollegeTableButton(pageHandler, sp, mTable, 
            new AddDataButton[]{addClgButton, addStdButton, addPrgButton},
            new DeleteDataButton[]{delClgButton, delPrgButton, delStdButton},
            new EditDataButton[]{editClgButton, editStdButton, editPrgButton},
            searchBar, searchFieldList);
        final ButtonGroup changeTableGroup = new ButtonGroup();
    
        final JLabel searchLabel = new JLabel("Search: ");
        final JLabel by = new JLabel("By: ");
    
        final JPanel content = new JPanel(new BorderLayout());
    
        final JPanel tools = new JPanel(new GridBagLayout());
        final JPanel table = new JPanel(new BorderLayout());
        
        final JPanel dataButtons = new JPanel(new GridBagLayout());
        final JPanel changeTables = new JPanel(new GridBagLayout());
        
        final GridBagConstraints dataButtonsGBC = new GridBagConstraints();
        final GridBagConstraints toolsGBC = new GridBagConstraints();
        final GridBagConstraints changeTablesGBC = new GridBagConstraints();
    
        final Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        
        changeTableGroup.add(cStdTblButton);
        changeTableGroup.add(cPrgTblButton);
        changeTableGroup.add(cClgTblButton);

        dataButtonsGBC.gridx = dataButtonsGBC.gridy = 0;
        dataButtonsGBC.insets = new Insets(0, 10, 0, 0);
        dataButtonsGBC.fill = GridBagConstraints.VERTICAL;
        dataButtons.add(addStdButton, dataButtonsGBC);
        dataButtons.add(addPrgButton, dataButtonsGBC);
        dataButtons.add(addClgButton, dataButtonsGBC);
        dataButtonsGBC.gridx = 2;
        dataButtons.add(delStdButton, dataButtonsGBC);
        dataButtons.add(delPrgButton, dataButtonsGBC);
        dataButtons.add(delClgButton, dataButtonsGBC);
        dataButtonsGBC.gridx = 3;
        dataButtons.add(editStdButton, dataButtonsGBC);
        dataButtons.add(editPrgButton, dataButtonsGBC);
        dataButtons.add(editClgButton, dataButtonsGBC);

        toolsGBC.gridx = toolsGBC.gridy = 0;
        toolsGBC.insets = new Insets(0, 0, 15, 0);
        toolsGBC.anchor = GridBagConstraints.LINE_START;
        tools.add(searchLabel, toolsGBC); 
        
        toolsGBC.gridx = 1; 
        toolsGBC.insets = new Insets(0, 10, 15, 0);
        toolsGBC.fill = GridBagConstraints.VERTICAL;
        tools.add(searchBar, toolsGBC);

        toolsGBC.gridx = 2;
        toolsGBC.anchor = GridBagConstraints.WEST;
        toolsGBC.fill = GridBagConstraints.VERTICAL;
        tools.add(by, toolsGBC);

        toolsGBC.gridx = 3;
        tools.add(searchFieldList, toolsGBC);

        toolsGBC.gridx = 4;
        tools.add(sortingFormButton, toolsGBC);

        toolsGBC.gridx = 5;
        toolsGBC.weightx = 10;
        tools.add(undoButton, toolsGBC);

        toolsGBC.fill = GridBagConstraints.BOTH;
        toolsGBC.gridx = 6; toolsGBC.fill = GridBagConstraints.NONE; 
        toolsGBC.anchor = GridBagConstraints.LINE_END;
        tools.add(dataButtons, toolsGBC);

        changeTablesGBC.gridx = 0; changeTablesGBC.gridy = 0; 
        changeTables.add(cStdTblButton, changeTablesGBC);
        changeTablesGBC.gridx = 1;
        changeTables.add(cPrgTblButton, changeTablesGBC);
        changeTablesGBC.gridx = 2; changeTablesGBC.weightx = 1;
        changeTablesGBC.anchor = GridBagConstraints.LINE_START;
        changeTables.add(cClgTblButton, changeTablesGBC); 
        
        table.add(changeTables, BorderLayout.NORTH);
        table.add(sp, BorderLayout.CENTER);

        content.add(tools, BorderLayout.NORTH);
        content.add(table, BorderLayout.CENTER);
        content.add(pageHandler, BorderLayout.SOUTH);

        content.setBorder(padding);

        this.setContentPane(content);
        
        pageHandler.setUpPageHandling();
        pageHandler.setPageText();
        
        setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 100, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 100);
        setLocationRelativeTo(null);
        setVisible(true);  
        setExtendedState(DefaultFrame.MAXIMIZED_BOTH);

    }

}


