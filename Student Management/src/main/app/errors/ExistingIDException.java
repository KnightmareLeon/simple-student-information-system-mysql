package main.app.errors;

/**
 * An {@code Exception} that arises when an ID number already exists.
 */
public class ExistingIDException extends ExceptionWithWindow{
    public ExistingIDException(){
        this.setErrMsg("Error. ID already exists. Please try again.");
        this.setErrMsgTitle("ID Already Exists.");
    }
}
