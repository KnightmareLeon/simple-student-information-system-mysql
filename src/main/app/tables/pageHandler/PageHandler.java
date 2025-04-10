package main.app.tables.pageHandler;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import main.app.buttons.pageHandler.PageHandlingButton;
import main.app.input.fields.SearchBar;
import main.app.input.fields.SearchFieldList;
import main.app.tables.ManagementTable;
import main.app.tables.tableModels.DatabaseHandlingTableModel;


/**
 * {@code JPanel} class that handles the pagination of a table.
 * It contains buttons to navigate through the pages and a text field
 * to enter the page number.
 * @see ManagementTable {@code ManagementTable}
 */
public class PageHandler extends JPanel{
    private final int ITEM_PER_PAGE = 50;
    private int maxPageIndex;
    private int currentPageIndex = 1;
    private int rowCount;
    private int v;
    
    private final JPanel po = new JPanel();
    private final JTextField pageField = new JTextField(3);
    private final JLabel pageLabel = new JLabel();
    private final PageHandlingButton first;
    private final PageHandlingButton prev;
    private final PageHandlingButton next;
    private final PageHandlingButton last; 

    private ManagementTable mTable;
    private SearchBar searchBar;
    private SearchFieldList searchFieldList;

    private boolean searching = false;

    public PageHandler(ManagementTable mTable, SearchBar searchBar, SearchFieldList searchFieldList){
        this.mTable = mTable; 
        this.searchBar = searchBar;
        this.searchFieldList = searchFieldList;

        this.pageField.setHorizontalAlignment(JTextField.RIGHT);
        this.po.add(this.pageField); this.po.add(this.pageLabel);
        
        first = new PageHandlingButton(this, "|<", PageHandlingButton.FIRST);
        prev  = new PageHandlingButton(this, "<", PageHandlingButton.PREV);
        next = new PageHandlingButton(this, ">", PageHandlingButton.NEXT);
        last = new PageHandlingButton(this, ">|", PageHandlingButton.LAST);

        this.add(this.first);
        this.add(this.prev);
        this.add(this.po);
        this.add(this.next);
        this.add(this.last);

        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        pageField.getInputMap(JComponent.WHEN_FOCUSED).put(enter, "Enter");
        pageField.getActionMap().put("Enter", new AbstractAction() {
        
            @Override public void actionPerformed(ActionEvent e) {
                try {
                    int v = Integer.parseInt(pageField.getText());
                        if(v > 0 && v <= maxPageIndex) {
                            currentPageIndex = v;
                        }
                    } catch(Exception ex) {
                        ex.printStackTrace();
                }
                    
                initFilterAndButton();
            }
        });
    }

    public int getCurrentPageIndex(){return currentPageIndex;}
    public void setCurrentPageIndex(int currentPageIndex){this.currentPageIndex = currentPageIndex;}

    public void setRowCount(){
        try {
            if(!searching){
                rowCount = ((DatabaseHandlingTableModel) mTable.getModel()).getTotalRows();
            } else {
                rowCount = ((DatabaseHandlingTableModel) mTable.getModel()).getTotalRows(
                    (String) searchFieldList.getSelectedItem(), searchBar.getText()
                );
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getRowCount(){return rowCount;}
    
    public void setMaxPageIndex(int maxPageIndex){this.maxPageIndex = maxPageIndex;}
    public void setMaxPageIndex(){
        v = rowCount % ITEM_PER_PAGE == 0 ? 0 : 1;
        maxPageIndex = rowCount / ITEM_PER_PAGE + v;
    }
    
    public int getMaxPageIndex(){return maxPageIndex;}

    public void setToSearching(){this.searching = true;}
    public void setToNotSearching(){this.searching = false;}
    
    public int getItemPerPage(){return this.ITEM_PER_PAGE;}

    public void setPageText(){pageLabel.setText(String.format("/ %d", maxPageIndex));}
    public void setUpPageHandling(){
        setRowCount();
        setMaxPageIndex();
        initFilterAndButton();
    }

    public void initFilterAndButton() {
        if(currentPageIndex > maxPageIndex){
            currentPageIndex = maxPageIndex;
        }
        if(currentPageIndex == 0 && maxPageIndex > currentPageIndex){
            currentPageIndex = 1;
        }
        if(!searching && rowCount > 0){
            ((DatabaseHandlingTableModel)mTable.getModel()).getData(currentPageIndex);
        } else if (rowCount > 0){
            ((DatabaseHandlingTableModel)mTable.getModel()).getData(
                currentPageIndex,
                (String) searchFieldList.getSelectedItem(),
                searchBar.getText()    
            );
        } else {
            ((DatabaseHandlingTableModel) mTable.getModel()).setRowCount(0);
        }
        
        first.setEnabled(currentPageIndex > 1);
        prev.setEnabled(currentPageIndex > 1);
        next.setEnabled(currentPageIndex < maxPageIndex);
        last.setEnabled(currentPageIndex < maxPageIndex);
        pageField.setText(Integer.toString(currentPageIndex));
    }
}
