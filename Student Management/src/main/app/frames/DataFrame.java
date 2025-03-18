package main.app.frames;

import javax.swing.JFrame;

import main.app.tables.tableModels.CSVHandlingTableModel;
import main.data.maps.DataMap;

/**
 * A custom {@code JFrame} that is initialized by
 * {@link main.app.buttons.DataFormButton DataButtons} that also
 * sets up the components needed to input data that will either
 * be added or edited in to existing data.
 */
public class DataFrame extends JFrame {
    public DataFrame(CSVHandlingTableModel tm, DataMap dMap){
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
