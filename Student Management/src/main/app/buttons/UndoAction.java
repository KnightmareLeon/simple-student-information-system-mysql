package main.app.buttons;

import main.app.input.InputType;

public class UndoAction {
    private InputType inputType;
    private String tableName;
    private String[] data = null;
    private String[][] dataArray = null;
 
    public UndoAction(InputType inputType, String tableName, String[] data){
        this.inputType = inputType;
        this.tableName = tableName;
        this.data = data;
       
    }

    public UndoAction(InputType inputType, String tableName, String[][] dataArray){
        this.inputType = inputType;
        this.tableName = tableName;
        this.dataArray = dataArray;
    }

    public void undo(){
        switch(inputType){
            case ADD:

                break;
            case DELETE:
                break;
            case DELETE_MULTIPLE:
                break;
            case EDIT_MULTIPLE:
                break;
            case EDIT_SINGLE:
                break;
            default:
                break;
            
        }
    }
}
