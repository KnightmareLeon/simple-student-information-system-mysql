package main.app.input.filters;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class SearchBarFilter extends LimitedUpperCaseDocumentFilter{

    public SearchBarFilter(int limit){
        super(limit);
    }

    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
        super.insertString(fb, offset, text.replaceAll("[;]", ""), attr);
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text != null) {
            text = text.replaceAll("[;]", "");
        }
        super.replace(fb, offset, length, text, attrs);
    }


}
