package main.app.buttons.delete;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import main.app.tables.ManagementTable;
import main.app.windows.MainFrame;

/**
 * Deletes a {@link main.data.dataClass.Program Program}'s data based
 * on the selected row(s) in {@link ManagementTable}. Deletes that
 * selected row and the copy of data in {@link main.data.maps.DataMap
 * DataMap}.
 */
public class DeleteProgramButton extends DeleteDataButton{
    public DeleteProgramButton(MainFrame mainFrame, ManagementTable mTable){
        super(mainFrame, mTable);
        this.setDataText("Program");
        this.setText(this.getActionDataText());
        this.setVisible(false);
    }

    @Override
    protected boolean delete(ManagementTable mTable) throws SQLException {
        boolean confirm = true;
        int totalStds = 0;
        int[] rowArray = new int[mTable.getSelectedRowCount()];
        for(int i = 0; i < mTable.getSelectedRowCount(); i++){
            totalStds += mTable.getdBDriver().matchesWithForeignKey(
                (String) mTable.getValueAt(mTable.getSelectedRows()[i], 0), 
                mTable.getSTM().getTableName());
            rowArray[i] = mTable.convertRowIndexToModel(mTable.getSelectedRows()[i]);
        }

        if(totalStds > 0){
            confirm = (JOptionPane.showConfirmDialog(
                        mainFrame, 
                        "There are a total of " + totalStds + " student(s) that will be " +
                        "affected if you proceed to delete the selected program(s). Proceed " + 
                        "to delete?", 
                  "Program Deletion Confirmation", 
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) ? true : false;
        } 
        if(confirm){mTable.getPTM().deleteData(rowArray);}
        return confirm;
    }

}
