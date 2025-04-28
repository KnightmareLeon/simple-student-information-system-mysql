package main.app.input.filters;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public abstract class LimitedDocumentFilter extends DocumentFilter{
    private int limit;

    public LimitedDocumentFilter(int limit) {
        this.limit = limit;
    }

    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
        if (text != null && (fb.getDocument().getLength() + text.length()) > limit) {
            text = text.substring(0, limit - fb.getDocument().getLength());
        }
        super.insertString(fb, offset, text, attr);
    }
    
    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text != null && (fb.getDocument().getLength() + text.length()) > limit) {
            text = text.substring(0, limit - fb.getDocument().getLength());
        }
        super.replace(fb, offset, length, text, attrs);
    }
}
