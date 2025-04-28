package main.app.input.fields;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.AbstractDocument;

import main.app.input.filters.SearchBarFilter;
import main.app.tables.pageHandler.PageHandler;

/**
 * A custom {@code JTextField} that is a child class of {@link UpperCaseTextField}.
 * When the user presses "Enter". The {@link PageHandler} gets the text from this
 * and uses it to for search substrings based on the column of choice in the 
 * {@link SearchFieldList}. The searching is perforemed through a MySQL query
 * handled by a method in the {@code DatabaseDriver}.
 * <p>
 * @see main.app.tables.ManagementTable {@code ManagementTable}
 */
public class SearchBar extends JTextField{

    private PageHandler pageHandler;
    private SearchBarFilter filter;
    public SearchBar(){
        super(50);
        filter = new SearchBarFilter(50);
        ((AbstractDocument) this.getDocument()).setDocumentFilter(filter);
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
