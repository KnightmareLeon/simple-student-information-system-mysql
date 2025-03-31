package main.app.tables;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import main.app.tables.pageHandler.PageHandler;
import main.app.tables.tableModels.CollegeTableModel;
import main.app.tables.tableModels.ProgramTableModel;
import main.app.tables.tableModels.StudentTableModel;
import main.database.DatabaseDriver;

/**
 * Custom {@code JTable} that the application uses. Has three initialized
 * {@link main.app.tables.tableModels.DatabaseHandlingTableModel CSVHandlingTableModels}:
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
    private DatabaseDriver dbDriver;

    /**
     * Sets up all the {@link main.app.tables.tableModels.DatabaseHandlingTableModel
     * DatabaseHandlingTableModels} and specific {@code JTable} customizations.
     */
    public ManagementTable(DatabaseDriver dbDriver){
        this.dbDriver = dbDriver;
        this.ctm = new CollegeTableModel(this.dbDriver);
        this.ptm = new ProgramTableModel(this.dbDriver);
        this.stm = new StudentTableModel(this.dbDriver);
        this.setModel(this.stm);
        this.getTableHeader().setResizingAllowed(false);
        this.getTableHeader().setReorderingAllowed(false);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                if(e.getButton()== MouseEvent.BUTTON3){
                    clearSelection();
                }
            }
        });
    }

    public DatabaseDriver getdBDriver(){return this.dbDriver;}
    
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
    
    /**
     * Does the original {@link javax.swing.JTable#setModel(TableModel) setModel} from
     * {@code JTable} and adjusts columns widths based on the {@code DatabaseHandlingTableModel} 
     * that was set using {@link #setColumns}.
     */
    @Override
    public void setModel(TableModel tableModel){
        super.setModel(tableModel);
        if(tableModel == this.stm){
            this.setColumns(10, 30, 30, 10, 10, 10);
        } else if(tableModel == this.ptm){
            this.setColumns(10, 80, 10);
        } else if(tableModel == this.ctm){
            this.setColumns(10,90);
        }
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
            this.getColumnModel().getColumn(i).setPreferredWidth(
                (int) (width * (percentages[i] / TOTAL)));    
            this.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
    }

    public void setPageHandler(PageHandler pageHandler){
        stm.setPageHandler(pageHandler);
        ptm.setPageHandler(pageHandler);
        ctm.setPageHandler(pageHandler);
    }
}