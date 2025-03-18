package main.app.input;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.data.maps.DataMap;
import main.app.errors.EmptyInputException;
import main.app.errors.NoRowSelectedException;
import main.app.frames.DataFrame;
import main.app.input.fields.AutoCompletingComboBox;
import main.app.input.fields.UpperCaseOnlyTextField;
import main.app.tables.ManagementTable;

/**
 * <p>
 * Sets up the components needed to handle a {@link main.data.dataClass.Program 
 * Program}'s data. 
 * <li> These include:
 * <ul>
 * <li> Code
 * <li> Name
 * <li> College Code (Depends on data in 
 * {@link main.data.maps.CollegeMap CollegeMap}
 * that is in {@link DataMap})
 * </ul>
 * @see StudentInput {@code StudentInput}
 * @see CollegeInput {@code CollegeInput}
 */
public class ProgramInput extends DataInput{

    //Input Labels
    private JLabel codeLabel = new JLabel("Code:");
    private JLabel nameLabel = new JLabel("Name:");
    private JLabel cCodeLabel = new JLabel("College Code:");
    private JLabel cNameLabel = new JLabel("College Name:");
    private JLabel cName =  new JLabel();

    //Input Fields
    private JTextField codeField = new UpperCaseOnlyTextField();
    private JTextField nameField = new UpperCaseOnlyTextField();
    private JComboBox<String> cCodeList;

    /**
     * @param dFrame - this app's custom {@code JFrame} in which the components 
     * will be displayed
     * @param dMap - {@code DataMap} that handles and maps all data during 
     * the application's runtime.
     * @param frameGBC - {@code GridBagConstrainsts} of the {@code DataFrame}
     * @throws NoRowSelectedException when user doesn't select a row in the 
     * {@code ManagementTable}.
     */
    public ProgramInput(DataFrame dFrame, ManagementTable mTable, GridBagConstraints frameGBC, InputType inputType) throws NoRowSelectedException{
        super(inputType);
        this.setUpComponents(dFrame, mTable, frameGBC);
    }

    @Override
    protected void setUpComponents(DataFrame dFrame, 
                                   ManagementTable mTable, 
                                   GridBagConstraints frameGBC) throws NoRowSelectedException{
        
        this.cCodeList = new AutoCompletingComboBox(mTable.getDMap().getCollegeList());
        this.codeField.setPreferredSize(new Dimension(70, 20));
        this.cName.setPreferredSize(new Dimension(400, 20));
        this.cName.setText((mTable.getDMap().getCollege((String)this.cCodeList.getSelectedItem())).getName());
        this.cCodeList.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cName.setText((mTable.getDMap().getCollege((String)cCodeList.getSelectedItem())).getName());
            }
            
        });

        frameGBC.insets = new Insets(5, 5, 5, 5);
        frameGBC.fill = GridBagConstraints.HORIZONTAL; 
        frameGBC.ipady = 10;
        if(this.inputType == InputType.ADD || this.inputType == InputType.EDIT_SINGLE){

            frameGBC.gridx = frameGBC.gridy = 0; dFrame.add(this.codeLabel, frameGBC);
            frameGBC.gridy = 1; dFrame.add(this.nameLabel,frameGBC);        
            frameGBC.gridy = 2; dFrame.add(this.cCodeLabel,frameGBC);
            frameGBC.gridy = 3; dFrame.add(this.cNameLabel, frameGBC);
            frameGBC.gridx = 1; frameGBC.gridy = 0; frameGBC.gridwidth = 2;
            frameGBC.fill = GridBagConstraints.NONE; frameGBC.anchor = GridBagConstraints.LINE_START;
            dFrame.add(this.codeField,frameGBC);
            frameGBC.gridy = 1; dFrame.add(this.nameField, frameGBC);
            frameGBC.gridy = 2; dFrame.add(this.cCodeList, frameGBC);
            frameGBC.gridy = 3; dFrame.add(this.cName, frameGBC);
        } else {
            frameGBC.gridx = frameGBC.gridy = 0; dFrame.add(this.cCodeLabel,frameGBC);
            frameGBC.gridy = 1; dFrame.add(this.cNameLabel, frameGBC);
            frameGBC.gridx = 1; frameGBC.gridy = 0; frameGBC.gridwidth = 2;
            frameGBC.fill = GridBagConstraints.NONE; frameGBC.anchor = GridBagConstraints.LINE_START;
            dFrame.add(this.cCodeList, frameGBC);
            frameGBC.gridy = 1; dFrame.add(this.cName, frameGBC);
        }
        
        if(this.inputType == InputType.EDIT_SINGLE || this.inputType == InputType.EDIT_MULTIPLE){
            if(mTable.getSelectedRowCount() == 0){throw new NoRowSelectedException();}
        }
        
        if(this.inputType == InputType.EDIT_SINGLE){
            int row = mTable.getSelectedRow();
            String prevCode = (String) mTable.getValueAt(row, 0);
            String prevName = (String) mTable.getValueAt(row, 1);
            String prevCCode = (String) mTable.getValueAt(row, 2);

            codeField.setText(prevCode);
            nameField.setText(prevName);
            cCodeList.setSelectedItem(prevCCode);
        }
        
    }

    /**
     * Gets the inputted program's code from its designated {@code JTextField}
     * @return {@code String}
     * @throws EmptyInputException when no input is in the {@code JTextField} for a
     * program's code
     */
    public String getCode() throws EmptyInputException{
        if(codeField.getText().isBlank()){
            throw new EmptyInputException();
        }
        return codeField.getText();
    }

    /**
     * Gets the inputted program's name from its designated {@code JTextField}
     * @return {@code String}
     * @throws EmptyInputException when no input is in the {@code JTextField} for a
     * program's name
     */
    public String getName() throws EmptyInputException{
        if(nameField.getText().isBlank()){
            throw new EmptyInputException();
        }
        return nameField.getText();
    }

    /**
     * Gets the inputted college code from its designated {@code JComboBox}
     * in which the choices could only be selected from the data in 
     * {@link main.data.maps.CollegeMap CollegeMap} from {@link DataMap}
     * @return {@code String}
     */
    public String getCCode(){return (String) cCodeList.getSelectedItem();}

}
