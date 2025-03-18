package main.data.dataClass;

import java.util.HashMap;
import java.util.Set;

/**
 * Data class that stores a {@code Program}'s data
 * <li>Data values stored are:
 * <ul>
 * <li>{@code code} - {@code String} value that stores the program's code.
 * <li>{@code name} - {@code String} value that stores the program's name.
 * <li>{@code cCode} - {@code String} value that stores the college code that 
 * the program is under.
 * <li>{@code clgMap} - a {@code HashMap<String,Student>} that stores a map of
 * {@link Student Students} under the program.
 * </ul>
 * 
 */
public class Program {
    
    private String code; //Program Code
    private String name; //Program Name
    private String cCode; //College Code
    private HashMap<String,Student> stdMap = new HashMap<String,Student>(); //Students under this Program

    public Program(String code, String name, String cCode){
        this.code = code;
        this.name = name;
        this.cCode = cCode;
    }
    
    /**
     * Sets the code of the program
     * @param code
     */
    public void setCode(String code){this.code = code;}
    
    /**
     * Sets the name of this program
     * @param name
     */
    public void setName(String name){this.name = name;}
    
    /**
     * Sets the {@code College} Code
     * @param cCode
     */
    public void setCCode(String cCode){this.cCode = cCode;}

    /**
     * 
     * @return the code of this {@code Program}
     */
    public String getCode(){return code;}
    
    /**
     * 
     * @return the name of this {@code Program}
     */
    public String getName(){return name;}

    /**
     * 
     * @return the {@code College} Code of this {@code Program}
     */
    public String getCCode(){return cCode;}

    /**
     * Add a {@code Student} that is under this {@code Program}
     * @param std - {@link Student} to be added
     */
    public void addStudent(Student std){this.stdMap.put(std.getID(),std);}

    /**
     * Removes a {@link Student} under this program
     * @param std
     */
    public void removeStudent(Student std){this.stdMap.remove(std.getID(),std);}

    /**
     * 
     * @return The {@link Student Students} under this {@code Program}
     * as {@code Set<String>}
     */
    public Set<String> getStudentList(){return stdMap.keySet();}

    /**
     * 
     * @return The total number of {@link Student Students} under this
     * {@code Program} 
     */
    public int getTotalStudents(){return stdMap.size();}

    /**
     * Transfers the {@link Student Students} under this {@code Program} to the
     * new {@code Program}. This method is only called when editing a {@code Program}
     * in the {@link main.data.maps.DataMap DataMap} and its code was changed.
     * @see main.data.maps.DataMap#editProgram(String, String[]) {@code editProgram(String, String[])}
     * @see main.data.maps.DataMap#editMultiProgram(String[], String) {@code multiEditProgram(String[], String)}
     * @param newProgram
     */
    public void transferStudents(Program newProgram){
        this.stdMap.forEach((key, value) ->{
            value.setPC(newProgram.getCode());
            newProgram.addStudent(value);
            }
        );
        this.stdMap.clear();
    }
}
