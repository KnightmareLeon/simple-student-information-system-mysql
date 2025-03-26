package main.app.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

import main.database.DatabaseDriver;

public class LogInFrame extends JFrame{

    public LogInFrame(){
        this.setResizable(false);
        this.setTitle("Student Management System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        final JLabel header = new JLabel("Login");
        header.setFont(header.getFont().deriveFont(Font.BOLD, 30));

        final JLabel userLabel = new JLabel("Username");
        final JTextField userField = new JTextField(20);

        final JLabel passwordLabel = new JLabel("Password");
        final JPasswordField passwordField = new JPasswordField(20);

        final JButton logInButton = new JButton("Login");
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                login(userField.getText(), new String(passwordField.getPassword()));
            }
        });
        logInButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Login");
        logInButton.getActionMap().put("Login", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                login(userField.getText(), new String(passwordField.getPassword()));
            }
        });

        final Border padding = BorderFactory.createEmptyBorder(0,2,0,3);

        final JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        content.setBorder(padding);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 0, 25, 0);
        content.add(header,gbc);
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 3, 0);
        content.add(userLabel,gbc);
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 6, 0);
        content.add(userField,gbc);
        gbc.gridy = 3; gbc.insets = new Insets(0, 0, 3, 0);
        content.add(passwordLabel,gbc);
        gbc.gridy = 4; gbc.insets = new Insets(0, 0, 15, 0);
        content.add(passwordField,gbc);
        gbc.gridy = 5; gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER; gbc.fill = GridBagConstraints.BOTH;
        content.add(logInButton,gbc);
        
        this.setContentPane(content);

        this.setSize(new Dimension(300,270));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void login(String username, String password){
        try{
            DatabaseDriver dbDriver = new DatabaseDriver(username, password);
            MainFrame mFrame = new MainFrame(dbDriver);
            mFrame.setMinimumSize(new Dimension(mFrame.getWidth(),mFrame.getHeight()));
            this.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
            e.getMessage(), 
            "Error",
            JOptionPane.ERROR_MESSAGE);
        }
    }
}
