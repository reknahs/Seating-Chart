import java.util.ArrayList;

public class Student {
    
    private String firstName;
    private String lastName;
    private int grade;
    private boolean eyesight;
    private boolean hearing; 
    private ArrayList<Student> near = new ArrayList();
    private ArrayList<Student> avoid = new ArrayList();
    private int id;

    public Student (String first, String last, int grade, boolean eyesight, boolean hearing) {
        firstName = first;
        lastName = last;
        this.grade = grade;
        this.eyesight = eyesight;
        this.hearing = hearing;
    }

    public Student (String first, String last) {
        firstName = first;
        lastName = last;
    }

    public String getFirstName () {
        return firstName;
    }
    
    public String getLastName () {
        return lastName;
    }

    public boolean getEyesight () {
        return eyesight;
    }

    public boolean getHearing () {
        return hearing;
    }

    public ArrayList<Student> getNear() {
        return near;
    }
    
    public ArrayList<Student> getAvoid() {
        return avoid;
    }

    public void setNear(Student student) {
        near.add(student);
    }

    public void setAvoid (Student student) {
        avoid.add(student);
    }

    public void setId (int id) {
        this.id = id;
    }

    public int getId () {
        return id;
    }
    }
