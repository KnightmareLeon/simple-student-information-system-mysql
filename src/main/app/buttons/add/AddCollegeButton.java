package main.app.buttons.add;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import main.app.errors.EmptyInputException;
import main.app.errors.ExistingCodeException;
import main.app.errors.ExistingNameException;
import main.app.errors.NoRowSelectedException;
import main.app.input.CollegeInput;
import main.app.input.InputType;
import main.app.tables.ManagementTable;
import main.app.windows.MainFrame;

/**
 * {@code JButton} class that sets up all components needed to add a new
 * college to the database.
 * @see CollegeInput {@code CollegeInput}
 */
public class AddCollegeButton extends AddDataButton{
    public AddCollegeButton(MainFrame mainFrame, ManagementTable mTable){
        super(mainFrame, mTable);
        this.setDataText("College");
        this.setText(this.getActionDataText());
        this.setVisible(false);
    }
    @Override
    protected void setUpComponents(ManagementTable mTable) throws NoRowSelectedException{
        this.getDataDialog().setTitle("Add College");

        CollegeInput clgInput = new CollegeInput(this.getDataDialog(), mTable, this.getGBC(), InputType.ADD);

        //Add Button
        this.getActionButton().setText("Add College");
        this.getActionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try{
                    String tableName = mTable.getCTM().getTableName();
                    if(mTable.getdBDriver().ifRecordExists(mTable.getColumnName(0), 
                    tableName, clgInput.getCode())){
                        throw new ExistingCodeException();
                    } else if (mTable.getdBDriver().ifRecordExists(mTable.getColumnName(1), 
                    tableName, clgInput.getName())){
                        throw new ExistingNameException();
                    }
                    String[] data = {
                        clgInput.getCode(),
                        clgInput.getName()
                    };
                    boolean confirm = JOptionPane.showConfirmDialog(getActionButton(), 
                                "Please confirm that the details of the college" 
                                + " that will be added are correct.", 
                                "Confirm Adding College", 
                                JOptionPane.YES_NO_OPTION) 
                                == JOptionPane.YES_OPTION;
                    if(confirm){
                        mTable.getCTM().addData(data);
                        JOptionPane.showMessageDialog(getActionButton(), "College added successfully!");
                        getDataDialog().dispose();
                    }
                    
                } catch(EmptyInputException | ExistingCodeException | ExistingNameException e){
                    e.printStackTrace();
                    e.startErrorWindow(getActionButton());
                } catch (SQLException e) {
                    e.printStackTrace();
                }  
            }
        });

        //Adding Components
        this.getGBC().gridx = 0; this.getGBC().gridy = 2; this.getGBC().gridwidth = 3; 
        this.getGBC().fill = GridBagConstraints.HORIZONTAL;
        this.getDataDialog().add(this.getActionButton(),this.getGBC());
    }
}
