package sample;

import Handlers.Model.Etudiant;
import Handlers.implementaion.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfController {

    @FXML
    private ChoiceBox<?> EtudiantList;

    @FXML
    private Button addStudent;

    @FXML
    private TextField inFirstName;

    @FXML
    private TextField inLastName;

    @FXML
    private TextField inNote;

    @FXML
    private Button removeStudent;

    @FXML
    private TableView<?> studentsTable;

    @FXML
    private Button updateStudent;

    // we assume that we passed the prof_id after login
    protected int pf_id = 0;



    public void initialize(URL url, ResourceBundle resourceBundle) {

        EtudiantList.getItems().addAll();
    }

}
