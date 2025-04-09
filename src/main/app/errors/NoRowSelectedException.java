package main.app.errors;

/**
 * An {@code Exception} that arises when no row is selected in the
 * {@link main.app.tables.ManagementTable ManagementTable}.
 */
public class NoRowSelectedException extends ExceptionWithWindow{
    public NoRowSelectedException(){
        this.setErrMsg("Error. No row selected. Please select a row.");
        this.setErrMsgTitle("No Row Selected");

    }
}
