package main.app.buttons.pageHandler;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;

import main.app.frames.MainFrame;

public class PageHandlingButton extends JButton{
    public static final byte FIRST = 0;
    public static final byte PREV = 1;
    public static final byte NEXT = 2;
    public static final byte LAST = 3;
    private MainFrame mFrame;
    public PageHandlingButton (MainFrame mFrame, String actionText, byte action){
        
        super(actionText);
        this.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePageIndex(action);
                mFrame.initFilterAndButton();
            }
            
        });
        this.mFrame = mFrame;
    }

    private void changePageIndex(byte action){
        switch (action) {
            case FIRST:
                mFrame.setCurrentPageIndex(1);
                break;
            case PREV:
                mFrame.setCurrentPageIndex(mFrame.getCurrentPageIndex() - 1);
                break;
            case NEXT:
                mFrame.setCurrentPageIndex(mFrame.getCurrentPageIndex() + 1);
                break;
            case LAST:
                mFrame.setCurrentPageIndex(mFrame.getMaxPageIndex());
                break;
        }
    }
}
