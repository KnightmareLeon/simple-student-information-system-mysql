package main.app.input;

import java.awt.GridBagConstraints;

import main.app.errors.NoRowSelectedException;
import main.app.frames.DataFrame;
import main.app.tables.ManagementTable;
import main.data.maps.DataMap;

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
     * @param dFrame - this app's custom {@code JFrame} in which the components will be displayed.
     * @param dMap - {@link DataMap} that handles and maps all data during 
     * the application's runtime.
     * @param frameGBC - {@code GridBagConstrainsts} of the {@link DataFrame}.
     * @throws NoRowSelectedException  when user doesn't select a row in the 
     * {@code ManagementTable}.
     */
    protected abstract void setUpComponents(DataFrame dFrame, 
                                            ManagementTable mTable, 
                                            GridBagConstraints frameGBC
                                            ) throws NoRowSelectedException;

}
