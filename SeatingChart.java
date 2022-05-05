import java.util.ArrayList;
public class SeatingChart {

    private boolean[][] classroom;
    private ArrayList<Student> students;
    private ArrayList<String> priorities;

    public SeatingChart(boolean[][] classroom, ArrayList<Student> students, ArrayList<String> priorities) {
        this.classroom = classroom;
        this.students = students;
        this.priorities = priorities; 

        Student[][] order = getBestChart();
    }

    public Student[][] getBestChart() {;
        for(int i = 0; i < priorities.size(); i++) {
            students = sort(priorities.get(i));
        }
        return new Student[][]{};
    }

    public ArrayList<Student> sort(String priority) {
        ArrayList<Student> newOrder = new ArrayList();
        for(int i = 0; i < students.size(); i++) {
            for(int j = 0; j < students.size(); j++) {
                //temp
                int a = 1;
            }
        }
        return new ArrayList<Student>();

    }
}
