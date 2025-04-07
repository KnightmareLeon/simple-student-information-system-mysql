package main.app.input;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.app.errors.EmptyInputException;
import main.app.errors.EmptyTableException;
import main.app.errors.NoRowSelectedException;
import main.app.input.fields.AutoCompletingComboBox;
import main.app.input.fields.UpperCaseOnlyTextField;
import main.app.tables.ManagementTable;
import main.app.windows.DataFormDialog;

/**
 * <p>
 * Sets up the components needed to add or update data to the programs 
 * table of this application's designated MySQL database.
 * </p>
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
    private JTextField codeField = new UpperCaseOnlyTextField(20);
    private JTextField nameField = new UpperCaseOnlyTextField(100);
    private JComboBox<String> cCodeList;

    /**
     * 
     * @param dataFormDialog - this app's custom {@code JDialog} in which the components 
     * for adding or editing data will be displayed.
     * @param mTable - the app's custom {@code JTable} for displaying data.
     * @param dialogGBC - {@code GridBagConstrainsts} of the {@code DataFormDialog}.
     * @param inputType - either {@code ADD} or {@code EDIT_SINGLE}
     * @throws NoRowSelectedException when user doesn't select at least one row in the 
     * {@code ManagementTable}.
     * @throws EmptyTableException when there is still no data from its parent table.
     * For the programs table, its parent table would be the colleges table.
     */
    public ProgramInput(DataFormDialog dataFormDialog,
                        ManagementTable mTable,
                        GridBagConstraints dialogGBC,
                        InputType inputType) throws NoRowSelectedException, EmptyTableException{
        super(inputType);
        this.setUpComponents(dataFormDialog, mTable, dialogGBC);
    }

    @Override
    protected void setUpComponents(DataFormDialog dFrame, 
                                   ManagementTable mTable, 
                                   GridBagConstraints dialogGBC) 
                                   throws NoRowSelectedException, EmptyTableException{
        
        try {
            if(mTable.getdBDriver().isTableEmpty("colleges")){
                throw new EmptyTableException();
            }
            this.cCodeList = new AutoCompletingComboBox(mTable.getdBDriver().getArrayFromColumn("code", "colleges"));
            
            this.cName.setText(mTable.getdBDriver().getData((String)this.cCodeList.getSelectedItem(), "name", "colleges"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.codeField.setPreferredSize(new Dimension(70, 20));
        this.cName.setPreferredSize(new Dimension(400, 20));
        this.cCodeList.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!((String) cCodeList.getSelectedItem()).equals("-") &&
                   !((String) cCodeList.getSelectedItem()).equals("NULL")){
                    try {
                        cName.setText(mTable.getdBDriver().getData((String)cCodeList.getSelectedItem(), "name", "colleges"));
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    cName.setText("");
                }
            }
            
        });

        dialogGBC.insets = new Insets(5, 5, 5, 5);
        dialogGBC.fill = GridBagConstraints.HORIZONTAL; 
        dialogGBC.ipady = 10;
        if(this.inputType == InputType.ADD || this.inputType == InputType.EDIT_SINGLE){

            dialogGBC.gridx = dialogGBC.gridy = 0; dFrame.add(this.codeLabel, dialogGBC);
            dialogGBC.gridy = 1; dFrame.add(this.nameLabel,dialogGBC);        
            dialogGBC.gridy = 2; dFrame.add(this.cCodeLabel,dialogGBC);
            dialogGBC.gridy = 3; dFrame.add(this.cNameLabel, dialogGBC);
            dialogGBC.gridx = 1; dialogGBC.gridy = 0; dialogGBC.gridwidth = 2;
            dialogGBC.fill = GridBagConstraints.NONE; dialogGBC.anchor = GridBagConstraints.LINE_START;
            dFrame.add(this.codeField,dialogGBC);
            dialogGBC.gridy = 1; dFrame.add(this.nameField, dialogGBC);
            dialogGBC.gridy = 2; dFrame.add(this.cCodeList, dialogGBC);
            dialogGBC.gridy = 3; dFrame.add(this.cName, dialogGBC);
        } else {
            this.cCodeList.addItem("-");
            this.cCodeList.setSelectedItem("-");
            dialogGBC.gridx = dialogGBC.gridy = 0; dFrame.add(this.cCodeLabel,dialogGBC);
            dialogGBC.gridy = 1; dFrame.add(this.cNameLabel, dialogGBC);
            dialogGBC.gridx = 1; dialogGBC.gridy = 0; dialogGBC.gridwidth = 2;
            dialogGBC.fill = GridBagConstraints.NONE; dialogGBC.anchor = GridBagConstraints.LINE_START;
            dFrame.add(this.cCodeList, dialogGBC);
            dialogGBC.gridy = 1; dFrame.add(this.cName, dialogGBC);
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
            if(prevCCode.equals("NULL")){
                cCodeList.addItem("NULL");
            }
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
