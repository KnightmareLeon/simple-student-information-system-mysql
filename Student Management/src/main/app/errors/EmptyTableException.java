package main.app.errors;

public class EmptyTableException extends ExceptionWithWindow{
    public EmptyTableException(){
        this.setErrMsg("Error. Parent table is empty, please add input to parent table first.");
        this.setErrMsgTitle("Empty Parent Table");
    }
}
