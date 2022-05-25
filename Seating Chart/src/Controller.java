
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Set;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;


public class Controller implements Initializable{

    //@FXML
    //private Label label;
    @FXML
    private ListView<Student> listView;
    @FXML
    private Label studentLabel;
    @FXML
    private Button studentChosen;
    @FXML
    private CheckBox visibility;
    @FXML
    private CheckBox hearing;
    @FXML
    private ComboBox<Student> avoid;
    @FXML
    private ComboBox<Student> near;
    @FXML
    private ListView<Student> avoidList;
    @FXML
    private ListView<Student> nearList;
    @FXML
    private GridPane classroomGrid;
    @FXML
    private Button sort;


    private Course c;
    private Student student;

    private int[][] classroom;
    private ArrayList<Student> studentList;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1){

        studentList = new ArrayList<Student>();
        //initialize classroom
        classroom = new int[14][14];


        //initialie course
        File file = new File("G:/My Drive/IntroCS Workspace/Seating-Chart/Seating Chart/src//roster.txt");
        try {
            c = new Course(file);
        } catch (FileNotFoundException e) { 
            System.out.println("well damn");
        }

        //display all students to pick from
        Hashtable<Integer, Student> students = c.getStudentDict();
        Set<Integer> setOfKeys = students.keySet();
        for (Integer x : setOfKeys) {
            studentList.add(students.get(x));
            listView.getItems().addAll(students.get(x));
            near.getItems().add(students.get(x));
            avoid.getItems().add(students.get(x));        }
        
        //code for what to do when student selected from list
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {

            @Override
            public void changed(ObservableValue<? extends Student> arg0, Student arg1, Student arg2) {
                nearList.getItems().clear();
                avoidList.getItems().clear();
                student = listView.getSelectionModel().getSelectedItem();
                studentLabel.setText(student.toString());
                visibility.setSelected(student.getEyesight());
                hearing.setSelected(student.getHearing());
                for (Student s: student.getNear()){
                    if (s != null) nearList.getItems().add(s);
                }
                for (Student s: student.getAvoid()){
                    if (s != null) avoidList.getItems().add(s);
                } 
            }
            
        });
        //code for choosing students preferred to be seated near too
        near.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {

            @Override
            public void changed(ObservableValue<? extends Student> arg0, Student arg1, Student arg2) {
                student.setNear(near.getSelectionModel().getSelectedItem()); 
                nearList.getItems().add(near.getSelectionModel().getSelectedItem());
                near.getSelectionModel().clearSelection();               
            }
            
        });

        //code for choosing students preferred to NOT be seated near too
        avoid.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {

            @Override
            public void changed(ObservableValue<? extends Student> arg0, Student arg1, Student arg2) {
                student.setAvoid(avoid.getSelectionModel().getSelectedItem()); 
                avoidList.getItems().add(avoid.getSelectionModel().getSelectedItem());
                avoid.getSelectionModel().clearSelection();               
            }
            
        });
        //set the 196 buttons or empty spaces to be set as desk or not
        for (int row = 0; row < 14; row++) {
            for (int col = 0; col < 14; col++) {
                Button seat = new Button();
                seat.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                seat.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent arg0) {
                        int row = GridPane.getRowIndex(seat);
                        int col = GridPane.getColumnIndex(seat);
                        if (classroom[row][col] == 0) {
                            seat.setText("X");
                            classroom[row][col] = 1; }
                        else if (classroom[row][col] == 1) {
                            classroom[row][col] = 0;
                            seat.setText("");
                        }
                    }
                    
                });               
                classroomGrid.add(seat, row, col);

            }
        }
       
        }
           
    //when visibility disability checkbox is selected...
    public void visibility () {
        student.setSight(visibility.isSelected());
    }

    //when hearing disability checkbox is selected...
    public void hearing () {
        student.setHearing(hearing.isSelected());
    }

    //code for final button sort. 
    public void sort () {
        ArrayList<String> priorities = new ArrayList<String>();
        priorities.add("Eyesight");
        priorities.add("Hearing");
        priorities.add("Near");
        priorities.add("Avoid");
        SeatingChart chart = new SeatingChart(classroom, c, priorities, studentList);
        for (int row = 0; row < classroom.length-1; row++) {
            for (int col = 0; col < classroom[row].length-1; col++) {
                System.out.println(classroom[row][col]);
            }
        }
        
    }

}

    /*public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");
    }*/
