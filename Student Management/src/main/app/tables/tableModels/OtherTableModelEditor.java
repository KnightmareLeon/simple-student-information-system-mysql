package main.app.tables.tableModels;

/**
 * Interface that allows {@code CSVHandlingTableModel} to handle data of another
 * {@code CSVHandlingTableModel}
 * @see CSVHandlingTableModel {@code CSVHandlingTableModel}
 */
public interface OtherTableModelEditor {
    
    /**
     * Edits data on another {@code CSVHandlingTableModel}
     * @param prevData {@code String} data that is also present
     * in the other table model, which is used to identify which
     * row to edit
     * @param newData {@code String} value that will replace the
     * previous data
     */
    public void editOtherTableModel(String prevData, String newData);

    /**
     * Deletes data from another {@code CSVHandlingTableModel}
     * @param code - {@code String} key that is also present in
     * the other table model, which is used to identify which row
     * to delete.
     */
    public void deleteFromOtherTableModel(String code);
}
