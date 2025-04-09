package main.app.errors;

/**
 * An {@code Exception} that arises when a program or college code already exists.
 */
public class ExistingCodeException extends ExceptionWithWindow{
    public ExistingCodeException(){
        this.setErrMsg("Error. Code already exists. Please try again.");
        this.setErrMsgTitle("Code Already Exists");
    }
}
