package main.app.input.fields;

import java.awt.Dimension;

import javax.swing.JComboBox;

import main.app.input.filters.AutoCompletion;

/**
 * Custom {@code JComboBox<String>} that autocomplete's a user's
 * input. It uses the {@link AutoCompletion} class authored by <b>Thomas Bierhance</b>,
 * which was released to the public domain. To view a copy of the public domain 
 * dedication, visit http://creativecommons.org/licenses/publicdomain/
 */
public class AutoCompletingComboBox extends JComboBox<String> {
    public AutoCompletingComboBox(String[] arg0){
        super(arg0);
        AutoCompletion.enable(this);
        this.setPreferredSize(new Dimension(100,20));
    }
}
