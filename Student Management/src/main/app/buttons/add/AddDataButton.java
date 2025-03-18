package main.app.buttons.add;

import java.awt.Color;
import java.awt.Image;


import javax.swing.ImageIcon;

import main.app.buttons.DataFormButton;
import main.app.frames.MainFrame;
import main.app.tables.ManagementTable;

/**
 * Abstract {@link DataFormButton} that will add the data type that will be handled by its child
 * classes: {@link AddStudentButton}, {@link AddProgramButton}, and
 * {@link AddCollegeButton}. Sets the icon downloaded from 
 * https://www.svgrepo.com/svg/125067/plus-sign.
 */
public abstract class AddDataButton extends DataFormButton{
    private ImageIcon plus = new ImageIcon(ICON_FILE_DIRECTORY + "plus-sign-svgrepo-com.png");
    private Image plusImg = plus.getImage();
    private Image scaledImg = plusImg.getScaledInstance((int)(WIDTH * 0.13),(int)(HEIGHT * 0.55), Image.SCALE_SMOOTH);
    public AddDataButton(ManagementTable mTable, MainFrame mainFrame) {
        super(mTable, mainFrame);
        this.setActionText("Add ");
        this.setIcon(new ImageIcon(this.scaledImg));
        this.setBackground(new Color(1, 50, 32));
    }

}
