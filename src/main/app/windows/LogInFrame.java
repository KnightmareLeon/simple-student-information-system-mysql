package main.app.windows;

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
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import main.database.DatabaseDriver;

/**
 * This frame is used to log in to the database. The username and password
 * are passed to the {@link DatabaseDriver}, which will attempt to connect
 * to the database. If the connection is successful, the main frame
 * is opened. If the connection fails, an error message is displayed.
 * The username and password that the user must use to log in are
 * the same as the ones used to log in to the their MySQL account.
 */
public class LogInFrame extends DefaultFrame{

    public LogInFrame(){
        this.setResizable(false);
        
        final JLabel header = new JLabel("Login");
        header.setFont(header.getFont().deriveFont(Font.BOLD, 70));

        final JLabel userLabel = new JLabel("Username");
        final JTextField userField = new JTextField();
        userField.setPreferredSize(new Dimension(450,30));

        final JLabel passwordLabel = new JLabel("Password");
        final JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(450,30));

        final JButton logInButton = new JButton("Log in");
        logInButton.setPreferredSize(new Dimension(100,30));
        logInButton.putClientProperty( "JButton.buttonType", "roundRect" );
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                login(userField.getText(), new String(passwordField.getPassword()));
            }
        });
        logInButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Log in");
        logInButton.getActionMap().put("Log in", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                login(userField.getText(), new String(passwordField.getPassword()));
            }
        });

        final JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 60, 0);
        content.add(header,gbc);
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 3, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        content.add(userLabel,gbc);
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 6, 0);
        content.add(userField,gbc);
        gbc.gridy = 3; gbc.insets = new Insets(0, 0, 3, 0);
        content.add(passwordLabel,gbc);
        gbc.gridy = 4; gbc.insets = new Insets(0, 0, 30, 0);
        content.add(passwordField,gbc);
        gbc.gridy = 5; gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER; gbc.fill = GridBagConstraints.BOTH;
        content.add(logInButton,gbc);
        
        this.setContentPane(content);

        this.setSize(new Dimension(600,570));
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
