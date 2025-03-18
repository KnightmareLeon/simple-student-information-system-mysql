package main.app.input;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Year;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import main.data.maps.DataMap;
import main.app.errors.EmptyInputException;
import main.app.errors.NoRowSelectedException;
import main.app.errors.NullPointerExceptionWithWindow;
import main.app.frames.DataFrame;
import main.app.input.fields.AutoCompletingComboBox;
import main.app.input.fields.UpperCaseOnlyTextField;
import main.app.tables.ManagementTable;

/**
 * <p>
 * Sets up the components needed to handle a {@link main.data.dataClass.Student 
 * Student}'s data. 
 * <li> These include:
 * <ul>
 * <li> ID [YYYY-NNNN]
 * <li> First Name
 * <li> Last Name
 * <li> Year Level [1, 2, 3, 4, 5, 5+]
 * <li> Gender [MALE, FEMALE, OTHERS]
 * <li> Program Code (Depends on data in 
 * {@link main.data.maps.ProgramMap ProgramMap} 
 * that is in {@link DataMap})
 * </ul>
 * @see ProgramInput {@code ProgramInput}
 * @see CollegeInput {@code CollegeInput}
 */
public class StudentInput extends DataInput{

    private JPanel inputPanel = new JPanel();
    private GridBagLayout panelGBL = new GridBagLayout();
    private GridBagConstraints panelGBC = new GridBagConstraints();

    //Input Labels
    private JLabel idLabel = new JLabel("ID:");
    private JLabel fnLabel = new JLabel("First Name:");
    private JLabel lnLabel = new JLabel("Last Name:");
    private JLabel ylLabel = new JLabel("Year Level:");
    private JLabel gLabel = new JLabel("Gender:");
    private JLabel pcLabel = new JLabel("Program Code:");
    private JLabel pnLabel = new JLabel("Program Name:");
    private JLabel pn = new JLabel();

    //Input Lists for ID
    private JComboBox<String> yearList;
    private JComboBox<String> idNumList;
    private JLabel dash = new JLabel("-");
    private JPanel idPanel = new JPanel();
    private GridBagConstraints idGBC = new GridBagConstraints();

    //Input Text Fields for First Name and Last Name
    private JTextField fnField = new UpperCaseOnlyTextField();
    private JTextField lnField = new UpperCaseOnlyTextField();

    //Input Buttons for Year Level
    private JPanel ylPanel = new JPanel();
    private ButtonGroup ylList = new ButtonGroup();
    private JRadioButton yOneButton = new JRadioButton("1");
    private JRadioButton yTwoButton = new JRadioButton("2");
    private JRadioButton yThreeButton = new JRadioButton("3");
    private JRadioButton yFourButton = new JRadioButton("4");
    private JRadioButton yFiveButton = new JRadioButton("5");
    private JRadioButton yFivePlusButton = new JRadioButton("5+");
    private JRadioButton yNoUpdateButton = new JRadioButton("NO UPDATE");

    //Input Buttons for Gender
    private ButtonGroup gList = new ButtonGroup();
    private JPanel gPanel = new JPanel();
    private JRadioButton maleButton = new JRadioButton("M");
    private JRadioButton femaleButton = new JRadioButton("F");
    private JRadioButton othersButton = new JRadioButton("OTHERS");
    private JRadioButton gNoUpdateButton = new JRadioButton("NO UPDATE");

    //Input List for Program Code
    private JComboBox<String> pcList;
    private String[] prgCodeList; //Program Code List

    /**
     * @param dFrame - this app's custom {@code JFrame} in which the components
     * will be displayed.
     * @param dMap - {@code DataMap} that handles and maps all data during 
     * the application's runtime.
     * @param frameGBC - {@code GridBagConstrainsts} of the {@code DataFrame}.
     * @throws NoRowSelectedException when user doesn't select a row in the 
     * {@code ManagementTable}.
     */
    public StudentInput(DataFrame dFrame, ManagementTable mTable, GridBagConstraints frameGBC, InputType inputType) throws NoRowSelectedException{
        super(inputType);
        this.setUpComponents(dFrame, mTable, frameGBC);
    }

    @Override
    protected void setUpComponents(DataFrame dFrame, 
                                   ManagementTable mTable, 
                                   GridBagConstraints frameGBC) throws NoRowSelectedException{
        frameGBC.insets = new Insets(5, 5, 5, 5);
        
        this.inputPanel.setLayout(panelGBL);
        
        //Setting up Year Level Buttons
        this.ylPanel.setLayout(new GridLayout(1,6));
        this.ylList.add(yOneButton); this.ylPanel.add(this.yOneButton); this.yOneButton.setActionCommand(this.yOneButton.getText());
        this.ylList.add(yTwoButton); this.ylPanel.add(this.yTwoButton); this.yTwoButton.setActionCommand(this.yTwoButton.getText());
        this.ylList.add(yThreeButton); this.ylPanel.add(this.yThreeButton); this.yThreeButton.setActionCommand(this.yThreeButton.getText());
        this.ylList.add(yFourButton); this.ylPanel.add(this.yFourButton); this.yFourButton.setActionCommand(this.yFourButton.getText());
        this.ylList.add(yFiveButton); this.ylPanel.add(this.yFiveButton); this.yFiveButton.setActionCommand(this.yFiveButton.getText());
        this.ylList.add(yFivePlusButton); this.ylPanel.add(this.yFivePlusButton); this.yFivePlusButton.setActionCommand(this.yFivePlusButton.getText());

        //Setting up Gender Buttons
        this.gPanel.setLayout(new GridLayout(1,3));
        this.gList.add(maleButton); this.gPanel.add(maleButton); this.maleButton.setActionCommand(maleButton.getText());
        this.gList.add(femaleButton); this.gPanel.add(femaleButton); this.femaleButton.setActionCommand(femaleButton.getText());
        this.gList.add(othersButton); this.gPanel.add(othersButton); this.othersButton.setActionCommand(othersButton.getText());
        
        //Setting up Input Program List
        this.prgCodeList = mTable.getDMap().getProgramList();
        this.pcList = new AutoCompletingComboBox(prgCodeList);

        //Setting up Program Name
        this.pn.setPreferredSize(new Dimension(400, 20));
        this.pn.setText(mTable.getDMap().getProgram((String) this.pcList.getSelectedItem()).getName());
        this.pcList.addActionListener(new ActionListener(){ //To set text of pn when a new selection in pcList is done
            @Override
            public void actionPerformed(ActionEvent e) { 
                if(!((String) pcList.getSelectedItem()).equals("NO UPDATE")){
                    pn.setText(mTable.getDMap().getProgram((String) pcList.getSelectedItem()).getName());
                } else {
                    pn.setText("");
                }

            }
            
        });

        this.panelGBC.insets = new Insets(3, 3, 3, 3);
        this.panelGBC.ipady = 10;

        if(this.inputType == InputType.ADD || this.inputType == InputType.EDIT_SINGLE){
            //Setting up Input Lists for ID
            int currentYear = Year.now().getValue();
            int yearLimit = currentYear - 1966 + 1;
            String[] years = new String[yearLimit - 1];
            String[] idNum = new String[9999];
            for(int i = 1; i < 10000; i++){
                if (i < yearLimit){
                    years[i - 1] = String.valueOf(1966 + i);
                }
                idNum[i - 1] = String.format("%04d",i);
            }

            this.yearList = new AutoCompletingComboBox(years);
            this.idNumList = new AutoCompletingComboBox(idNum);
            this.yearList.setSelectedItem(Integer.toString(currentYear));

            this.idPanel.setLayout(new GridBagLayout());
            this.idGBC.gridx = 0; this.idGBC.gridy = 0; this.idGBC.ipady = 10;
            this.idGBC.anchor = GridBagConstraints.FIRST_LINE_START;
            this.idPanel.add(this.yearList, idGBC);
            this.idGBC.gridx = 1;
            this.idPanel.add(this.dash, idGBC);
            this.idGBC.gridx = 2;
            this.idPanel.add(this.idNumList, idGBC);

            //Adding Components to the Data frame
            this.panelGBC.gridx = panelGBC.gridy = 0; this.panelGBC.fill = GridBagConstraints.HORIZONTAL;
            this.inputPanel.add(this.idLabel, this.panelGBC);
            this.panelGBC.gridy = 1; this.inputPanel.add(this.fnLabel, this.panelGBC);
            this.panelGBC.gridy = 2; this.inputPanel.add(this.lnLabel, this.panelGBC);
            this.panelGBC.gridy = 3; this.inputPanel.add(this.ylLabel, this.panelGBC);
            this.panelGBC.gridy = 4; this.inputPanel.add(this.gLabel, this.panelGBC);
            this.panelGBC.gridy = 5; this.inputPanel.add(this.pcLabel, this.panelGBC);
            this.panelGBC.gridy = 6; this.inputPanel.add(this.pnLabel, this.panelGBC);
            this.panelGBC.gridx = 1; this.panelGBC.gridy = 0; 
            this.panelGBC.fill = GridBagConstraints.NONE; 
            this.panelGBC.anchor = GridBagConstraints.FIRST_LINE_START; 
            this.inputPanel.add(this.idPanel, this.panelGBC);
            this.panelGBC.gridy = 1; this.inputPanel.add(this.fnField, this.panelGBC);
            this.panelGBC.gridy = 2; this.inputPanel.add(this.lnField, this.panelGBC);
            this.panelGBC.gridy = 3; this.inputPanel.add(this.ylPanel, this.panelGBC);
            this.panelGBC.gridy = 4; this.inputPanel.add(this.gPanel, this.panelGBC);
            this.panelGBC.gridy = 5; this.inputPanel.add(this.pcList, this.panelGBC);
            this.panelGBC.gridy = 6; this.inputPanel.add(this.pn, this.panelGBC);
        } else {
            this.ylList.add(this.yNoUpdateButton); this.ylPanel.add(this.yNoUpdateButton); this.yNoUpdateButton.setActionCommand(this.yNoUpdateButton.getText());
            this.gList.add(this.gNoUpdateButton); this.gPanel.add(this.gNoUpdateButton); this.gNoUpdateButton.setActionCommand(this.gNoUpdateButton.getText());
            this.pcList.addItem("NO UPDATE");
            this.pcList.setSelectedItem("NO UPDATE");
            this.panelGBC.gridx = panelGBC.gridy = 0; this.panelGBC.fill = GridBagConstraints.HORIZONTAL;
            this.inputPanel.add(this.ylLabel, this.panelGBC);
            this.panelGBC.gridy = 1; this.inputPanel.add(this.gLabel, this.panelGBC);
            this.panelGBC.gridy = 2; this.inputPanel.add(this.pcLabel, this.panelGBC);
            this.panelGBC.gridy = 3; this.inputPanel.add(this.pnLabel, this.panelGBC);
            this.panelGBC.gridx = 1; this.panelGBC.gridy = 0; 
            this.panelGBC.fill = GridBagConstraints.NONE; 
            this.panelGBC.anchor = GridBagConstraints.FIRST_LINE_START; 
            this.inputPanel.add(this.ylPanel, this.panelGBC);
            this.panelGBC.gridy = 1; this.inputPanel.add(this.gPanel, this.panelGBC);
            this.panelGBC.gridy = 2; this.inputPanel.add(this.pcList, this.panelGBC);
            this.panelGBC.gridy = 3; this.inputPanel.add(this.pn, this.panelGBC);
        }
        
        frameGBC.gridx = frameGBC.gridy = 0;
        dFrame.add(inputPanel, frameGBC);
        frameGBC.ipady = 10;
        
        if(this.inputType == InputType.EDIT_SINGLE || this.inputType == InputType.EDIT_MULTIPLE){
            if(mTable.getSelectedRowCount() == 0){throw new NoRowSelectedException();}
        }

        if(this.inputType == InputType.EDIT_SINGLE){
            int row = mTable.getSelectedRow();
            String prevID = (String) mTable.getValueAt(row, 0);
            String prevFN = (String) mTable.getValueAt(row, 1);
            String prevLN = (String) mTable.getValueAt(row, 2);
            String prevYL = (String) mTable.getValueAt(row, 3);
            String prevG = (String) mTable.getValueAt(row, 4);
            String prevPC = (String) mTable.getValueAt(row, 5);
            this.yearList.setSelectedItem(prevID.substring(0,4));
            this.idNumList.setSelectedItem(prevID.substring(5,9));
            this.fnField.setText(prevFN);
            if(prevYL.equals("1")){
                this.ylList.setSelected(yOneButton.getModel(), true);
            } else if(prevYL.equals("2")){
                this.ylList.setSelected(yTwoButton.getModel(), true);
            } else if(prevYL.equals("3")){
                this.ylList.setSelected(yThreeButton.getModel(), true);
            } else if(prevYL.equals("4")){
                this.ylList.setSelected(yFourButton.getModel(), true);
            } else if(prevYL.equals("5")){
                this.ylList.setSelected(yFiveButton.getModel(), true);
            } else if(prevYL.equals("5+")){
                this.ylList.setSelected(yFivePlusButton.getModel(), true);
            }
            if(prevG.equals("MALE")){
                this.gList.setSelected(maleButton.getModel(), true);
            } else if(prevG.equals("FEMALE")){
                this.gList.setSelected(femaleButton.getModel(), true);
            } else if(prevG.equals("OTHERS")){
                this.gList.setSelected(othersButton.getModel(), true);
            }
            
            this.lnField.setText(prevLN);
            this.pcList.setSelectedItem(prevPC);
        }
        
    }

    /**
     * Gets the inputted ID from its designated {@code JComboBox}
     * for [YYYY] and [NNNN].
     * @return {@code String} in the format "YYYY-NNNN"
     */
    public String getID(){return yearList.getSelectedItem() + "-" + idNumList.getSelectedItem();}

    /**
     * Gets the inputted first name from its designated {@code JTextField}.
     * @return {@code String}
     * @throws EmptyInputException when no input is in the {@code JTextField} for a
     * student's first name
     */
    public String getFN() throws EmptyInputException{
        if(fnField.getText().isBlank()){
            throw new EmptyInputException();
        }
        return fnField.getText();
    }

    /**
     * Gets the inputted last name from its designated {@code JTextField}.
     * @return {@code String}
     * @throws EmptyInputException when no input is in the {@code JTextField} for a
     * student's last name
     */
    public String getLN() throws EmptyInputException{
        if(lnField.getText().isBlank()){
            throw new EmptyInputException();
        }
        return lnField.getText();
    }

    /**
     * Gets the inputted year level from its designated {@code JButtonGroup} of
     * {@code JRadioButton}.
     * @return {@code String} that is either "1", "2", "3", "4", "5", and "5+"
     * @throws NullPointerExceptionWithWindow when no input is in the 
     * {@code JButtonGroup} for a student's year level.
     */
    public String getYL(){
        if(ylList.getSelection() == null){
            throw new NullPointerExceptionWithWindow();
        } else {
            return ylList.getSelection().getActionCommand();
        }
    }

    /**
     * Gets the inputted gender from its designated {@code JButtonGroup} of
     * {@code JRadioButton}.
     * @return {@code String} that is either "MALE", "FEMALE", or "OTHERS"
     * @throws NullPointerExceptionWithWindow when no input is in the 
     * {@code JButtonGroup} for a student's gender.
     */
    public String getG() throws NullPointerExceptionWithWindow{
        if(gList.getSelection() == null){
            throw new NullPointerExceptionWithWindow();
        } else {
            return gList.getSelection().getActionCommand();
        }        
    }

    /**
     * Gets the inputted program code from its designated {@code JComboBox}
     * in which the choices could only be selected from the data in 
     * {@link main.data.maps.ProgramMap} from {@link DataMap}
     * @return {@code String}
     */
    public String getPC(){return (String) pcList.getSelectedItem();}

}
