package main.app.errors;

/**
 * An {@code Exception} that arises when a student, program, or college
 * name already exists.
 */
public class ExistingNameException extends ExceptionWithWindow{
    public ExistingNameException(){
        this.setErrMsg("Error. Name already exists. Please try again.");
        this.setErrMsgTitle("Name Already Exists");

    }
}
