package main.app.input.fields;

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import main.app.input.filters.UpperCaseOnlyDocumentFilter;

/**
 * A custom {@code @JTextField} that uses an {@code UpperCaseOnlyDocumentFilter}.
 * This only accepts uppercase letters along with some selected special characters.
 */
public class UpperCaseOnlyTextField extends JTextField{
    private UpperCaseOnlyDocumentFilter filter = new UpperCaseOnlyDocumentFilter();

    public UpperCaseOnlyTextField(){
        ((AbstractDocument) this.getDocument()).setDocumentFilter(filter);
        this.setPreferredSize(new Dimension(400,20));
    }
}
