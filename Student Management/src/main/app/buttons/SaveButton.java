package main.app.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import main.app.frames.MainFrame;
import main.app.tables.ManagementTable;

/**
 * Saves the {@link main.app.tables.ManagementTable ManagementTable}
 * changes to the CSV files.
 */
public class SaveButton extends JButton{
    private ManagementTable mTable;
    private MainFrame mainFrame;
    public SaveButton(ManagementTable mTable, MainFrame mainFrame){
        this.mTable = mTable;
        this.mainFrame = mainFrame;
        this.setText("Save");
        this.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if(isUnsaved()){save();}
            }
            
        });
    }

    public void save(){
        if(this.mTable.getSTM().isChanged()){this.mTable.getSTM().saveData();}
        if(this.mTable.getPTM().isChanged()){this.mTable.getPTM().saveData();}
        if(this.mTable.getCTM().isChanged()){this.mTable.getCTM().saveData();}
        JOptionPane.showMessageDialog(mainFrame, "Saved successfully!");   
    }

    public boolean isUnsaved(){
        return 
        this.mTable.getSTM().isChanged() ||
        this.mTable.getPTM().isChanged() ||
        this.mTable.getCTM().isChanged();
    }
}
