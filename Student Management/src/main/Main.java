package main;

import java.awt.Dimension;

import com.formdev.flatlaf.FlatDarkLaf;

import main.app.frames.MainFrame;

/**
 * Main class of the application
 */
public class Main {

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        MainFrame mainFrame = new MainFrame();
        mainFrame.setMinimumSize(new Dimension(mainFrame.getWidth(),mainFrame.getHeight()));
    }
}
