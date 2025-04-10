package main.app.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Stack;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.KeyStroke;

import main.app.tables.pageHandler.PageHandler;
import main.app.undo.UndoAction;

/**
 * {@code JButton} class that handles the undo action of the
 * {@link main.app.buttons.UndoButton UndoButton}. The {@code UndoButton} class
 * is used to undo the last action performed on the database.
 */
public class UndoButton extends JButton{
    private Stack<UndoAction> undoStack = new Stack<>();
    private PageHandler pageHandler;
    private AbstractAction undoAction = new AbstractAction("Undo") {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!undoStack.empty()){
                undoStack.pop().undo();
                pageHandler.setUpPageHandling();
                pageHandler.setPageText();
            }
        }
        
    };
    public UndoButton(){
        this.setAction(undoAction);
        this.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK),
            "Undo");
        this.getActionMap().put("Undo",undoAction);
    }

    public void addUndoAction(UndoAction undoAction){
        this.undoStack.add(undoAction);
    }

    public void setPageHandler(PageHandler pageHandler){
        this.pageHandler = pageHandler;
    }


}
