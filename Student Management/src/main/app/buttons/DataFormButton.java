package main.app.buttons;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.KeyStroke;

import main.app.errors.EmptyTableException;
import main.app.errors.MultiEditCollegeException;
import main.app.errors.NoRowSelectedException;
import main.app.tables.ManagementTable;
import main.app.windows.DataFormDialog;
import main.app.windows.MainFrame;

/**
 * Abstract {@code JButton} class that sets up the components
 * needed to get input for adding or editing data. Initializes
 * a {@link main.app.windows.DataFormDialog DataFrame} in which the 
 * will components be set up in.
 */
public abstract class DataFormButton extends DataButton implements ActionListener{
    private DataFormDialog dataFormDialog;
    private GridBagLayout gbl;
    private GridBagConstraints gbc;
    private JButton actionButton;

    private ManagementTable mTable;
    public DataFormButton(MainFrame mFrame, ManagementTable mTable){
        super(mFrame);
        this.mTable = mTable;
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae){
        this.dataFormDialog = new DataFormDialog();
        this.gbl = new GridBagLayout();
        this.gbc = new GridBagConstraints();
        this.dataFormDialog.setLayout(gbl);
        this.actionButton = new JButton();
        this.actionButton.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Do Action");
        this.actionButton.getActionMap().put("Do Action", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                actionButton.doClick();
            }
        });
        try {
            this.setUpComponents(mTable);
            this.dataFormDialog.pack();
            this.dataFormDialog.setLocationRelativeTo(null);
            this.dataFormDialog.setVisible(true);
        } catch (NoRowSelectedException | MultiEditCollegeException | EmptyTableException e) {
            e.startErrorWindow(mainFrame);
            e.printStackTrace();
        }
    }

    /**
     * Sets up components for handling data.
     * @param mTable - this app's custom {@code JTable} that also includes the {@code DatabaseDriver}
     * @throws NoRowSelectedException when user doesn't select a row in the 
     * {@code ManagementTable}. (NOTE: Only {@link main.app.buttons.edit.EditDataButton
     * EditDataButtons} will throw this.)
     * @throws MultiEditCollegeException when users try to multi-edit colleges.
     */
    protected abstract void setUpComponents(ManagementTable mTable) throws NoRowSelectedException, EmptyTableException, MultiEditCollegeException;

    /**
     * Gets the JButton that will do the action of handling data.
     * @return {@code JButton}
     */
    protected JButton getActionButton(){return this.actionButton;};

    /**
     * Gets the {@link main.app.windows.DataFormDialog DataFrame} that will be initialized.
     * @return {@code DataFrame}
     */
    protected DataFormDialog getDataDialog(){return this.dataFormDialog;}

    /**
     * Gets the {@link java.awt.GridBagConstraints GridBagConstraints} used for
     * setting up the components in the {@link main.app.windows.DataFormDialog 
     * DataFormDialog}.
     * @return {@code GridBagConstraints}
     */
    protected GridBagConstraints getGBC(){return this.gbc;}
    
}
