package main.app.frames;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import main.app.buttons.SaveButton;
import main.app.buttons.add.*;
import main.app.buttons.changeTable.*;
import main.app.buttons.delete.*;
import main.app.buttons.edit.*;
import main.app.input.fields.SearchBar;
import main.app.input.fields.SearchFieldList;
import main.app.tables.ManagementTable;

/**
 * The main {@code JFrame} of the application. The 
 * {@link main.app.input.fields.SearchBar SearchBar},
 * {@link main.app.buttons.SaveButton SaveButton},
 * {@link main.app.buttons.add.AddDataButton AddButtons},
 * {@link main.app.buttons.delete.DeleteDataButton DeleteButtons},
 * {@link main.app.buttons.edit.EditDataButton EditButtons}, and
 * {@link main.app.tables.ManagementTable ManagementTable} are initialized here.
 */
public class MainFrame extends JFrame{
    private ManagementTable mTable = new ManagementTable();
    private JScrollPane sp = new JScrollPane(this.mTable);

    private SearchFieldList searchFieldList = new SearchFieldList(this.mTable);
    private SearchBar searchBar = new SearchBar(this.mTable.getRowSorter(), this.searchFieldList);
    private SaveButton saveButton = new SaveButton(this.mTable, this);

    private AddDataButton addStdButton = new AddStudentButton(mTable, this);
    private AddDataButton addPrgButton = new AddProgramButton(mTable, this);
    private AddDataButton addClgButton = new AddCollegeButton(mTable, this);
    
    private DeleteDataButton delStdButton = new DeleteStudentButton(mTable, this);
    private DeleteDataButton delPrgButton = new DeleteProgramButton(mTable, this);
    private DeleteDataButton delClgButton = new DeleteCollegeButton(mTable, this);

    private EditDataButton editStdButton = new EditStudentButton(mTable, this);
    private EditDataButton editPrgButton = new EditProgramButton(mTable, this);
    private EditDataButton editClgButton = new EditCollegeButton(mTable, this);

    private ChangeToTableButton cStdTblButton = new ChangeToStudentTableButton(sp, mTable, 
        new AddDataButton[]{addStdButton, addPrgButton, addClgButton}, 
        new DeleteDataButton[]{delStdButton, delPrgButton, delClgButton},
        new EditDataButton[]{editStdButton, editPrgButton, editClgButton},
        searchBar, searchFieldList);
    private ChangeToTableButton cPrgTblButton = new ChangeToProgramTableButton(sp, mTable,
        new AddDataButton[]{addPrgButton, addStdButton, addClgButton}, 
        new DeleteDataButton[]{delPrgButton, delStdButton, delClgButton},
        new EditDataButton[]{editPrgButton, editStdButton, editClgButton},
        searchBar, searchFieldList);
    private ChangeToTableButton cClgTblButton = new ChangeToCollegeTableButton(sp, mTable, 
        new AddDataButton[]{addClgButton, addStdButton, addPrgButton},
        new DeleteDataButton[]{delClgButton, delPrgButton, delStdButton},
        new EditDataButton[]{editClgButton, editStdButton, editPrgButton},
        searchBar, searchFieldList);
    private ButtonGroup changeTableGroup = new ButtonGroup();

    private JLabel searchLabel = new JLabel("Search: ");
    private JLabel by = new JLabel("By: ");
        
    private JPanel content = new JPanel(new BorderLayout());

    private JPanel tools = new JPanel(new GridBagLayout());
    private JPanel table = new JPanel(new BorderLayout());

    private JPanel dataButtons = new JPanel(new GridBagLayout());
    private JPanel changeTables = new JPanel(new GridBagLayout());

    private GridBagConstraints dataButtonsGBC = new GridBagConstraints();
    private GridBagConstraints toolsGBC = new GridBagConstraints();
    private GridBagConstraints changeTablesGBC = new GridBagConstraints();

    private Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

    private SaveChecker saveChecker = new SaveChecker(saveButton);

    /**
     * Adds all components needed to the frame. Also adds the 
     * {@link main.app.frames.SaveChecker SaveChecker}.
     */
    public MainFrame(){
        this.setResizable(true);     
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Student Management System");
        this.setLayout(new BorderLayout());

        this.changeTableGroup.add(this.cStdTblButton);
        this.changeTableGroup.add(this.cPrgTblButton);
        this.changeTableGroup.add(this.cClgTblButton);

        this.dataButtonsGBC.gridx = 0; this.dataButtonsGBC.gridy = 0;
        this.dataButtonsGBC.insets = new Insets(0, 10, 0, 0);
        this.dataButtonsGBC.fill = GridBagConstraints.VERTICAL;
        this.dataButtons.add(this.saveButton, this.dataButtonsGBC);
        this.dataButtonsGBC.gridx = 1;
        this.dataButtons.add(this.addStdButton, this.dataButtonsGBC);
        this.dataButtons.add(this.addPrgButton, this.dataButtonsGBC);
        this.dataButtons.add(this.addClgButton, this.dataButtonsGBC);
        this.dataButtonsGBC.gridx = 2;
        this.dataButtons.add(this.delStdButton, this.dataButtonsGBC);
        this.dataButtons.add(this.delPrgButton, this.dataButtonsGBC);
        this.dataButtons.add(this.delClgButton, this.dataButtonsGBC);
        this.dataButtonsGBC.gridx = 3;
        this.dataButtons.add(this.editStdButton, this.dataButtonsGBC);
        this.dataButtons.add(this.editPrgButton, this.dataButtonsGBC);
        this.dataButtons.add(this.editClgButton, this.dataButtonsGBC);

        this.toolsGBC.gridx = 0; this.toolsGBC.gridy = 0;
        this.toolsGBC.insets = new Insets(0, 0, 15, 0);
        this.toolsGBC.anchor = GridBagConstraints.LINE_START;
        this.tools.add(searchLabel, toolsGBC); 
        
        this.toolsGBC.gridx = 1; 
        this.toolsGBC.insets = new Insets(0, 10, 15, 0);
        this.toolsGBC.fill = GridBagConstraints.VERTICAL;
        this.tools.add(searchBar, toolsGBC);

        this.toolsGBC.gridx = 2;
        this.toolsGBC.anchor = GridBagConstraints.WEST;
        this.toolsGBC.fill = GridBagConstraints.VERTICAL;
        
        this.tools.add(by, toolsGBC);

        this.toolsGBC.gridx = 3;
        this.toolsGBC.weightx = 10;
        this.tools.add(searchFieldList, toolsGBC);

        this.toolsGBC.fill = GridBagConstraints.BOTH;
        this.toolsGBC.gridx = 4; this.toolsGBC.fill = GridBagConstraints.NONE; 
        this.toolsGBC.anchor = GridBagConstraints.LINE_END;
        this.tools.add(dataButtons, toolsGBC);

        this.changeTablesGBC.gridx = 0; this.changeTablesGBC.gridy = 0; 
        this.changeTables.add(this.cStdTblButton, this.changeTablesGBC);
        this.changeTablesGBC.gridx = 1;
        this.changeTables.add(this.cPrgTblButton, this.changeTablesGBC);
        this.changeTablesGBC.gridx = 2; this.changeTablesGBC.weightx = 1;
        this.changeTablesGBC.anchor = GridBagConstraints.LINE_START;
        this.changeTables.add(this.cClgTblButton, this.changeTablesGBC); 
        
        this.table.add(this.changeTables, BorderLayout.NORTH);
        this.table.add(this.sp, BorderLayout.CENTER);
        
        this.content.add(this.tools, BorderLayout.NORTH);
        this.content.add(this.table, BorderLayout.CENTER);

        this.content.setBorder(this.padding);

        this.add(this.content, BorderLayout.CENTER);
        
        this.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 100, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 100);
        this.setLocationRelativeTo(null);
        this.setVisible(true);  
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        this.addWindowListener(this.saveChecker);

    }
}
