package main.app.frames;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import main.app.buttons.SaveButton;

/**
 * A custom {@code WindowAdapter} that listens to when the {@code MainFrame} is
 * closing. It checks if there is any changes to the {@code ManagementTable},
 * and if there is, a dialog frame will pop out asking if the user wants to save
 * or not save the changes from {@code ManagementTable } to this applications's 
 * database. The {@code MainFrame} will then close if the user opts not to choose
 * the cancel option.
 * @see {@code MainFrame} - the main {@code JFrame} of this application
 * @see {@code ManagementTable} - the {@code JTable} that handles the viewing of
 * the data
 */
public class SaveChecker extends WindowAdapter {
    
    private SaveButton saveButton;
    public SaveChecker(SaveButton saveButton){
        this.saveButton = saveButton;
    }

    @Override
    public void windowClosing(WindowEvent evt){
        boolean close = true;
        if(saveButton.isUnsaved()){
            int option = JOptionPane.showConfirmDialog((Frame) evt.getSource(),
            "There are still unsaved changes. Would you like to save and close?",
            "Unsaved", JOptionPane.YES_NO_CANCEL_OPTION);
            if(option == JOptionPane.YES_OPTION){this.saveButton.save();     
            } else if (option != JOptionPane.NO_OPTION){close = false;}
        }
        if(close){System.exit(0);}
    }
}
