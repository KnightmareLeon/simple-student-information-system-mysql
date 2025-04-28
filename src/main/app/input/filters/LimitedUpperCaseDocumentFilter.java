package main.app.input.filters;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Custom {@code DocumentFilter} that makes all letters into uppercase.
 */
public class LimitedUpperCaseDocumentFilter extends LimitedDocumentFilter {

    public LimitedUpperCaseDocumentFilter(int limit) {
        super(limit);
    }

    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
        if (text != null) {text = text.toUpperCase();}
        super.insertString(fb, offset, text, attr);
    }
    
    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text != null) {text = text.toUpperCase();}
        super.replace(fb, offset, length, text, attrs);
    }
}
