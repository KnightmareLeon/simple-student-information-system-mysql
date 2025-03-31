package main.app.windows;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;

public abstract class DefaultFrame extends JFrame{
    public DefaultFrame(){
        this.setTitle("UniFlow Student Management System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        ArrayList<Image> images = new ArrayList<Image>(4);
        images.add(toolkit.getImage("src/resources/icons/uniflow-1.png"));
        images.add(toolkit.getImage("src/resources/icons/uniflow-2.png"));
        images.add(toolkit.getImage("src/resources/icons/uniflow-3.png"));
        images.add(toolkit.getImage("src/resources/icons/uniflow-4.png"));
        this.setIconImages(images);
    }
}
