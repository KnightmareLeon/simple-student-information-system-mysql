package main.data.dataClass;

/**
 * Data class that stores a {@code Student}'s data
 * <li>Data values stored are:
 * <ul>
 * <li>{@code id} - {@code String} value that stores the ID number; it is in the
 * format [YYYY-NNNN]
 * <li>{@code fn} - {@code String} value that stores the student's first name.
 * <li>{@code ln} - {@code String} value that stores the student's last name.
 * <li>{@code yl} - {@code String} value that stores the student's year level.
 * <li>{@code g} - {@code String} value that stores the student's gender.
 * <li>{@code pc} - {@code String} value that stores the program code that the
 * student is under.
 * */
public class Student {
    private String id;
    private String fn; //First Name
    private String ln; //Last Name
    private String yl; //Year Level
    private String g; //Gender
    private String pc; //Program Code

    public Student(String id, String fn, String ln, 
                   String yl, String g,  String pc){
        this.id = id;
        this.fn = fn;
        this.ln = ln;
        this.yl = yl;
        this.g = g;
        this.pc = pc;
    }
    
    /**
     * Sets the ID Number of this {@code Student}
     */
    public void setID(String id){this.id = id;}
    
    /**
     * Sets the First Name of this {@code Student}
     */
    public void setFN(String fn){this.fn = fn;}
    
    /**
     * Sets the Last Name of this {@code Student}
     */
    public void setLN(String ln){this.ln = ln;}
    
    /**
     * Sets the Year Level of this {@code Student}
     */
    public void setYL(String yl){this.yl = yl;}
    
    /**
     * Sets the Program Code of this {@code Student}
     */
    public void setG(String g){this.g = g;}

    /**
     * Sets the {@link Program} Code of this {@code Student}
     */
    public void setPC(String pc){this.pc = pc;}

    /**
     * 
     * @return This {@code Student}'s ID Number.
     */
    public String getID(){return id;}
    
    /**
     * 
     * @return This {@code Student}'s First Name.
     */
    public String getFN(){return fn;}

    /**
     * 
     * @return This {@code Student}'s Last Name.
     */
    public String getLN(){return ln;}

    /**
     * 
     * @return This {@code Student}'s Yeear Level.
     */
    public String getYL(){return yl;}

    /**
     * 
     * @return This {@code Student}'s Gender.
     */
    public String getG(){return g;}

    /**
     * 
     * @return This {@code Student}'s {@link Program} code.
     */
    public String getPC(){return pc;}

    
}
