package main.app.frames;

import javax.swing.JFrame;

/**
 * A custom {@code JFrame} that is initialized by
 * {@link main.app.buttons.DataFormButton DataButtons} that also
 * sets up the components needed to input data that will either
 * be added or edited in to existing data.
 */
public class DataFrame extends JFrame {
    public DataFrame(){
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
