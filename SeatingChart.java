import java.util.ArrayList;

public class SeatingChart {

    private int[][] classroom; // every spot without desk is 0, every spot with desk is 1
    private ArrayList<String> priorities; // has priorities in order, most prioritized at the front
    private ArrayList<Student> students; // every student

    // contructor
    public SeatingChart(int[][] classroom, Class students, ArrayList<String> priorities, ArrayList<Student> TEMPORARY) {
        this.classroom = classroom;
        this.students = new ArrayList();
        // for(int s: students.getStudentDict().keySet()) {
        //     this.students.add(students.getStudentDict().get(s));
        // }
        this.students = TEMPORARY;
        this.priorities = priorities; 
        Student[][] order = getBestChart();
    }

    // part of initial sorting algorithm, finds the bounds of all the current sorting 
    // EX: if we have finished the first priority (in this case eysight), if the current array is [bad_eye, bad_eye, good_eye]
    // the function returns 0 2 3 
    // because our greedy algorithm now knows it has permission to shuffle indexs 0 - 2, and 0 - 1, but it has to sort 
    // each of those independently
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
    // it sorts by eyesight
    // the second and final pass results in -> {[[Lamracus(e, h)], Kyle(e)], [[Bob(h), Deandre(h)], [Khadija, Harsha, Shanker]]}
    // this one sorts by hearing
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

    // sorts a given segment of students by a certain priority, returns an arraylist of the new order
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
