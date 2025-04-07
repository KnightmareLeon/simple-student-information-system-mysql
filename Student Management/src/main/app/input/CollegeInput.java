package main.app.input;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;

import main.app.errors.EmptyInputException;
import main.app.errors.NoRowSelectedException;
import main.app.input.fields.UpperCaseOnlyTextField;
import main.app.tables.ManagementTable;
import main.app.windows.DataFormDialog;

/**
 * <p>
 * Sets up the components needed to add or update data to the colleges 
 * table of this application's designated MySQL database. 
 * </p>
 * @see StudentInput {@code StudentInput}
 * @see ProgramInput {@code ProgramInput}
 */
public class CollegeInput extends DataInput{
    //Input Labels
    private JLabel codeLabel = new JLabel("Code:");
    private JLabel nameLabel = new JLabel("Name:");

    //Input Fields
    private JTextField codeField = new UpperCaseOnlyTextField(5);
    private JTextField nameField = new UpperCaseOnlyTextField(50);

    /**
     * 
     * @param dataFormDialog - this app's custom {@code JDialog} in which the components 
     * for adding or editing data will be displayed.
     * @param mTable - the app's custom {@code JTable} for displaying data.
     * @param dialogGBC - {@code GridBagConstrainsts} of the {@code DataFormDialog}.
     * @param inputType - either {@code ADD} or {@code EDIT_SINGLE}.
     * @throws NoRowSelectedException when user doesn't select at least one row in the 
     * {@code ManagementTable}.
     */
    public CollegeInput(DataFormDialog dataFormDialog,
                        ManagementTable mTable,
                        GridBagConstraints dialogGBC,
                        InputType inputType) throws NoRowSelectedException{
        super(inputType);
        this.setUpComponents(dataFormDialog, mTable, dialogGBC);
    }

    @Override
    protected void setUpComponents(DataFormDialog dFrame,
                                   ManagementTable mTable, 
                                   GridBagConstraints dialogGBC) throws NoRowSelectedException{
        this.codeField.setPreferredSize(new Dimension(70, 20));
        this.nameField.setPreferredSize(new Dimension(400, 20));
        dialogGBC.insets = new Insets(5, 5, 5, 5);
        dialogGBC.fill = GridBagConstraints.HORIZONTAL; 
        dialogGBC.ipady = 10;
        dialogGBC.gridx = dialogGBC.gridy = 0; dFrame.add(codeLabel,dialogGBC);
        dialogGBC.gridy = 1; dFrame.add(nameLabel,dialogGBC);
        dialogGBC.gridx = 2; dialogGBC.gridy = 0; dialogGBC.fill = GridBagConstraints.NONE;
        dialogGBC.anchor = GridBagConstraints.LINE_START;
        dFrame.add(codeField,dialogGBC);
        dialogGBC.gridy = 1; dFrame.add(nameField,dialogGBC);

        if(this.inputType == InputType.EDIT_SINGLE){
            int row = mTable.getSelectedRow();
            if(row == -1){throw new NoRowSelectedException();}
            String prevCode = (String) mTable.getValueAt(row, 0);
            String prevName = (String) mTable.getValueAt(row, 1);
            
            codeField.setText(prevCode);
            nameField.setText(prevName);
        }
        
    }

    /**
     * Gets the inputted college's code from its designated {@code JTextField}
     * @return {@code String}
     * @throws EmptyInputException when no input is in the {@code JTextField} for a
     * college's code
     */
    public String getCode() throws EmptyInputException{
        if(codeField.getText().isBlank()){
            throw new EmptyInputException();
        }
        return codeField.getText();
    }

    /**
     * Gets the inputted college's name from its designated {@code JTextField}
     * @return {@code String}
     * @throws EmptyInputException when no input is in the {@code JTextField} for a
     * college's name
     */
    public String getName() throws EmptyInputException{
        if(nameField.getText().isBlank()){
            throw new EmptyInputException();
        }
        return nameField.getText();
    }
}
