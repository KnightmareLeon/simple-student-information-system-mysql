package main.app.buttons.delete;

import main.app.tables.ManagementTable;
import main.app.windows.MainFrame;

import java.sql.SQLException;
import java.util.Arrays;
/**
 * Deletes a {@link main.data.dataClass.Student Student}'s data based
 * on the selected row(s) in {@link ManagementTable}. Deletes that
 * selected row and the copy of data in {@link main.data.maps.DataMap
 * DataMap}.
 */
public class DeleteStudentButton extends DeleteDataButton{

    public DeleteStudentButton(ManagementTable mTable, MainFrame mainFrame) {
        super(mTable, mainFrame);
        this.setDataText("Student");
        this.setText(this.getActionDataText());
    }

    @Override
    protected boolean delete(ManagementTable mTable) throws SQLException {
        if(mTable.getSelectedRowCount() == 1){
            mTable.getSTM().deleteData(mTable.convertRowIndexToModel(mTable.getSelectedRow()));
        } else {
            int[] rowArray = new int[mTable.getSelectedRowCount()];
            for(int i = 0; i < mTable.getSelectedRowCount(); i++){
                rowArray[i] = mTable.convertRowIndexToModel(mTable.getSelectedRows()[i]);
            }

            Arrays.sort(rowArray);

            for(int i = rowArray.length - 1; i > -1; i--){
                mTable.getSTM().deleteData(rowArray[i]);
            }
        }
        return true;
        
    }
}
