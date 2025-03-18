package main.data.dataClass;

import java.util.HashMap;
import java.util.Set;

/**
 * Data class that stores a {@code College}'s data
 * <li>Data values stored are:
 * <ul>
 * <li>{@code code} - {@code String} value that stores the college's code.
 * <li>{@code name} - {@code String} value that stores the college's name.
 * <li>{@code prgMap} - a {@code HashMap<String,Program>} that stores a map of
 * {@link Program Programs} under the college.
 * </ul>
 * 
 */
public class College {

    private String code;
    private String name;
    private HashMap<String,Program> prgMap = new HashMap<String,Program>(); //List of Programs

    public College(String code, String name){
        this.code = code;
        this.name = name;
    }
    
    /**
     * Sets this {@code College}'s code.
     * @param code
     */
    public void setCode(String code){this.code = code;}

    /**
     * Sets this {@code College}'s name.
     * @param name
     */
    public void setName(String name){this.name = name;}

    /**
     * 
     * @return This {@code College}'s code.
     */
    public String getCode(){return code;}

    /**
     * 
     * @return This {@code College}'s name.
     */
    public String getName(){return name;}

    /**
     * Add a {@link Program} that is under this {@code College}
     * @param prg
     */
    public void addProgram(Program prg){prgMap.put(prg.getCode(),prg);}

    /**
     * Removes a {@link Program} under this {@code College}
     * @param prg
     */
    public void removeProgram(Program prg){prgMap.remove(prg.getCode(),prg);}

    /**
     * 
     * @return The {@link Program Programs} under this {@code College}
     * as {@code Set<String>}
     */
    public Set<String> getProgramList(){return prgMap.keySet();}

    /**
     * 
     * @return The total number of {@link Program Programs} under this
     * {@code College} 
     */
    public int getTotalProgram(){return prgMap.size();}

    /**
     * 
     * @return The total number of {@link Student Students} under the
     * {@link Program Programs} this {@code college}
     */
    public int getTotalStudents(){
        int total = 0;
        for(String prg: this.getProgramList()){total += this.prgMap.get(prg).getTotalStudents();}
        return total;
    }

    /**
     * Transfers the {@link Program Programs} under this {@code College} to the
     * new {@code College}. This method is only called when editing a {@code College}
     * in the {@link main.data.maps.DataMap DataMap} and its code was changed
     * @see main.data.maps.DataMap#editCollege(String, String[]) {@code editCollege(String, String[])}
     * @param newCollege
     */
    public void transferPrograms(College newCollege){
        this.prgMap.forEach((key, value) ->{
            value.setCCode(newCollege.getCode());
            newCollege.addProgram(value);
            }
        );
        this.prgMap.clear();
    }
}
