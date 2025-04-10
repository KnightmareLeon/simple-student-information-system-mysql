package main.app.buttons.edit;

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
 * {@code JButton} class that sets up all components needed to edit at
 * least one program's data in the database based on the selected
 * row(s) in {@link ManagementTable}.
 * @see ProgramInput {@code ProgramInput}
 */
public class EditProgramButton extends EditDataButton{

    private ProgramInput prgInput;
    public EditProgramButton(MainFrame mainFrame, ManagementTable mTable) {
        super(mainFrame, mTable);
        this.setDataText("Program");
        this.setText(this.getActionDataText());
        this.setVisible(false);
    }

    @Override
    protected void setUpComponents(ManagementTable mTable) throws NoRowSelectedException, EmptyTableException {
        this.getDataDialog().setTitle("Edit Program");

        if(mTable.getSelectedRowCount() == 1){
            prgInput = new ProgramInput(this.getDataDialog(), mTable, this.getGBC(), InputType.EDIT_SINGLE);
        } else {
            prgInput = new ProgramInput(this.getDataDialog(), mTable, this.getGBC(), InputType.EDIT_MULTIPLE);
        }
        
        //Edit Button
        this.getActionButton().setText("Edit Program");
        this.getActionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                editData(mTable);
            }
        });

        this.getGBC().gridx = 0; ++this.getGBC().gridy; 
        this.getGBC().gridwidth = 3;
        this.getGBC().fill = GridBagConstraints.HORIZONTAL;
        this.getDataDialog().add(this.getActionButton(), this.getGBC());
    }

    @Override
    protected void editData(ManagementTable mTable){
        if(mTable.getSelectedRowCount() == 1){
            try {
                int row = mTable.getSelectedRow();
                String tableName = mTable.getPTM().getTableName();
                if(mTable.getdBDriver().ifRecordExists(mTable.getColumnName(0), tableName, prgInput.getCode())
                    && !mTable.getValueAt(row, 0).equals(prgInput.getCode())){
                    throw new ExistingCodeException();
                } else if(mTable.getdBDriver().ifRecordExists(mTable.getColumnName(1), tableName, prgInput.getName())
                    && !(mTable.getValueAt(row, 1)).equals(prgInput.getName())){
                    throw new ExistingNameException();
                }
                String[] data = {
                    prgInput.getCode(),
                    prgInput.getName(),
                    prgInput.getCCode()   
                };
    
                mTable.getPTM().editData(mTable.convertRowIndexToModel(row), data);
                JOptionPane.showMessageDialog(getActionButton(), "Program edited successfully!");
                getDataDialog().dispose();
        
            } catch (EmptyInputException | ExistingCodeException | ExistingNameException e){
                e.printStackTrace();
                e.startErrorWindow(getActionButton());
            } catch (SQLException e) {
                e.printStackTrace();
            } 
        } else {
            String[] data = {
                prgInput.getCCode()   
            };
            int[] rowArray = new int[mTable.getSelectedRowCount()];
            for(int i = 0; i < mTable.getSelectedRowCount(); i++){
                rowArray[i] = mTable.convertRowIndexToModel(mTable.getSelectedRows()[i]);
            }
            try {
                mTable.getPTM().batchEdit(rowArray, data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(getActionButton(), "Programs edited successfully!");
            getDataDialog().dispose();
        }
        
    }
}
