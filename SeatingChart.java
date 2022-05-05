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

    public Student[][] getBestChart() {
        ArrayList<Student> newOrder;
        for(int i = 0; i < priorities.size(); i++) {
            newOrder = sort(newOrder(priorites[i]))
        }
        return new Student[][]{};
    }
}
