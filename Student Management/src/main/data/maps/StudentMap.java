package main.data.maps;

import java.util.HashMap;

import main.data.dataClass.Student;

/**
 * Custom {@code HashMap} that stores {@link Student Students} as values
 * and their ID numbers as keys. Also has a {@code Set} of student names.
 */
public class StudentMap extends HashMap<String,Student> implements NameHandler{
    private HashMap<String, Integer> sNames = new HashMap<String, Integer>();

    public Student put(String key, Student value){
        String valueName = value.getFN() + " " + value.getLN();
        
        if(this.containsKey(key)){
            String keyName = this.get(key).getFN() + " " + this.get(key).getLN();
            if(!(keyName).equals(valueName)){
                sNames.put(keyName, sNames.get(keyName) - 1);
                if(sNames.get(keyName) == 0){
                    sNames.remove(keyName);
                }
            }
            
        }
        
        if(sNames.containsKey(valueName)){
            sNames.put(valueName, sNames.get(valueName) + 1);
        } else {
            sNames.put(valueName, 1);
        }

        super.put(key, value);
        return value;
    }

    public Student remove(String key){
        sNames.put(this.get(key).getFN() + " " + this.get(key).getLN(), sNames.get(this.get(key).getFN() + " " + this.get(key).getLN()) - 1);
        if(sNames.get(this.get(key).getFN() + " " + this.get(key).getLN()) == 0){
            sNames.remove(this.get(key).getFN() + " " + this.get(key).getLN());
        }
        return super.remove(key);
    }

    @Override
    public boolean hasName(String name) {return sNames.containsKey(name);}

    
}
