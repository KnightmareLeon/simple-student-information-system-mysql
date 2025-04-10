package main.app.buttons.pageHandler;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import main.app.tables.pageHandler.PageHandler;


/**
 * {@code JButton} class that handles the pagination of a table.
 * It contains buttons to navigate through the pages.
 * @see PageHandler {@code PageHandler}
 */
public class PageHandlingButton extends JButton{
    public static final byte FIRST = 0;
    public static final byte PREV = 1;
    public static final byte NEXT = 2;
    public static final byte LAST = 3;
    private PageHandler pageHandler;
    public PageHandlingButton (PageHandler pageHandler, String actionText, byte action){
        
        super(actionText);
        this.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePageIndex(action);
                pageHandler.initFilterAndButton();
            }
            
        });
        this.pageHandler = pageHandler;
    }

    private void changePageIndex(byte action){
        switch (action) {
            case FIRST:
                pageHandler.setCurrentPageIndex(1);
                break;
            case PREV:
                pageHandler.setCurrentPageIndex(pageHandler.getCurrentPageIndex() - 1);
                break;
            case NEXT:
                pageHandler.setCurrentPageIndex(pageHandler.getCurrentPageIndex() + 1);
                break;
            case LAST:
                pageHandler.setCurrentPageIndex(pageHandler.getMaxPageIndex());
                break;
        }
    }
}
