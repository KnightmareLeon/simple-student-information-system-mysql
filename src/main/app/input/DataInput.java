package main.app.input;

import java.awt.GridBagConstraints;

import main.app.errors.EmptyTableException;
import main.app.errors.NoRowSelectedException;
import main.app.tables.ManagementTable;
import main.app.windows.DataFormDialog;

/**
 * Abstract class for setting up components and getting data for data handling.
 * Its child classes will handle their designated data types.
 * @see StudentInput {@code StudentInput}
 * @see ProgramInput {@code ProgramInput}
 * @see CollegeInput {@code CollegeInput}
 */
public abstract class DataInput {
    
    protected InputType inputType;

    public DataInput(InputType inputType){
        this.inputType = inputType;
    }

    /**
     * Sets up the components needed for data handling.
     * 
     * @param dataFormDialog - this app's custom {@code JDialog} in which the components 
     * for adding or editing data will be displayed.
     * @param mTable - the app's custom {@code JTable} for displaying data.
     * @param dialogGBC - {@code GridBagConstrainsts} of the {@code DataFormDialog}.
     * @throws NoRowSelectedException  when user doesn't select at least one row in the 
     * {@code ManagementTable}.
     * @throws EmptyTableException when there is still no data from its parent table.
     * For the programs table, its parent table would be the colleges table.
     */
    protected abstract void setUpComponents(DataFormDialog dataFormDialog, 
                                            ManagementTable mTable, 
                                            GridBagConstraints dialogGBC
                                            ) throws NoRowSelectedException, EmptyTableException;
}
