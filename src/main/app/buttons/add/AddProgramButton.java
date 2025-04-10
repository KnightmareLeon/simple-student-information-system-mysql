package main.app.buttons.add;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import main.app.errors.EmptyInputException;
import main.app.errors.EmptyTableException;
import main.app.errors.ExistingCodeException;
import main.app.errors.ExistingNameException;
import main.app.errors.NoRowSelectedException;
import main.app.input.ProgramInput;
import main.app.input.InputType;
import main.app.tables.ManagementTable;
import main.app.windows.MainFrame;

/**
 * {@code JButton} class that sets up all components needed to add a new
 * program to the database.
 * @see ProgramInput {@code ProgramInput}
 */
public class AddProgramButton extends AddDataButton{
    public AddProgramButton(MainFrame mainFrame, ManagementTable mTable){
        super(mainFrame, mTable);
        this.setDataText("Program");
        this.setText(this.getActionDataText());
        this.setVisible(false);
    }

    @Override
    protected void setUpComponents(ManagementTable mTable) throws NoRowSelectedException, EmptyTableException{
        this.getDataDialog().setTitle("Add Program");

        ProgramInput pInput = new ProgramInput(this.getDataDialog(), mTable, this.getGBC(), InputType.ADD);
        
        //Add Button
        this.getActionButton().setText("Add Program");
        this.getActionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    String tableName = mTable.getPTM().getTableName();
                    if(mTable.getdBDriver().ifRecordExists(mTable.getColumnName(0), tableName, pInput.getCode())){
                        throw new ExistingCodeException();
                    } else if(mTable.getdBDriver().ifRecordExists(mTable.getColumnName(1),tableName,pInput.getName())){
                        throw new ExistingNameException();
                    } else {
                        String[] data = {
                            pInput.getCode(),
                            pInput.getName(),
                            pInput.getCCode()   
                        };
                        mTable.getPTM().addData(data);
                        JOptionPane.showMessageDialog(getActionButton(), "Program added successfully!");
                        getDataDialog().dispose();
                    }
                } catch (EmptyInputException | ExistingCodeException | ExistingNameException e){
                    e.printStackTrace();
                    e.startErrorWindow(getActionButton());
                }  catch (SQLException e) {
                    e.printStackTrace();
                } 
            }
        });

        this.getGBC().gridx = 0; this.getGBC().gridy = 4; this.getGBC().gridwidth = 3;
        this.getGBC().fill = GridBagConstraints.HORIZONTAL;
        this.getDataDialog().add(getActionButton(), this.getGBC());
    }   
}
