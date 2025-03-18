package main.app.buttons.edit;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import main.app.errors.EmptyInputException;
import main.app.errors.ExistingCodeException;
import main.app.errors.ExistingNameException;
import main.app.errors.MultiEditCollegeException;
import main.app.errors.NoRowSelectedException;
import main.app.frames.MainFrame;
import main.app.input.CollegeInput;
import main.app.input.InputType;
import main.app.tables.ManagementTable;

/**
 * {@code JButton} class that sets up all components needed to edit a
 * {@link main.data.dataClass.College College}'s data. The action button that 
 * it sets up will send the new data to the {@link main.data.maps.DataMap 
 * DataMap} and {@link main.app.tables.ManagementTable ManagementTable} when 
 * clicked.
 * @see CollegeInput {@code CollegeInput}
 */
public class EditCollegeButton extends EditDataButton{
    private CollegeInput clgInput;
    public EditCollegeButton(ManagementTable mTable, MainFrame mainFrame) {
        super(mTable, mainFrame);
        this.setDataText("College");
        this.setText(this.getActionDataText());
        this.setVisible(false);
    }

    @Override
    protected void setUpComponents(ManagementTable mTable) throws NoRowSelectedException, MultiEditCollegeException {
        this.getDataFrame().setTitle("Edit College");
        if(mTable.getSelectedRowCount() <= 1){
            clgInput = new CollegeInput(this.getDataFrame(), mTable, this.getGBC(), InputType.EDIT_SINGLE);
        } else {
            throw new MultiEditCollegeException();
        }
        
        this.getActionButton().setText("Edit College");
        this.getActionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                editData(mTable); 
            }
        });

        //Adding Components

        this.getGBC().gridx = 0; this.getGBC().gridy = 2; this.getGBC().gridwidth = 3; 
        this.getGBC().fill = GridBagConstraints.HORIZONTAL;
        this.getDataFrame().add(this.getActionButton(),this.getGBC());

    }

    @Override
    protected void editData(ManagementTable mTable) {
        try{
            int row = mTable.getSelectedRow();
            if(mTable.getDMap().hasCollegeCode(clgInput.getCode())
            && !mTable.getValueAt(row, 0).equals(clgInput.getCode())){
                throw new ExistingCodeException();
            } else if (mTable.getDMap().hasCollegeName(clgInput.getName())
                && !mTable.getValueAt(row, 1).equals(clgInput.getName())){
                throw new ExistingNameException();
            } 
            String[] data = {
                clgInput.getCode(),
                clgInput.getName()
            };
            mTable.getCTM().editData(mTable.convertRowIndexToModel(row), data, mTable.getDMap());
            JOptionPane.showMessageDialog(getActionButton(), "College edited successfully!");
            getDataFrame().dispose();
        } catch(EmptyInputException | ExistingCodeException | ExistingNameException e){
            e.printStackTrace();
            e.startErrorWindow(getActionButton());
        }
    }
}
