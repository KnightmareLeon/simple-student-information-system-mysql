package main.data.maps;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import main.data.dataClass.College;

/**
 * Custom {@code HashMap} that stores {@link College Colleges} as values
 * and their codes as keys. Also has a {@code Set} of College Names.
 */
public class CollegeMap extends HashMap<String,College> implements NameHandler{
    private Set<String> cNames = new HashSet<String>();

    public College put(String key, College value){
        if(this.containsKey(key) && !cNames.contains(value.getName())){
            this.removeName(this.get(key).getName());
        }
        cNames.add(value.getName());
        super.put(key, value);
        return value;
    }

    public College remove(String key){
        this.removeName(this.get(key).getName());
        return super.remove(key);
    }
    
    public void removeName(String name){cNames.remove(name);}

    @Override
    public boolean hasName(String name){return cNames.contains(name);}

}
