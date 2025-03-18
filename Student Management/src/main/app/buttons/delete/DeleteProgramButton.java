package main.app.buttons.delete;

import java.util.Arrays;

import javax.swing.JOptionPane;

import main.app.frames.MainFrame;
import main.app.tables.ManagementTable;

/**
 * Deletes a {@link main.data.dataClass.Program Program}'s data based
 * on the selected row(s) in {@link ManagementTable}. Deletes that
 * selected row and the copy of data in {@link main.data.maps.DataMap
 * DataMap}.
 */
public class DeleteProgramButton extends DeleteDataButton{
    public DeleteProgramButton(ManagementTable mTable, MainFrame mainFrame){
        super(mTable, mainFrame);
        this.setDataText("Program");
        this.setText(this.getActionDataText());
        this.setVisible(false);
    }

    @Override
    protected boolean delete(ManagementTable mTable) {
        boolean confirm = true;
        int totalStds = 0;
        int[] rowArray = new int[mTable.getSelectedRowCount()];
        if(mTable.getSelectedRowCount() == 1){
            totalStds = mTable.getDMap().getProgram((String) mTable.getValueAt(mTable.getSelectedRow(), 0)).getTotalStudents();
        } else {
            for(int i = 0; i < mTable.getSelectedRowCount(); i++){
                totalStds += mTable.getDMap().getProgram((String) mTable.getValueAt(mTable.getSelectedRows()[i], 0)).getTotalStudents();
                rowArray[i] = mTable.convertRowIndexToModel(mTable.getSelectedRows()[i]);
            }
            Arrays.sort(rowArray);
        }

        if(totalStds > 0){
            confirm = (JOptionPane.showConfirmDialog(
                        mainFrame, 
                        "There are a total of " + totalStds + " student(s) that will be " +
                        "deleted if you proceed to delete the selected program(s). Proceed " + 
                        "to delete?", 
                  "Program Deletion Confirmation", 
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) ? true : false;
        } 
        if(confirm && mTable.getSelectedRowCount() == 1){
            mTable.getPTM().deleteData(mTable.convertRowIndexToModel(mTable.getSelectedRow()), mTable.getDMap());
        } else if(confirm){
            for(int i = rowArray.length - 1; i > -1; i--){
                mTable.getPTM().deleteData(mTable.convertRowIndexToModel(mTable.getSelectedRow()), mTable.getDMap());
            }
        }
        return confirm;
    }

}
