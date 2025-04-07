package main.app.input.fields;

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import main.app.input.filters.LimitedUpperCaseDocumentFilter;

/**
 * A custom {@code @JTextField} that uses an {@code UpperCaseDocumentFilter}.
 * This makes every letter into an uppercase one.
 */
public class UpperCaseTextField extends JTextField{
    private LimitedUpperCaseDocumentFilter filter;

    public UpperCaseTextField(int limit){
        filter = new LimitedUpperCaseDocumentFilter(limit);
        ((AbstractDocument) this.getDocument()).setDocumentFilter(filter);
        this.setPreferredSize(new Dimension(400,20));
    }

}
