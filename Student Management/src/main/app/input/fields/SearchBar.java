package main.app.input.fields;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import main.app.tables.pageHandler.PageHandler;

/**
 * A custom {@code JTextField} that is a child class of {@link UpperCaseTextField}.
 * Filters a {@code JTable} by whatever is the input text of this text field 
 * through a {@code TableRowSorter}. The columns that it filters is based on 
 * {@link SearchFieldList}, if the chosen item is "Any", this will filter
 * the {@code JTable} based on any column, else it will be based on the
 * chosen choice of the user
 * 
 * <p>
 * For this application, it filters the {@code ManagementTable}.
 * @see UpperCaseTextField {@code UpperCaseTextField}
 * @see main.app.tables.ManagementTable {@code ManagementTable}
 */
public class SearchBar extends UpperCaseTextField{

    private PageHandler pageHandler;
    public SearchBar(){
        this.getInputMap(JComponent.WHEN_FOCUSED).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), 
            "Search"
        );
        this.getActionMap().put("Search", new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(getText().length() > 0){
                    pageHandler.setToSearching();
                    
                } else{
                    pageHandler.setToNotSearching();
                }
                pageHandler.setCurrentPageIndex(1);
                pageHandler.setUpPageHandling();
                pageHandler.setPageText();
            }
            
        });
    }

    public void setPageHandler(PageHandler pageHandler){this.pageHandler = pageHandler;}
}
