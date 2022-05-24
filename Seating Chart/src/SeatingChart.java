import java.util.Random;
import java.util.ArrayList;

public class SeatingChart {

    private int[][] classroom; // every spot without desk is 0, every spot with desk is 1
    private int[][] current_state; // used as the current evolutionary marker for the simulated annealing algorithm
    private ArrayList<String> priorities; // has priorities in order, most prioritized at the front
    private ArrayList<Student> students; // every student
    private Course students_id; // gets Student object based on student id

    // contructor
    // calls each of the sorting algorithms
    public SeatingChart(int[][] classroom, Course students, ArrayList<String> priorities, ArrayList<Student> TEMPORARY) {
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
        this.current_state = new int[14][14];
        for(int i = 0; i < classroom.length; i++) {
            for(int j = 0; j < classroom[i].length; j++) {
                if(this.classroom[i][j] == 1) {
                    this.classroom[i][j] = this.students.get(iterator).getId();
                    this.current_state[i][j] = this.classroom[i][j];
                    iterator++;
                }
            }
        }

        double curr_score = mean_score(this.classroom);

        
        for(int[] i: classroom) {
            for(int j: i) {
                System.out.print(j+" ");
            }
            System.out.println();
        }
        sort2(curr_score);
        for(int[] i: this.classroom) {
            for(int j: i) {
                System.out.print(j+" ");
            }
            System.out.println();
        }
    }

    // returns sorted classroom after SeatingChart object declared
    public int[][] getClassroom() {
        return classroom;
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
    
    // finds the location of a student in a specified classroom array based on their id
    public int[] getLocation(Student s, int[][] current_room) {
        for(int i = 0; i < current_room.length; i++) {
            for(int j = 0; j < current_room[i].length; j++) {
                if(current_room[i][j] == s.getId()) return new int[]{i, j};
            }
        }
        return new int[]{0, 0};
    }

    // calculates a "score" for a seating arrangment based on all of the student attributes + the prioritization of the attributes
    //our simulated annealing algorithm's goal is to minimize the score of this score, by changing the classroom array in certain ways
    public double mean_score(int[][] room) {
        double total_score = 0; 
        int conditioned_students = 0;
        for(int i = 0; i < room.length; i++) {
            for(int j = 0; j < room[i].length; j++) {
                if(room[i][j] == 0) continue;
                double score = 0;
                boolean hasCondition = false;
                for(int p = 0; p < priorities.size(); p++) {
                    double priority_weight = Math.abs(priorities.size()-p)*10;
                    if(priorities.get(p).equals("Eyesight") && !students_id.getStudent(room[i][j]).getEyesight()) {
                        score += priority_weight*(room.length-i);
                        hasCondition = true;
                    }
                    else if(priorities.get(p).equals("Hearing") && !students_id.getStudent(room[i][j]).getHearing()) {
                        score += priority_weight*(room.length-i);
                        hasCondition = true;
                    }
                    else if(priorities.get(p).equals("Near") && students_id.getStudent(room[i][j]).getNear().size() != 0) {
                        Student student = students_id.getStudent(room[i][j]);
                        double total_distance = 0;
                        for(Student s: student.getNear()) {
                            int[] index = getLocation(s, room);
                            double distance = Math.sqrt(Math.pow(i-index[0], 2)+Math.pow(j-index[1], 2));
                            total_distance += distance;
                        }
                        double mean_distance = total_distance/student.getNear().size();
                        score -= priority_weight*mean_distance;
                        hasCondition = true;
                    }
                    else if(priorities.get(p).equals("Avoid") && students_id.getStudent(room[i][j]).getAvoid().size() != 0) {
                        Student student = students_id.getStudent(room[i][j]);
                        double total_distance = 0;
                        for(Student s: student.getAvoid()) {
                            int[] index = getLocation(s, room);
                            double distance = Math.sqrt(Math.pow(i-index[0], 2)+Math.pow(j-index[1], 2));
                            total_distance += distance;
                        }
                        double mean_distance = total_distance/student.getAvoid().size();
                        score += priority_weight*mean_distance;
                        hasCondition = true;
                    }
                }
                if(hasCondition) {
                    conditioned_students++;
                    total_score += score;
                }
            }
        }
        double mean_score = total_score/conditioned_students;
        return mean_score;
    }

    // gets random neighbor of current classroom
    // "neighbor" -> current state with any two people swapped
    // used in simulated annealing algorithm
    public int[][] getNeighbor() {
        Random random = new Random();
        int i1 = random.nextInt(14);
        int i2 = random.nextInt(14);
        int j1 = random.nextInt(14);
        int j2 = random.nextInt(14);
        while(current_state[i1][i2] == 0 || current_state[j1][j2] == 0 || (i1 == j1 && i2 == j2)) {
            i1 = random.nextInt(14);
            i2 = random.nextInt(14);
            j1 = random.nextInt(14);
            j2 = random.nextInt(14);            
        }
        int[][] new_room = new int[14][14];
        for(int i = 0; i < 14; i++) {
            for(int j = 0; j < 14; j++) {
                new_room[i][j] = current_state[i][j];
            }
        }

        new_room[i1][i2] = current_state[j1][j2];
        new_room[j1][j2] = current_state[i1][i2];

        return new_room;
    }

    // Acceptance Probability Function of the Metropolis-Hastings Algorithm
    // in simulated annealing we dont want to only change to a new state if the state has a better score
    // we want the global maximum score of the classroom, rather than the local maximum score
    // because of this, sometimes, we randomly change our current_state to a worse state
    // however, we keep note of the best classroom, throughout the process
    // we automatically return 1.0, if the neighbor score is greater
    public double P(double state_score, double neighbor_score, double temp) {
        if(neighbor_score >= state_score) return 1.0;
        return Math.exp((neighbor_score-state_score)/temp);
    }

    // 2nd sorting algorithm which refines the seating chart made by the greedy algorithm
    // implementation of Simulated Annealing algorithm -> essentially "evolves" our classroom
    // time -> how many iterations so far
    // limit -> the time we choose where we stop evolving, I chose 10^6 because it was large enough such that the evolution was good but small enough such that the program didn't take too long
    // current_state -> the array that we evolve throughout the process
    // current_state_score -> the score of the current_state
    // classroom -> our best classroom
    // current_best_score -> score of best classroom
    // temperature -> a means of controlling randomness of evolution
    // at the beginning, we want lots of variation in our evolutions, so that we can get past the local maximum mentioned earlier
    // near the end, we want to converge onto a specific classroom, so the randomness goes down 
    // temperature controls how random our moes are
    // it is used in the probability P function, and I specifically chose 0.99998 because I found it had the best transition from random to not random 
    public void sort2(double current_best_score) {
        int time = 0;
        int limit = 1000000;
        double current_state_score = current_best_score;
        double temperature = 100000000;
        while(time < limit) {
            int[][] next = getNeighbor();
            double next_score = mean_score(next);
            if(current_state_score > current_best_score) {
                current_best_score = current_state_score;
                for(int i = 0; i < next.length; i++) {
                    for(int j = 0; j < next[i].length; j++) {
                        classroom[i][j] = next[i][j];
                    }
                }
            }
            mean_score(classroom);

            if(P(current_state_score, next_score, temperature) >= Math.random()) {
                for(int i = 0; i < next.length; i++) {
                    for(int j = 0; j < next[i].length; j++) {
                        current_state[i][j] = next[i][j];
                    }
                }                
                current_state_score = next_score;
            }
            temperature *= 0.99998;
            time++;
        }
    }
}