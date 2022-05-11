import java.util.ArrayList;
public class FinalProject {
    public static void main(String[] args) {
        //EVERYTHIG BELOW THIS COMMENT IS TESTING
        boolean[][] classroom = new boolean[14][14];
        classroom[0][0] = true;
        classroom[0][1] = true;
        classroom[2][0] = true;
        classroom[2][1] = true;
        classroom[4][0] = true;
        classroom[4][1] = true;
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
        SeatingChart chart = new SeatingChart(classroom, students, priorities);
    }
}