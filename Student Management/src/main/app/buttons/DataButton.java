package main.app.buttons;

import java.awt.Dimension;

import javax.swing.JButton;

import main.app.frames.MainFrame;

public class DataButton extends JButton{
    
    private String actionText;
    private String data;
    protected MainFrame mainFrame;
    protected final int WIDTH = 135;
    protected final int HEIGHT = 30;
    protected final String ICON_FILE_DIRECTORY = "Student Management/src/resources/icons/";

    public DataButton(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
    }
    /**
     * Sets what kind of action that the {@code DataButton} will do
     * @param actionText - {@code String} value that is either
     * <b>Add</b> or <b>Edit</b>
     */
    protected void setActionText(String actionText){this.actionText = actionText;}

    /**
     * Sets what kind of data that is being handled.
     * @param data - {@code String} value that is either 
     * <b>Student</b>, <b>Program</b>, or <b>College</b>
     */
    protected void setDataText(String data){this.data = data;}

    protected String getDataText(){return this.data;}
    /**
     * Gets both the action and data text
     * @return {@link #actionText} and {@link #data}
     */
    protected String getActionDataText(){return actionText + data;}
}
