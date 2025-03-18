package main.app.errors;

import java.awt.Component;

/**
 * Interface to start a {@code JOptionDialog} that
 * will send an error message to the user
 * */
public interface ErrorWindow {
    public void startErrorWindow(Component component);
}
