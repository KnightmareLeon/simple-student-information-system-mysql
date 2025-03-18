package main.app.errors;
/**
 * An {@code Exception} that arises when users try to multi-edit colleges.
 */
public class MultiEditCollegeException extends ExceptionWithWindow{
    public MultiEditCollegeException(){
        this.setErrMsg("Error. Multi-editing for colleges is not supported.");
        this.setErrMsgTitle("Unsupported Multi-editing");
    }
}
