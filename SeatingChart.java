import java.util.ArrayList;

public class SeatingChart {

    private boolean[][] classroom; // every spot with a desk is true
    private ArrayList<Student> students; // contains all students
    private ArrayList<String> priorities; // has priorities in order, most prioritized at the front

    public SeatingChart(boolean[][] classroom, ArrayList<Student> students, ArrayList<String> priorities) {
        this.classroom = classroom;
        this.students = students;
        this.priorities = priorities; 

        Student[][] order = getBestChart();
    }

    public ArrayList<Integer> getSplits(int index) {
        ArrayList<Integer> indices = new ArrayList();
        indices.add(0);
        for(int i = 1; i < students.size(); i++) {
            for(int j = 0; j < index; j++) {
                if(priorities.get(j).equals("Hearing")) {
                    if(students.get(i-1).getHearing() != students.get(i).getHearing()) {
                        indices.add(i);
                    }
                }
                else if(priorities.get(j).equals("Eyesight")) {
                    if(students.get(i-1).getEyesight() != students.get(i).getEyesight()) {
                        indices.add(i);
                    }
                }
            }
        }
        indices.add(students.size());
        return indices;
    }

    //primary sorting algorithm, splits it up into groups, and then sorts those
    // EX -> eyesight (e) first priority, hearing (h) second priority
    // if the original random order is {Bob(h), Khadija, Harsha, Shanker, Lamarcus(e, h), Deandre(h), Kyle(e)}
    // then the first pass results in -> {[Kyle(e), Lamarcus(e, h)], [Deandre(h), Shanker, Harsha, Khadija, Bob(h)]}
    // it sorts by 
    // the second and final pass results in -> {[[Lamracus(e, h)], Kyle(e)], [[Bob(h), Deandre(h)], [Khadija, Harsha, Shanker]]}
    public Student[][] getBestChart() {
        ArrayList<Integer> indices = new ArrayList();
        for(int i = 0; i < priorities.size(); i++) {
            if(i == 0) {
                students = sort(priorities.get(i), students);
                for(Student s: students) System.out.println(s.getFirstName());
                System.out.println();
                continue;
            }
            indices = getSplits(i);
            ArrayList<Student> modified_students = new ArrayList();
            for(int j = 1; j < indices.size(); j++) {
                ArrayList<Student> new_segment = new ArrayList();
                for(int k = indices.get(j-1); k < indices.get(j); k++) {
                    new_segment.add(students.get(k));
                }
                new_segment = sort(priorities.get(i), new_segment);
                for(Student student: new_segment) modified_students.add(student);
            }
            students = modified_students;
        }
        for(Student i: students) System.out.println(i.getFirstName());
        return new Student[][]{};
    }

    public ArrayList<Student> sort(String priority, ArrayList<Student> segment) {
        ArrayList<Student> newOrder = new ArrayList();
        ArrayList<Student> taken = new ArrayList();
        int size = segment.size();
        for(int i = 0; i < segment.size(); i++) {
            Student optimal = segment.get(0);
            for(Student j: segment) if(!taken.contains(j)) optimal = j;
            for(int j = 0; j < segment.size(); j++) {
                if(priority.equals("Eyesight") && !taken.contains(segment.get(j))) {
                    if(!segment.get(j).getEyesight()) optimal = segment.get(j);
                }
                else if(priority.equals("Hearing") && !taken.contains(segment.get(j))) {
                    if(!segment.get(j).getHearing()) optimal = segment.get(j);
                }   
            }
            taken.add(optimal);
            newOrder.add(optimal);
        }
        return newOrder;

    }
}
