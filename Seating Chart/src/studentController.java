
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class studentController implements Initializable{

    //@FXML
    //private Label label;
    @FXML
    private ListView<String> listView;
    private Class c;



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        /*File file = new File("roster.txt");
        try {
            c = new Class(file);
        } catch (FileNotFoundException e) { }
        Hashtable<Integer, Student> students = c.getStudentDict();
        Set<Integer> setOfKeys = students.keySet();
        String[] studentListElements = new String[students.size()];
        int i = 0;
        for (Integer x : setOfKeys) {
            studentListElements[i] = students.get(x).toString();
            i++;
        }*/
        listView.getItems().addAll("aashna","nitya", "anvita", "khadija"); 


           
    }

    /*public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + ".");
    }*/
}