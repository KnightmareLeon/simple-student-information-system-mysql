package main.app.input.filters;

import javax.swing.RowFilter;
import javax.swing.table.TableModel;

import main.app.tables.pageHandler.PageHandler;

public class PageFilter extends RowFilter<TableModel, Integer>{
    PageHandler pageHandler;
    public PageFilter(PageHandler pageHandler){
        this.pageHandler = pageHandler;
    }
    @Override 
        public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
            int ti = pageHandler.getCurrentPageIndex() - 1;
            int ei = entry.getIdentifier();
            int items = pageHandler.getItemPerPage();
            return ti * items <=ei  && ei < ti * items + items;
        }

}
