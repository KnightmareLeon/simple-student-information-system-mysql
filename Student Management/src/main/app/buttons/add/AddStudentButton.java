package main.app.buttons.add;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import main.app.errors.EmptyInputException;
import main.app.errors.ExistingIDException;
import main.app.errors.NoRowSelectedException;
import main.app.errors.NullPointerExceptionWithWindow;
import main.app.frames.MainFrame;
import main.app.input.InputType;
import main.app.input.StudentInput;
import main.app.tables.ManagementTable;

/**
 * {@code JButton} class that sets up all components needed to add a new
 * {@link main.data.dataClass.Student Student}'s data. The action button that 
 * it sets up will send the new data to the {@link main.data.maps.DataMap 
 * DataMap} and {@link main.app.tables.ManagementTable ManagementTable} when 
 * clicked.
 * @see StudentInput {@code StudentInput}
 */
public class AddStudentButton extends AddDataButton{
    public AddStudentButton(ManagementTable mTable, MainFrame mainFrame){
        super(mTable, mainFrame);
        this.setDataText("Student");
        this.setText(this.getActionDataText());
        this.setVisible(true);
    }
    
    @Override
    protected void setUpComponents(ManagementTable mTable) throws NoRowSelectedException{
        this.getDataFrame().setTitle("Add Student");

        StudentInput stdInput = new StudentInput(this.getDataFrame(), mTable, this.getGBC(), InputType.ADD);
        
        //Add Student
        this.getActionButton().setText("Add Student");
        this.getActionButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event){
                try{
                    boolean confirm = true;
                    if(mTable.getDMap().hasID(stdInput.getID())){
                        throw new ExistingIDException();
                    }

                    String[] data = {
                        stdInput.getID(),
                        stdInput.getFN(),
                        stdInput.getLN(),
                        stdInput.getYL(),
                        stdInput.getG(),
                        stdInput.getPC()
                    };
                    if(mTable.getDMap().hasStudentName(stdInput.getFN() + " " + stdInput.getLN())){
                        confirm = (JOptionPane.showConfirmDialog(
                                    getActionButton(), 
                            "Name already exists. Do you want to proceed?", 
                              "Same Name Confirmation", 
                                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) ? true : false;
                    }
                    if(confirm){
                        mTable.getSTM().addData(data, mTable.getDMap());
                        JOptionPane.showMessageDialog(getActionButton(), "Student added successfully!");
                        getDataFrame().dispose();

                    }

                } catch(EmptyInputException | NullPointerExceptionWithWindow | ExistingIDException e) {
                    e.printStackTrace();
                    e.startErrorWindow(getActionButton());
                } 
            }
        });

        //Adding Components
        
        this.getGBC().gridy = 1; this.getGBC().fill = GridBagConstraints.HORIZONTAL;
        this.getDataFrame().add(this.getActionButton(),this.getGBC());
    }
}
