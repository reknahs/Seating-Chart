import java.util.ArrayList;

public class Student {
    
    private String name;
    int grade;
    boolean eyesight;
    boolean hearing; 
    ArrayList<Student> near;
    ArrayList<Student> avoid;

    public Student (String name, int grade, boolean eyesight, boolean hearing) {
        this.name = name;
        this.grade = grade;
        this.eyesight = eyesight;
        this.hearing = hearing;
    }

    


}
