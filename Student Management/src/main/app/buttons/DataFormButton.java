package main.app.buttons;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.app.errors.MultiEditCollegeException;
import main.app.errors.NoRowSelectedException;
import main.app.frames.DataFrame;
import main.app.frames.MainFrame;
import main.app.tables.ManagementTable;

/**
 * Abstract {@code JButton} class that sets up the components
 * needed to get input for adding or editing data. Initializes
 * a {@link main.app.frames.DataFrame DataFrame} in which the 
 * will components be set up in.
 */
public abstract class DataFormButton extends DataButton{
    private DataFrame dFrame;
    private GridBagLayout gbl;
    private GridBagConstraints gbc;
    private JButton actionButton;
    public DataFormButton(ManagementTable mTable, MainFrame mainFrame){
        super(mainFrame);
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                setUp(mTable);
            }
        });
        
    }

    /**
     * Initializes the {@link main.app.frames.DataFrame DataFrame}
     * and components.
     * @param mTable
     */
    private void setUp(ManagementTable mTable){
        this.dFrame = new DataFrame(mTable.getCTM(), mTable.getDMap());
        this.gbl = new GridBagLayout();
        this.gbc = new GridBagConstraints();
        this.dFrame.setLayout(gbl);
        this.actionButton = new JButton();
        try {
            this.setUpComponents(mTable);
            this.dFrame.pack();
            this.dFrame.setLocationRelativeTo(null);
            this.dFrame.setVisible(true);
        } catch (NoRowSelectedException | MultiEditCollegeException e) {
            e.startErrorWindow(mainFrame);
            e.printStackTrace();
        }
    }

    /**
     * Sets up components for handling data.
     * @param mTable - this app's custom {@code JTable} that also includes {@code DataMap}
     * @throws NoRowSelectedException when user doesn't select a row in the 
     * {@code ManagementTable}. (NOTE: Only {@link main.app.buttons.edit.EditDataButton
     * EditDataButtons} will throw this.)
     * @throws MultiEditCollegeException when users try to multi-edit colleges.
     */
    protected abstract void setUpComponents(ManagementTable mTable) throws NoRowSelectedException, MultiEditCollegeException;

    /**
     * Gets the JButton that will do the action of handling data.
     * @return {@code JButton}
     */
    protected JButton getActionButton(){return this.actionButton;};

    /**
     * Gets the {@link main.app.frames.DataFrame DataFrame} that will be initialized.
     * @return {@code DataFrame}
     */
    protected DataFrame getDataFrame(){return this.dFrame;}

    /**
     * Gets the {@link java.awt.GridBagConstraints GridBagConstraints} used for
     * setting up the components in the {@link main.app.frames.DataFrame 
     * DataFrame}.
     * @return {@code GridBagConstraints}
     */
    protected GridBagConstraints getGBC(){return this.gbc;}
    
}
