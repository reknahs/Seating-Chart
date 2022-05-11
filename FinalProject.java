import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
public class FinalProject {
    public static void main(String[] args) throws FileNotFoundException {
        //EVERYTHIG BELOW THIS COMMENT IS TESTING
        int[][] classroom = new int[14][14];
        classroom[0][0] = 1;
        classroom[0][1] = 1;
        classroom[2][0] = 1;
        classroom[2][1] = 1;
        classroom[4][0] = 1;
        classroom[4][1] = 1;
        ArrayList<Student> students = new ArrayList();
        students.add(new Student("Deaf1", "", 9, true, false));
        students.add(new Student("Khadija", "", 10, true, true));
        students.add(new Student("Harsha", "", 10, true, true));
        students.add(new Student("Shanker", "", 10, true, true));  
        students.add(new Student("Blind and Deaf", "", 9, false, false));
        students.add(new Student("Deaf2", "", 10, true, false));
        students.add(new Student("Blind", "", 10, false, true));   
        ArrayList<String> priorities = new ArrayList();
        priorities.add("Eyesight");
        priorities.add("Hearing");
        for(Student s: students) System.out.println(s.getFirstName());
        System.out.println();
        Class a = new Class(new File("roster.txt"));
        SeatingChart chart = new SeatingChart(classroom, a, priorities, students);
    }
}