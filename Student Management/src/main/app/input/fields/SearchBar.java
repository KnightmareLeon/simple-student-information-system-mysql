package main.app.input.fields;

import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * A custom {@code JTextField} that is a child class of {@link UpperCaseTextField}.
 * Filters a {@code JTable} by whatever is the input text of this text field 
 * through a {@code TableRowSorter}. The columns that it filters is based on 
 * {@link SearchFieldList}, if the chosen item is "Any", this will filter
 * the {@code JTable} based on any column, else it will be based on the
 * chosen choice of the user
 * 
 * <p>
 * For this application, it filters the {@code ManagementTable}.
 * @see UpperCaseTextField {@code UpperCaseTextField}
 * @see main.app.tables.ManagementTable {@code ManagementTable}
 */
public class SearchBar extends UpperCaseTextField{
    public SearchBar(TableRowSorter<TableModel> rowSorter, SearchFieldList searchFieldList){
        this.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else if (searchFieldList.getIndex() == -1){
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, searchFieldList.getIndex()));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else if (searchFieldList.getIndex() == -1){
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, searchFieldList.getIndex()));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
    }
}
