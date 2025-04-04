package main.app.buttons.delete;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import main.app.tables.ManagementTable;
import main.app.windows.MainFrame;

/**
 * 
 */
public class DeleteCollegeButton extends DeleteDataButton{
    public DeleteCollegeButton(MainFrame mainFrame, ManagementTable mTable){
        super(mainFrame, mTable);
        this.setDataText("College");
        this.setText(this.getActionDataText());
        this.setVisible(false);
    }

    @Override
    protected boolean delete(ManagementTable mTable) throws SQLException {
        boolean confirm = true;
        int totalPrgs = 0;
        int[] rowArray = new int[mTable.getSelectedRowCount()];
        for(int i = 0; i < mTable.getSelectedRowCount(); i++){
            totalPrgs += mTable.getdBDriver().matchesWithForeignKey(
                (String) mTable.getValueAt(mTable.getSelectedRows()[i], 0), 
                mTable.getPTM().getTableName());
            rowArray[i] = mTable.convertRowIndexToModel(mTable.getSelectedRows()[i]);
        }

        if(totalPrgs > 0){
            confirm = (JOptionPane.showConfirmDialog(
                        mainFrame, 
                        "There are a total of " + totalPrgs + " program(s) that will be affected" +
                        " if you proceed to delete the selected college(s). Proceed to delete?", 
                  "College Deletion Confirmation", 
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) ? true : false;
        } 
        
        if(confirm){mTable.getCTM().deleteData(rowArray);}

        return confirm;
    }
}
