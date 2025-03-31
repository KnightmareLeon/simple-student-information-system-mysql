package main.app.windows;

import javax.swing.JDialog;

/**
 * A custom {@code JDialog} that is initialized by
 * {@link main.app.buttons.DataFormButton DataButtons} that also
 * sets up the components needed to input data that will either
 * be added or edited in to existing data.
 */
public class DataFormDialog extends JDialog {
    public DataFormDialog(){
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
    }
}
