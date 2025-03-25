package main.app.frames;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.border.Border;
import javax.swing.table.TableModel;

import main.app.buttons.add.*;
import main.app.buttons.changeTable.*;
import main.app.buttons.delete.*;
import main.app.buttons.edit.*;
import main.app.buttons.pageHandler.PageHandlingButton;
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

    private final int itemsPerPage = 50;
    private int maxPageIndex;
    private int currentPageIndex = 1;
    private int rowCount;
    private int v;

    private ManagementTable mTable = new ManagementTable();
    private JScrollPane sp = new JScrollPane(this.mTable);

    private SearchFieldList searchFieldList = new SearchFieldList(this.mTable);
    private SearchBar searchBar = new SearchBar(this.mTable.getRowSorter(), this.searchFieldList);

    private AddDataButton addStdButton = new AddStudentButton(mTable, this);
    private AddDataButton addPrgButton = new AddProgramButton(mTable, this);
    private AddDataButton addClgButton = new AddCollegeButton(mTable, this);
    
    private DeleteDataButton delStdButton = new DeleteStudentButton(mTable, this);
    private DeleteDataButton delPrgButton = new DeleteProgramButton(mTable, this);
    private DeleteDataButton delClgButton = new DeleteCollegeButton(mTable, this);

    private EditDataButton editStdButton = new EditStudentButton(mTable, this);
    private EditDataButton editPrgButton = new EditProgramButton(mTable, this);
    private EditDataButton editClgButton = new EditCollegeButton(mTable, this);

    private ChangeToTableButton cStdTblButton = new ChangeToStudentTableButton(this, sp, mTable, 
        new AddDataButton[]{addStdButton, addPrgButton, addClgButton}, 
        new DeleteDataButton[]{delStdButton, delPrgButton, delClgButton},
        new EditDataButton[]{editStdButton, editPrgButton, editClgButton},
        searchBar, searchFieldList);
    private ChangeToTableButton cPrgTblButton = new ChangeToProgramTableButton(this, sp, mTable,
        new AddDataButton[]{addPrgButton, addStdButton, addClgButton}, 
        new DeleteDataButton[]{delPrgButton, delStdButton, delClgButton},
        new EditDataButton[]{editPrgButton, editStdButton, editClgButton},
        searchBar, searchFieldList);
    private ChangeToTableButton cClgTblButton = new ChangeToCollegeTableButton(this, sp, mTable, 
        new AddDataButton[]{addClgButton, addStdButton, addPrgButton},
        new DeleteDataButton[]{delClgButton, delPrgButton, delStdButton},
        new EditDataButton[]{editClgButton, editStdButton, editPrgButton},
        searchBar, searchFieldList);
    private ButtonGroup changeTableGroup = new ButtonGroup();

    private final PageHandlingButton first = new PageHandlingButton(this, "|<", PageHandlingButton.FIRST);
    private final PageHandlingButton prev  = new PageHandlingButton(this, "<", PageHandlingButton.PREV);
    private final PageHandlingButton next = new PageHandlingButton(this, ">", PageHandlingButton.NEXT);
    private final PageHandlingButton last = new PageHandlingButton(this, ">|", PageHandlingButton.LAST);

    private final JTextField pageField = new JTextField(3);

    private JLabel searchLabel = new JLabel("Search: ");
    private JLabel by = new JLabel("By: ");

    private JPanel content = new JPanel(new BorderLayout());

    private JPanel tools = new JPanel(new GridBagLayout());
    private JPanel table = new JPanel(new BorderLayout());
    private JPanel po = new JPanel();
    private JPanel pageHandlingButtons = new JPanel();
    private final JLabel pageLabel = new JLabel();

    private JPanel dataButtons = new JPanel(new GridBagLayout());
    private JPanel changeTables = new JPanel(new GridBagLayout());

    private GridBagConstraints dataButtonsGBC = new GridBagConstraints();
    private GridBagConstraints toolsGBC = new GridBagConstraints();
    private GridBagConstraints changeTablesGBC = new GridBagConstraints();

    private Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);

    /**
     * Adds all components needed to the frame. Also adds the 
     * {@link main.app.frames.SaveChecker SaveChecker}.
     */
    public MainFrame(){

        this.setResizable(true);     
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Student Management System");
        this.setLayout(new BorderLayout());

        this.changeTableGroup.add(this.cStdTblButton);
        this.changeTableGroup.add(this.cPrgTblButton);
        this.changeTableGroup.add(this.cClgTblButton);

        this.dataButtonsGBC.gridx = this.dataButtonsGBC.gridy = 0;
        this.dataButtonsGBC.insets = new Insets(0, 10, 0, 0);
        this.dataButtonsGBC.fill = GridBagConstraints.VERTICAL;
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

        this.po.add(pageField);
        this.po.add(pageLabel);

        this.pageHandlingButtons.add(first);
        this.pageHandlingButtons.add(prev);
        this.pageHandlingButtons.add(po);
        this.pageHandlingButtons.add(next);
        this.pageHandlingButtons.add(last);

        this.content.add(this.tools, BorderLayout.NORTH);
        this.content.add(this.table, BorderLayout.CENTER);
        this.content.add(this.pageHandlingButtons, BorderLayout.SOUTH);

        this.content.setBorder(this.padding);

        this.add(this.content, BorderLayout.CENTER);
        
        this.setUpPageHandling();
        this.setPageText();
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        this.pageField.getInputMap(JComponent.WHEN_FOCUSED).put(enter, "Enter");
        this.pageField.getActionMap().put("Enter", new AbstractAction() {
        @Override public void actionPerformed(ActionEvent e) {
            try {
            int v = Integer.parseInt(pageField.getText());
                if(v>0 && v<=maxPageIndex) {
                    currentPageIndex = v;
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
            
            initFilterAndButton();
            }
        });
        this.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 100, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 100);
        this.setLocationRelativeTo(null);
        this.setVisible(true);  
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

    }

    public void initFilterAndButton() {
        mTable.getRowSorter().setRowFilter(new RowFilter<TableModel, Integer>() {
            @Override public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                int ti = currentPageIndex - 1;
                int ei = entry.getIdentifier();
                return ti*itemsPerPage<=ei && ei<ti*itemsPerPage+itemsPerPage;
            }
        });
        first.setEnabled(currentPageIndex>1);
        prev.setEnabled(currentPageIndex>1);
        next.setEnabled(currentPageIndex<maxPageIndex);
        last.setEnabled(currentPageIndex<maxPageIndex);
        pageField.setText(Integer.toString(currentPageIndex));
    }

    public int getCurrentPageIndex(){return this.currentPageIndex;}
    public void setCurrentPageIndex(int currentPageIndex){this.currentPageIndex = currentPageIndex;}

    public int getMaxPageIndex(){return this.maxPageIndex;}
    public void setMaxPageIndex(int maxPageIndex){this.maxPageIndex = maxPageIndex;}

    public int getRowCount(){return this.rowCount;}
    public void setRowCount(int rowCount){this.rowCount = rowCount;}

    public void setPageText(){this.pageLabel.setText(String.format("/ %d", maxPageIndex));}
    public void setUpPageHandling(){
        rowCount = mTable.getModel().getRowCount();
        v = rowCount % itemsPerPage == 0 ? 0 : 1;
        maxPageIndex = rowCount / itemsPerPage + v;
        initFilterAndButton();
    }
}


