import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
public class FinalProject {
    public static void main(String[] args) throws FileNotFoundException {
        //EVERYTHIG BELOW THIS COMMENT IS TESTING
        int[][] classroom = new int[14][14];
        classroom[0][0] = 1;
        classroom[0][1] = 1;
        classroom[0][3] = 1;
        classroom[0][4] = 1;
        classroom[2][0] = 1;
        classroom[2][1] = 1;
        classroom[4][0] = 1;
        classroom[4][1] = 1;
        classroom[6][0] = 1;
        classroom[6][1] = 1;
        Class a = new Class(new File("roster.txt"));
        ArrayList<Student> students = new ArrayList();
        Student one = new Student("Stephen", "", 9, true, true);
        one.setId(1); a.students.put(1, one);
        Student two = new Student("Draymond", "", 9, true, true);
        two.setId(2);a.students.put(2, two);
        Student three = new Student("Nemanja", "", 10, true, true);
        three.setId(3);a.students.put(3, three);
        Student four = new Student("Jonathan", "", 10, true, true);
        four.setId(4);a.students.put(4, four);
        Student five = new Student("PJ", "", 10, false, true);  
        five.setId(5);a.students.put(5, five);
        Student six = new Student("Bamonte", "", 9, true, false);
        six.setId(6);a.students.put(6, six);
        Student seven = new Student("JaeSean", "", 10, true, true);
        seven.setId(7);a.students.put(7, seven);
        Student eight = new Student("Jimmy", "", 10, true, true);   
        eight.setId(8);a.students.put(8, eight);
        Student nine = new Student("Jayson", "", 9, true, true);
        one.setId(9); a.students.put(9, nine);
        Student ten = new Student("Jaylen", "", 9, false, true);
        two.setId(10);a.students.put(10, ten);
        Student eleven = new Student("Luka", "", 10, true, true);
        three.setId(11);a.students.put(11, eleven);
        Student twelve = new Student("Boban", "", 10, true, false);
        four.setId(12);a.students.put(12, twelve);
        Student thirteen = new Student("Marjanovic", "", 10, true, true);  
        five.setId(13);a.students.put(13, thirteen);
        Student fourteen = new Student("Gregg", "", 9, true, true);
        six.setId(14);a.students.put(14, fourteen);
        Student fifteen = new Student("Strus", "", 10, false, true);
        seven.setId(15);a.students.put(15, fifteen);
        Student sixteen = new Student("Andre", "", 10, true, true);   
        eight.setId(16);a.students.put(16, sixteen);
        Student seventeen = new Student("Deandre", "", 9, true, true);
        one.setId(17); a.students.put(17, seventeen);
        Student eighteen = new Student("Iguodala", "", 9, true, false);
        two.setId(18);a.students.put(18, eighteen);
        Student nineteen = new Student("Klay", "", 10, true, true);
        three.setId(19);a.students.put(19, nineteen);
        Student twenty = new Student("Demarcus", "", 10, false, false);
        four.setId(20);a.students.put(20, twenty);
        Student twentyone = new Student("Kawhi", "", 10, true, true);  
        five.setId(21);a.students.put(21, twentyone);
        Student twentytwo = new Student("Gabe", "", 9, true, true);
        six.setId(22);a.students.put(22, twentytwo);
        Student twentythree = new Student("Vincent", "", 10, true, true);
        seven.setId(23);a.students.put(23, twentythree);
        Student twentyfour = new Student("Kyle", "", 10, true, false);   
        eight.setId(24);a.students.put(24, twentyfour);
        three.setAvoid(four);
        four.setAvoid(three);
        six.setAvoid(eight);
        eight.setAvoid(six);
        twelve.setAvoid(eighteen);
        eighteen.setAvoid(twelve);
        three.setNear(twentyone);
        twentyone.setNear(three);
        twentythree.setNear(seven);
        seven.setNear(twentythree);
        students.add(one); students.add(two); students.add(three); students.add(four); students.add(five); students.add(six);
        students.add(seven); students.add(eight); 
        students.add(nine); students.add(ten); students.add(eleven); students.add(twelve); students.add(thirteen); students.add(fourteen);
        students.add(fifteen); students.add(sixteen);
        students.add(seventeen); students.add(eighteen); students.add(nineteen); students.add(twenty); students.add(twentyone); students.add(twentytwo);
        students.add(twentythree); students.add(twentyfour);
        ArrayList<String> priorities = new ArrayList();
        priorities.add("Eyesight");
        priorities.add("Hearing");
        priorities.add("Avoid");
        priorities.add("Near");
        SeatingChart chart = new SeatingChart(classroom, a, priorities, students);
    }
}