package main.app.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class LogInFrame extends JFrame{

    public LogInFrame(){
        this.setResizable(false);
        this.setTitle("Student Management System");
        this.setLayout(new BorderLayout());
        
        final JLabel userLabel = new JLabel("Username");
        final JTextField userField = new JTextField(20);
        final JPanel userPanel = new JPanel();

        final JLabel passwordLabel = new JLabel("Password");
        final JPasswordField passwordField = new JPasswordField(20);

        final JButton logInButton = new JButton("Log In");

        final Border padding = BorderFactory.createEmptyBorder(3,3,3,3);

        final JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        content.setBorder(padding);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 0, 3, 0);
        content.add(userLabel,gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 6, 0);
        content.add(userField,gbc);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 3, 0);
        content.add(passwordLabel,gbc);
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 12, 0);
        content.add(passwordField,gbc);
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        content.add(logInButton,gbc);
        
        this.setContentPane(content);

        this.setSize(new Dimension(300,180));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
