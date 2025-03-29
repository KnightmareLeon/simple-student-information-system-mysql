package main.app.windows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

import main.app.tables.ManagementTable;
import main.app.tables.pageHandler.PageHandler;
import main.app.tables.tableModels.DatabaseHandlingTableModel;

public class SortingFormDialog extends JDialog implements ItemListener, ActionListener{
    private JLabel[] orderLabels;
    private JCheckBox[] checkBoxes;
    private ButtonGroup[] ascOrDesc;
    private JPanel[] ascOrDescPanel;
    private int order = 0;
    private JButton saveSortingButton = new JButton("Save Sorting Options");

    private ManagementTable mTable;
    private PageHandler pageHandler;
    public SortingFormDialog(ManagementTable mTable, PageHandler pageHandler){
        this.mTable = mTable;
        this.pageHandler = pageHandler;
        this.setResizable(false);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setTitle("Sorting Options");
        JPanel main = new JPanel(new BorderLayout());
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(0,3,10,10));
        int columnCount = mTable.getColumnCount();
        content.add(new JLabel("Sort Order"));
        content.add(new JLabel("Sort By"));
        content.add(new JLabel("ASC or DESC"));
        orderLabels = new JLabel[columnCount];
        checkBoxes = new JCheckBox[columnCount];
        ascOrDesc = new ButtonGroup[columnCount];
        ascOrDescPanel = new JPanel[columnCount];
        for(int i = 0; i < columnCount; i++){
            orderLabels[i] = new JLabel("-");
            checkBoxes[i] = new JCheckBox(mTable.getColumnName(i));
            checkBoxes[i].addItemListener(this);
            JToggleButton asc = new JToggleButton("ASC");
            JToggleButton desc = new JToggleButton("DESC");
            ascOrDesc[i] = new ButtonGroup();
            asc.setActionCommand(asc.getText());
            desc.setActionCommand(desc.getText());
            ascOrDesc[i].add(asc); ascOrDesc[i].add(desc);
            ascOrDesc[i].setSelected(asc.getModel(), true);
            ascOrDescPanel[i] = new JPanel(new GridLayout(0,2));
            ascOrDescPanel[i].add(asc); ascOrDescPanel[i].add(desc);
            
            content.add(orderLabels[i]);
            content.add(checkBoxes[i]);
            content.add(ascOrDescPanel[i]);
        }

        saveSortingButton.addActionListener(this);

        main.add(content, BorderLayout.CENTER);
        main.add(saveSortingButton, BorderLayout.SOUTH);
        main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.setContentPane(main);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        for(int i = 0; i < checkBoxes.length; i++){
            if(checkBoxes[i] == e.getItemSelectable() && checkBoxes[i].isSelected()){
                orderLabels[i].setText(Integer.toString(++order));
            } else if (checkBoxes[i] == e.getItemSelectable() && !checkBoxes[i].isSelected()){
                int orderRemoved = Integer.parseInt(orderLabels[i].getText());
                order--;
                for(int j = 0; j < orderLabels.length; j++){
                    if(!orderLabels[j].getText().equals("-")){
                        int currentOrder = Integer.parseInt(orderLabels[j].getText());
                        if(currentOrder > orderRemoved){
                            orderLabels[j].setText(Integer.toString(currentOrder - 1));
                        }
                    }
                }
                orderLabels[i].setText("-");
            }            
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == saveSortingButton){
            if(order != 0){
                String[] newSortingOptions = new String[order];
                for(int i = order - 1; i > -1; i--){
                    for(int j = 0; j < orderLabels.length; j++){
                        if(!orderLabels[j].getText().equals("-")){
                            if(Integer.parseInt(orderLabels[j].getText()) == i + 1){
                                newSortingOptions[i] = 
                                    mTable.getColumnName(j).replaceAll(" ", "") 
                                    + " " + ascOrDesc[j].getSelection().getActionCommand();
                            }
                        }
                    }
                }
                ((DatabaseHandlingTableModel) mTable.getModel()).setSortingOptions(newSortingOptions);
                pageHandler.setUpPageHandling();
                pageHandler.setPageText();
            }        
            this.dispose();
        }
    }
}
