package main.app.windows;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;

import main.app.tables.ManagementTable;

public class SortingFormDialog extends JDialog{
    public SortingFormDialog(ManagementTable mTable){
        this.setResizable(false);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setTitle("Sorting Options");
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(0,3,10,10));
        int columnCount = mTable.getColumnCount();
        int order = 0;
        content.add(new JLabel("Sort Order"));
        content.add(new JLabel("Sort By"));
        content.add(new JLabel("ASC or DESC"));
        JLabel[] orderLabels = new JLabel[columnCount];
        JCheckBox[] checkBoxes = new JCheckBox[columnCount];
        ButtonGroup[] ascOrDesc = new ButtonGroup[columnCount];
        JPanel[] ascOrDescPanel = new JPanel[columnCount];
        for(int i = 0; i < columnCount; i++){
            orderLabels[i] = new JLabel("-");
            checkBoxes[i] = new JCheckBox(mTable.getColumnName(i));
            JToggleButton asc = new JToggleButton("ASC");
            JToggleButton desc = new JToggleButton("DESC");
            ascOrDesc[i] = new ButtonGroup();
            ascOrDesc[i].add(asc); ascOrDesc[i].add(desc);
            ascOrDesc[i].setSelected(asc.getModel(), true);
            ascOrDescPanel[i] = new JPanel(new GridLayout(0,2));
            ascOrDescPanel[i].add(asc); ascOrDescPanel[i].add(desc);
            
            content.add(orderLabels[i]);
            content.add(checkBoxes[i]);
            content.add(ascOrDescPanel[i]);
        }

        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.setContentPane(content);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
}
