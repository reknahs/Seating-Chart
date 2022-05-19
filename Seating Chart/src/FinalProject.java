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
        classroom[6][0] = 1;
        classroom[6][1] = 1;
        Class a = new Class(new File("roster.txt"));
        ArrayList<Student> students = new ArrayList();
        Student one = new Student("Deaf1", "", 9, true, false);
        one.setId(1); a.students.put(1, one);
        Student two = new Student("Deaf3", "", 9, true, false);
        two.setId(2);a.students.put(2, two);
        Student three = new Student("Khadija", "", 10, true, true);
        three.setId(3);a.students.put(3, three);
        Student four = new Student("Harsha", "", 10, true, true);
        four.setId(4);a.students.put(4, four);
        Student five = new Student("Shanker", "", 10, true, true);  
        five.setId(5);a.students.put(5, five);
        Student six = new Student("Blind and Deaf", "", 9, false, false);
        six.setId(6);a.students.put(6, six);
        Student seven = new Student("Deaf2", "", 10, true, false);
        seven.setId(7);a.students.put(7, seven);
        Student eight = new Student("Blind", "", 10, false, true);   
        eight.setId(8);a.students.put(8, eight);
        one.setAvoid(two);
        two.setAvoid(one);
        three.setAvoid(four);
        five.setAvoid(four);
        four.setAvoid(three);
        six.setAvoid(eight);
        eight.setAvoid(six);
        students.add(one); students.add(two); students.add(three); students.add(four); students.add(five); students.add(six);
        students.add(seven); students.add(eight);
        ArrayList<String> priorities = new ArrayList();
        priorities.add("Avoid");
        SeatingChart chart = new SeatingChart(classroom, a, priorities, students);
    }
}