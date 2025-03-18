package main.app.input;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;

import main.app.errors.EmptyInputException;
import main.app.errors.NoRowSelectedException;
import main.app.frames.DataFrame;
import main.app.input.fields.UpperCaseOnlyTextField;
import main.app.tables.ManagementTable;
//import main.data.maps.DataMap;

/**
 * <p>
 * Sets up the components needed to handle a {@link main.data.dataClass.College 
 * College}'s data. 
 * <li> These include:
 * <ul>
 * <li> Code
 * <li> Name
 * </ul>
 * @see StudentInput {@code StudentInput}
 * @see ProgramInput {@code ProgramInput}
 */
public class CollegeInput extends DataInput{
    //Input Labels
    private JLabel codeLabel = new JLabel("Code:");
    private JLabel nameLabel = new JLabel("Name:");

    //Input Fields
    private JTextField codeField = new UpperCaseOnlyTextField();
    private JTextField nameField = new UpperCaseOnlyTextField();

    /**
     * 
     * @param dFrame - this app's custom {@code JFrame} in which the components 
     * will be displayed.
     * @param dMap - {@code DataMap} that handles and maps all data during 
     * the application's runtime.
     * @param frameGBC - {@code GridBagConstrainsts} of the {@code DataFrame}.
     * @param inputType - either {@code ADD} or {@code EDIT_SINGLE}
     * @throws NoRowSelectedException when user doesn't select a row in the 
     * {@code ManagementTable}.
     */
    public CollegeInput(DataFrame dFrame, ManagementTable mTable, GridBagConstraints frameGBC, InputType inputType) throws NoRowSelectedException{
        super(inputType);
        this.setUpComponents(dFrame, mTable, frameGBC);
    }

    @Override
    protected void setUpComponents(DataFrame dFrame,
                                   ManagementTable mTable, 
                                   GridBagConstraints frameGBC) throws NoRowSelectedException{
        this.codeField.setPreferredSize(new Dimension(70, 20));
        this.nameField.setPreferredSize(new Dimension(400, 20));
        frameGBC.insets = new Insets(5, 5, 5, 5);
        frameGBC.fill = GridBagConstraints.HORIZONTAL; 
        frameGBC.ipady = 10;
        frameGBC.gridx = frameGBC.gridy = 0; dFrame.add(codeLabel,frameGBC);
        frameGBC.gridy = 1; dFrame.add(nameLabel,frameGBC);
        frameGBC.gridx = 2; frameGBC.gridy = 0; frameGBC.fill = GridBagConstraints.NONE;
        frameGBC.anchor = GridBagConstraints.LINE_START;
        dFrame.add(codeField,frameGBC);
        frameGBC.gridy = 1; dFrame.add(nameField,frameGBC);

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
