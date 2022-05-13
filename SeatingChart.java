import java.util.ArrayList;

public class SeatingChart {

    private int[][] classroom; // every spot without desk is 0, every spot with desk is 1
    private ArrayList<String> priorities; // has priorities in order, most prioritized at the front
    private ArrayList<Student> students; // every student
    private Class students_id; 
    private int[][] positions;

    // contructor
    public SeatingChart(int[][] classroom, Class students, ArrayList<String> priorities, ArrayList<Student> TEMPORARY) {
        this.classroom = classroom;
        this.students = new ArrayList();
        // for(int s: students.getStudentDict().keySet()) {
        //     this.students.add(students.getStudentDict().get(s));
        // }
        this.students_id = students;
        this.students = TEMPORARY;
        this.priorities = priorities; 
        getBestChart();
        int iterator = 0;
        this.positions = new int[this.students.size()][2];
        for(int i = 0; i < classroom.length; i++) {
            for(int j = 0; j < classroom[i].length; j++) {
                if(this.classroom[i][j] == 1) {
                    this.positions[iterator] = new int[]{i, j};
                    this.classroom[i][j] = this.students.get(iterator).getId();
                    iterator++;
                }
            }
        }
        double curr_score = mean_score(this.classroom);
        sort2(curr_score, 0);
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
    public void getBestChart() {
        ArrayList<Integer> indices = new ArrayList();
        for(int i = 0; i < priorities.size(); i++) {
            if(i == 0) {
                students = sort(priorities.get(i), students);
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
    
    // finds the location of a student based on their id
    public int[] getLocation(Student s) {
        for(int i = 0; i < classroom.length; i++) {
            for(int j = 0; j < classroom[i].length; j++) {
                if(classroom[i][j] == s.getId()) return new int[]{i, j};
            }
        }
        return new int[]{0, 0};
    }

    // calculates a "score" for a seating arrangment based on all of the student attributes + the prioritization of the attributes
    public double mean_score(int[][] room) {
        double total_score = 0; 
        for(int i = 0; i < room.length; i++) {
            for(int j = 0; j < room[i].length; j++) {
                if(room[i][j] == 0) continue;
                double score = 0;
                for(int p = 0; p < priorities.size(); p++) {
                    double priority_weight = Math.abs(priorities.size()-(p+1))*0.1;
                    if(priorities.get(p).equals("Eyesight")) {
                        score += priority_weight*(room.length-i);
                    }
                    else if(priorities.get(p).equals("Hearing")) {
                        score += priority_weight*(room.length-i);
                    }
                    else if(priorities.get(p).equals("Near")) {
                        Student student = students_id.getStudent(room[i][j]);
                        double total_distance = 0;
                        for(Student s: student.getNear()) {
                            int[] index = getLocation(s);
                            double distance = Math.sqrt(Math.pow(i-index[0], 2)+Math.pow(j-index[1], 2));
                            total_distance += distance;
                        }
                        double mean_distance = total_distance/student.getNear().size();
                        score -= priority_weight*mean_distance;
                    }
                    else if(priorities.get(p).equals("Avoid")) {
                        Student student = students_id.getStudent(room[i][j]);
                        double total_distance = 0;
                        for(Student s: student.getNear()) {
                            int[] index = getLocation(s);
                            double distance = Math.sqrt(Math.pow(i-index[0], 2)+Math.pow(j-index[1], 2));
                            total_distance += distance;
                        }
                        double mean_distance = total_distance/student.getNear().size();
                        score += priority_weight*mean_distance;
                    }
                }
                //System.out.println(score);
                total_score += score;            
            }
        }

        double mean_score = total_score/students.size();
        return mean_score;
    }

    public void sort2(double current_mean_score, int stopper) {
        System.out.println(stopper);
        if(stopper == 1000000) return;
        for(int i = 0; i < positions.length; i++) {
            for(int j = 0; j < positions.length; j++) {
                if(i == j) continue;
                int[][] temp_classroom = new int[14][14];
                for(int k = 0; k < 14; k++) {
                    for(int l = 0; l < 14; l++) {
                        temp_classroom[k][l] = classroom[k][l];
                    }
                }
                int one = classroom[positions[i][0]][positions[i][1]];
                int two = classroom[positions[j][0]][positions[j][1]];
                temp_classroom[positions[i][0]][positions[i][1]] = two;
                temp_classroom[positions[j][0]][positions[j][1]] = one;
                System.out.println(temp_classroom[0][0]+" "+temp_classroom[0][1]);
                System.out.println(classroom[0][0]+" "+classroom[0][1]);
                double new_score = mean_score(temp_classroom);
                System.out.println(new_score+" "+current_mean_score);
                if(new_score > current_mean_score) {
                    this.classroom = temp_classroom;
                    sort2(new_score, stopper+1);
                }
            }
        }
    }
}
