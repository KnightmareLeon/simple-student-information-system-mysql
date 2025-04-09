package main.app.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.app.tables.ManagementTable;
import main.app.tables.pageHandler.PageHandler;
import main.app.windows.SortingFormDialog;

public class SortingOptionsButton extends JButton implements ActionListener{

    private ManagementTable mTable;
    private PageHandler pageHandler;
    public SortingOptionsButton(ManagementTable mTable, PageHandler pageHandler){
        this.mTable = mTable;
        this.pageHandler = pageHandler;
        this.setText("Sorting Options");
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this){
            SortingFormDialog sortingFormDialog = new SortingFormDialog(mTable, pageHandler);
            sortingFormDialog.setVisible(true);
        }
    }
}
