package main.app.buttons;

import java.awt.Dimension;

import javax.swing.JButton;

import main.app.windows.MainFrame;

/**
 * {@code JButton} class that is used to set up the action
 * and data text of the button. This class is extended by
 * {@link main.app.buttons.add.AddDataButton AddDataButton},
 * {@link main.app.buttons.delete.DeleteDataButton DeleteDataButton},
 * and {@link main.app.buttons.edit.EditDataButton EditDataButton}.
 */
public class DataButton extends JButton{
    
    private String actionText;
    private String data;
    protected MainFrame mainFrame;
    protected final int WIDTH = 135;
    protected final int HEIGHT = 30;
    protected final String ICON_FILE_DIRECTORY = "src/resources/icons/";

    public DataButton(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        this.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
    }
    /**
     * Sets what kind of action that the {@code DataButton} will do
     * @param actionText - {@code String} value that is either
     * <b>Add</b>, <b>Delete</b>, or <b>Edit</b>
     */
    protected void setActionText(String actionText){this.actionText = actionText;}

    /**
     * Sets what kind of data that is being handled.
     * @param data - {@code String} value that is either 
     * <b>Student</b>, <b>Program</b>, or <b>College</b>
     */
    protected void setDataText(String data){this.data = data;}

    /**
     * Gets both the data text
     * @return {@link #actionText} and {@link #data}
     */
    protected String getDataText(){return this.data;}
    
    /**
     * Gets both the action and data text
     * @return {@link #actionText} and {@link #data}
     */
    protected String getActionDataText(){return this.actionText + this.data;}
}
