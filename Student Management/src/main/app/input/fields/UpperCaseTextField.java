package main.app.input.fields;

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import main.app.input.filters.UpperCaseDocumentFilter;

/**
 * A custom {@code @JTextField} that uses an {@code UpperCaseDocumentFilter}.
 * This makes every letter into an uppercase one.
 */
public class UpperCaseTextField extends JTextField{
    private UpperCaseDocumentFilter filter = new UpperCaseDocumentFilter();

    public UpperCaseTextField(){
        ((AbstractDocument) this.getDocument()).setDocumentFilter(filter);
        this.setPreferredSize(new Dimension(400,20));
    }

}
