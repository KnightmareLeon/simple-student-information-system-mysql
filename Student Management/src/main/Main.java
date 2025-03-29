package main;

import java.awt.Dimension;

import com.formdev.flatlaf.FlatDarkLaf;

import main.app.windows.LogInFrame;

/**
 * Main class of the application
 */
public class Main {

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        LogInFrame logInFrame = new LogInFrame();
        logInFrame.setMaximumSize(new Dimension(logInFrame.getWidth(), logInFrame.getHeight()));
    }
}
