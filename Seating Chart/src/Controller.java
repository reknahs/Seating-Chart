
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.text.ComponentView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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



    private Class c;
    private Student student;

    private Stage stage;
    private Scene scene;
    private Parent root;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        File file = new File("G:/My Drive/IntroCS Workspace/Seating-Chart/Seating Chart/src//roster.txt");
        try {
            c = new Class(file);
        } catch (FileNotFoundException e) { 
            System.out.println("well damn");
        }
        Hashtable<Integer, Student> students = c.getStudentDict();
        Set<Integer> setOfKeys = students.keySet();
        for (Integer x : setOfKeys) {
            listView.getItems().addAll(students.get(x));
            near.getItems().add(students.get(x));
            avoid.getItems().add(students.get(x));        }

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {

            @Override
            public void changed(ObservableValue<? extends Student> arg0, Student arg1, Student arg2) {
                student = listView.getSelectionModel().getSelectedItem();
                studentLabel.setText(student.toString());
                visibility.setSelected(student.getEyesight());
                hearing.setSelected(student.getHearing());
            }
            
        });

        }            
    
    public void visibility () {
        student.setSight(visibility.isSelected());
    }

    public void hearing () {
        student.setHearing(hearing.isSelected());
    }
}

    /*public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");
    }*/
