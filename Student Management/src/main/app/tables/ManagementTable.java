package main.app.tables;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import main.app.tables.tableModels.CollegeTableModel;
import main.app.tables.tableModels.ProgramTableModel;
import main.app.tables.tableModels.StudentTableModel;
import main.data.maps.DataMap;

/**
 * Custom {@code JTable} that the application uses. Has three initialized
 * {@link main.app.tables.tableModels.CSVHandlingTableModel CSVHandlingTableModels}:
 * the {@link main.app.tables.tableModels.StudentTableModel StudentTableModel}, 
 * the {@link main.app.tables.tableModels.ProgramTableModel ProgramTableModel}, and
 * the {@link main.app.tables.tableModels.CollegeTableModel CollegeTableModel}.
 * Also initializes the {@link main.data.maps.DataMap DataMap}. The table is first
 * set with the {@code StudentTableModel}. The {@link main.app.buttons.changeTable.ChangeToTableButton
 * changeToTableButtons} will handle changing the set table model.
 */
public class ManagementTable extends JTable{
    private CollegeTableModel ctm;
    private ProgramTableModel ptm;
    private StudentTableModel stm;
    private TableRowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>();
    private DataMap dMap = new DataMap();
    private List<RowSorter.SortKey> sortKeys = new ArrayList<>();

    /**
     * Sets up all the {@link main.app.tables.tableModels.CSVHandlingTableModel
     * CSVHandlingTableModels} and specific {@code JTable} customizations.
     */
    public ManagementTable(){
        this.ctm = new CollegeTableModel(this.dMap);
        this.ptm = new ProgramTableModel(this.dMap);
        this.stm = new StudentTableModel(this.dMap);
        this.ctm.setPTM(this.ptm);
        this.ptm.setSTM(this.stm);
        //this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setModel(this.stm);
        this.getTableHeader().setResizingAllowed(false);
    }

    /**
     * Gets the {@code DataMap} that the 
     * {@code ManagementTable} uses.
     * @return {@link main.data.maps.DataMap DataMap}
     */
    public DataMap getDMap(){return dMap;}

    /**
     * Gets the {@code CollegeTableModel}that the {@code ManagementTable} uses.
     * @return {@link main.app.tables.tableModels.CollegeTableModel
     * CollegeTableModel} 
     */
    public CollegeTableModel getCTM(){return ctm;}

    /**
     * Gets the {@code ProgramTableModel}that the {@code ManagementTable} uses.
     * @return {@link main.app.tables.tableModels.ProgramTableModel
     * ProgramTableModel} 
     */
    public ProgramTableModel getPTM(){return ptm;}

    /**
     * Gets the {@code StudentTableModel}that the {@code ManagementTable} uses.
     * @return {@link main.app.tables.tableModels.StudentTableModel
     * StudentTableModel} 
     */
    public StudentTableModel getSTM(){return stm;}

    public TableRowSorter<TableModel> getRowSorter(){return this.rowSorter;}
    
    /**
     * Does the original {@link javax.swing.JTable#setModel(TableModel) setModel} from
     * {@code JTable} but also sets the {@link #rowSorter} model, programatically
     * sorts specific columns using {@link #initialSort()}, and adjusts columns widths
     * based on the {@code CSVHandlingTableModel} that was set using {@link #setColumns}.
     */
    @Override
    public void setModel(TableModel tableModel){
        super.setModel(tableModel);
        if(this.rowSorter != null){
            this.rowSorter.setModel(tableModel);
            this.setRowSorter(this.rowSorter);
            this.initialSort();  
        }
        if(tableModel == this.stm){
            this.setColumns(10, 30, 30, 10, 10, 10);
        } else if(tableModel == this.ptm){
            this.setColumns(10, 80, 10);
        } else if(tableModel == this.ctm){
            this.setColumns(10,90);
        }
    }

    /**
     * Programatically sorts the {@code ManagementTable} by the first and last 
     * columns.
     * 
     *  */ 
    private void initialSort(){
        this.sortKeys.clear();
        this.sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        this.sortKeys.add(new RowSorter.SortKey(this.getColumnCount() - 1, SortOrder.ASCENDING));
        this.rowSorter.setSortKeys(this.sortKeys);
        this.rowSorter.sort();
    }

    /**
     * Sets the column width ratio using the {@code percentages} that
     * was provided. Also sets the horizontal alignment of the 
     * {@code ManagementTable}'s cells to center.
     * @param percentages {@code double} values
     */
    private void setColumns(double... percentages){
        final double TOTAL = 100;
        int width = (this.getWidth() != 0) ? this.getWidth(): 1514;
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i = 0; i < this.getColumnCount(); i++){
            this.getColumnModel().getColumn(i).setPreferredWidth((int) (width * (percentages[i] / TOTAL)));    
            this.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
    }
}