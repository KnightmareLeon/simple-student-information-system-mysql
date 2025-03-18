package main.app.buttons.delete;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import main.app.buttons.DataButton;
import main.app.errors.NoRowSelectedException;
import main.app.frames.MainFrame;
import main.app.tables.ManagementTable;

/**
 * Abstract {@link JButton} that will delete the data type that will be handled by its child
 * classes: {@link DeleteStudentButton}, {@link DeleteProgramButton}, and
 * {@link DeleteCollegeButton}. Sets the icon downloaded from 
 * https://www.svgrepo.com/svg/78105/subtract.
 */
public abstract class DeleteDataButton extends DataButton {
    private ImageIcon thrashCan = new ImageIcon("Student Management/src/resources/icons/subtract-svgrepo-com.png");
    private Image thrashCanImg = thrashCan.getImage();
    private Image scaledImg = thrashCanImg.getScaledInstance((int)(WIDTH * 0.13),(int)(HEIGHT * 0.55), Image.SCALE_SMOOTH);
    public DeleteDataButton(ManagementTable mTable, MainFrame mainFrame){
        super(mainFrame);
        this.setActionText("Delete ");
        this.setIcon(new ImageIcon(this.scaledImg));
        this.setBackground(new Color(139,0,0));
        this.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                int row = mTable.getSelectedRowCount();
                try{
                    if(row == 0){throw new NoRowSelectedException();}
                    else{
                        int confirm = JOptionPane.showConfirmDialog(
                            mainFrame,
                            "Please confirm deletion action.",
                            "Confirm Deletion",
                            JOptionPane.YES_NO_OPTION);
                        if(confirm == JOptionPane.YES_OPTION){
                            if(delete(mTable)){
                                JOptionPane.showMessageDialog(mainFrame, 
                                getDataText() + "(s) successfully deleted!");
                            }
                            
                        }
                    }
                          
                } catch(NoRowSelectedException e){
                    e.printStackTrace();
                    e.startErrorWindow(mainFrame);
                }
            }
        });

    }

    protected abstract boolean delete(ManagementTable mTable);
}
