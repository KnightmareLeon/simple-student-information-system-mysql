package main.data.maps;

import main.data.dataClass.College;
import main.data.dataClass.Program;
import main.data.dataClass.Student;

/**
 * The main map that handles all the data during the application's runtime.
 * @see StudentMap {@code StudentMap}
 * @see ProgramMap {@code ProgramMap}
 * @see CollegeMap {@code CollegeMap}
 */
public class DataMap {
    private CollegeMap cMap = new CollegeMap();
    private ProgramMap pMap = new ProgramMap();
    private StudentMap sMap = new StudentMap();

    /**
     * Adds a new {@code College} to the {@link CollegeMap}
     * @param data - {@code String} array containing the {@code College}'s
     * data
     */
    public void addCollege(String[] data){
        //data[0] - Code
        //data[1] - Name
        this.cMap.put(data[0], new College(data[0], data[1]));
    }

    /**
     * Adds a new {@code Program} to the {@link ProgramMap}
     * @param data - {@code String} array containing the {@code Program}'s
     * data
     */
    public void addProgram(String[] data){
        //data[0] - Code
        //data[1] - Name
        //data[2] - College Code
        this.pMap.put(data[0], new Program(data[0],data[1],data[2]));
        this.getCollege(data[2]).addProgram(getProgram(data[0]));
    }

    /**
     * Add a new {@code Student} to the {@link StudentMap}
     * @param data {@code String} array containing the {@code Student}'s
     * data
     */
    public void addStudent(String[] data){
        //data[0] - ID
        //data[1] - First Name
        //data[2] - Last Name
        //data[3] - Year Level
        //data[4] - Gender
        //data[5] - Program Code
        this.sMap.put(data[0], new Student(data[0],data[1],data[2],data[3],data[4],data[5]));
        this.getProgram(data[5]).addStudent(this.getStudent(data[0]));
    }

    /**
     * Deletes a {@code College} from {@link CollegeMap}
     * @param cCode - {@code String} key of the {@code College} that will be 
     * deleted
     */
    public void removeCollege(String cCode){
        for(String code : this.cMap.get(cCode).getProgramList()){
            for(String ID : this.pMap.get(code).getStudentList()){this.sMap.remove(ID);}
            this.pMap.remove(code);
        }
        this.cMap.remove(cCode);
    }

    /**
     * Deletes a {@code Program} from {@link ProgramMap}
     * @param pCode - {@code String} key of the {@code Program} that will be 
     * deleted
     */
    public void removeProgram(String pCode){
        this.getCollege(getProgram(pCode).getCCode()).removeProgram(getProgram(pCode));
        for(String ID : this.pMap.get(pCode).getStudentList()){this.sMap.remove(ID);}
        this.pMap.remove(pCode);
    }

    /**
     * Deletes a {@code Student} from {@link StudentMap}
     * @param id - {@code String} key of the {@code Student} that will be 
     * deleted
     */
    public void removeStudent(String id){
        this.getProgram(getStudent(id).getPC()).removeStudent(getStudent(id));
        this.sMap.remove(id);
    }

    /**
     * Edits an existing {@code Student}'s data in the {@link StudentMap}
     * @param prevID - previous ID Number of the {@code Student} whose
     * data will be edited; may still be the same ID Number in the new
     * data
     * @param newData - {@code String array} containing the new data that
     * will replace the previous data
     */
    public void editStudent(String prevID, String[] newData){
        String prevPC = this.getStudent(prevID).getPC();
        if(!prevID.equals(newData[0]) || !prevPC.equals(newData[5])){ //Checks if ID or Program Code was changed 
            this.removeStudent(prevID);
        }
        this.addStudent(newData);
    }

    /**
     * Edits an existing {@code Program}'s data in the {@link ProgramMap}
     * @param prevCode - previous code of the {@code Program} whose
     * data will be edited; may still be the same code in the new
     * data
     * @param newData - {@code String array} containing the new data that
     * will replace the previous data
     */
    public void editProgram(String prevCode, String[] newData){
        String prevCCode = this.getProgram(prevCode).getCCode();
        String prevName = this.getProgram(prevCode).getName();
        if(!prevCode.equals(newData[0])){
            this.addProgram(newData);
            this.getProgram(prevCode).transferStudents(this.getProgram(newData[0]));
            this.removeProgram(prevCode);
        } else if(!prevCCode.equals(newData[2])){
            this.getCollege(getProgram(prevCode).getCCode()).removeProgram(getProgram(prevCode));
            this.getProgram(prevCode).setCCode(newData[2]);
            this.getCollege(newData[2]).addProgram(this.getProgram(prevCode));
        } 
        if (!prevName.equals(newData[1])){
            this.getProgram(newData[0]).setName(newData[1]);
            this.pMap.removeName(prevName);
        }
        
    }

    /**
     * Edits an existing {@code College}'s data in the {@link CollegeMap}
     * @param prevCode - previous code of the {@code College} whose
     * data will be edited; may still be the same ID Number in the new
     * data
     * @param newData - {@code String array} containing the new data that
     * will replace the previous data
     */
    public void editCollege(String prevCode, String[] newData){
        if(!prevCode.equals(newData[0])){
            this.addCollege(newData);
            this.getCollege(prevCode).transferPrograms(this.getCollege(newData[0]));
            this.removeCollege(prevCode);
        } else {
            this.cMap.removeName(this.getCollege(prevCode).getName());
            this.getCollege(prevCode).setName(newData[1]);
        }
    }

    /**
     * Edits multiple existing {@code Students}' data in the {@link StudentMap}
     * @param ids - array containing the ID Number keys of the {@code Students}
     * that will have their data edited
     * @param newData - {@code String array} containing the new data that
     * will replace the previous data
     */
    public void editMultiStudent(String[] ids, String[] newData){
        for(String id: ids){
            Student std = this.getStudent(id);
            String prevCode = std.getPC();
            if(!newData[0].equals("NO UPDATE")){std.setYL(newData[0]);}
            if(!newData[1].equals("NO UPDATE")){std.setG(newData[1]);}
            if(!prevCode.equals(newData[2]) && !newData[2].equals("NO UPDATE")){
                this.getProgram(newData[2]).addStudent(std);
                std.setPC(newData[2]);
                this.getProgram(prevCode).removeStudent(std);
            }
        }
    }

    /**
     * Edits multiple existing {@code Programs}' data in the {@link ProgramMap}
     * @param codes - array containing the codes of the {@code Programs}
     * that will have their data edited
     * @param newData - {@code String array} containing the new data that
     * will replace the previous data
     */
    public void editMultiProgram(String[] codes, String newCCode){
        for(String code: codes){
            String prevCCode = this.getProgram(code).getCCode();
            if(!prevCCode.equals(newCCode)){
                this.getCollege(prevCCode).removeProgram(this.getProgram(code));
                this.getProgram(code).setCCode(newCCode);
                this.getCollege(newCCode).addProgram(this.getProgram(code));
            }
        }
    }

    /**
     * 
     * @param cCode - {@code College} code {@code String} key
     * @return The {@link main.data.dataClass.College College} from the
     * {@link CollegeMap}
     */
    public College getCollege(String cCode){return this.cMap.get(cCode);}
    
    /**
     * 
     * @param pCode - {@code Program} code {@code String} key
     * @return The {@link main.data.dataClass.Program Program} from the
     * {@link ProgramMap}
     */
    public Program getProgram(String pCode){return this.pMap.get(pCode);}
    
    /**
     * 
     * @param id - ID Number {@code String} key
     * @return The {@link main.data.dataClass.Student Student} from the
     * {@link StudentMap}
     */
    public Student getStudent(String id){return this.sMap.get(id);}
    
    /**
     * 
     * @return The {@code String} array containing all {@code Program}'s Codes
     */
    public String[] getProgramList(){return this.pMap.keySet().toArray(new String[pMap.size()]);}
    
    /**
     * 
     * @return The {@code String} array containing all {@code College}'s Codes
     */
    public String[] getCollegeList(){return this.cMap.keySet().toArray(new String[cMap.size()]);}

    /**
     * 
     * @param name
     * @return {@code true} if this {@code Student}'s name already exists
     */
    public boolean hasStudentName(String name){return this.sMap.hasName(name);}
    
    /**
     * 
     * @param name
     * @return {@code true} if this {@code Program}'s name already exists
     */
    public boolean hasProgramName(String name){return this.pMap.hasName(name);}
    
    /**
     * 
     * @param name
     * @return {@code true} if this {@code College}'s name already exists
     */
    public boolean hasCollegeName(String name){return this.cMap.hasName(name);}

    /**
     * 
     * @param name
     * @return {@code true} if this {@code Student}'s ID Number already exists
     */
    public boolean hasID(String id){return this.sMap.containsKey(id);}

    /**
     * 
     * @param name
     * @return {@code true} if this {@code Program}'s code already exists
     */
    public boolean hasProgramCode(String code){return this.pMap.containsKey(code);}
    
    /**
     * 
     * @param name
     * @return {@code true} if this {@code College}'s code already exists
     */
    public boolean hasCollegeCode(String code){return this.cMap.containsKey(code);}
}
