package main.app.buttons.delete;

import main.app.tables.ManagementTable;
import main.app.windows.MainFrame;

import java.sql.SQLException;

/**
 * Deletes a student's data in the database based on the selected
 * row(s) in {@link ManagementTable}.
 */
public class DeleteStudentButton extends DeleteDataButton{

    public DeleteStudentButton(MainFrame mainFrame, ManagementTable mTable) {
        super(mainFrame, mTable);
        this.setDataText("Student");
        this.setText(this.getActionDataText());
    }

    @Override
    protected boolean delete(ManagementTable mTable) throws SQLException {
        int[] rowArray = new int[mTable.getSelectedRowCount()];
        for(int i = 0; i < mTable.getSelectedRowCount(); i++){
            rowArray[i] = mTable.convertRowIndexToModel(mTable.getSelectedRows()[i]);
        }
        mTable.getSTM().deleteData(rowArray);
        return true;
    }
}
