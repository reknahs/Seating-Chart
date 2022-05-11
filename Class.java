import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.UUID;
import java.util.Set;

public class Class {

    private Hashtable<String, Student> students = new Hashtable<String, Student>();


    public Class (File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);

        while (scan.hasNextLine()) {
            String [] name = scan.nextLine().split(" ");
            Student student = new Student (name[0], name[1]);
            String id = UUID.randomUUID().toString();
            students.put(id, student);
        }
    }

    public Student getStudent (String id) {
       return students.get(id);
    }

    public void listStudents () {
        Set<String> setOfKeys = students.keySet();
        
        for (String c : setOfKeys) {
            System.out.println(students.get(c).getFirstName());
        }
    }

    public static void main (String[] args) throws FileNotFoundException {
        File file = new File("roster.txt");
        Class c = new Class(file);
        c.listStudents();
    }

}
