import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.UUID;
import java.util.Set;

public class Class {

    public Hashtable< Integer, Student> students = new Hashtable< Integer, Student>();
    private Set<Integer> setOfKeys = students.keySet();


    public Class (File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);

        while (scan.hasNextLine()) {
            String [] name = scan.nextLine().split(" ");
            Student student = new Student (name[0], name[1]);
            UUID idOne = UUID.randomUUID();
            String str=""+idOne;        
            int uid=str.hashCode();
            String filterStr=""+uid;
            str=filterStr.replaceAll("-", "");
            Integer id = Integer.parseInt(str);
            students.put(id, student);
            student.setId(id);
        }
    }

    public Student getStudent (int id) {
       return students.get(id);
    }

    public Hashtable<Integer, Student> getStudentDict () {
        return students;
    } 

    public void listStudents () {        
        for (Integer c : setOfKeys) {
            System.out.println(students.get(c).getFirstName());
        }
    }

    public static void main (String[] args) throws FileNotFoundException {
        File file = new File("roster.txt");
        Class c = new Class(file);
        c.listStudents();
    }


}
