package sample;

import Handlers.Model.Etudiant;
import Handlers.implementaion.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfController {

    @FXML
    private ChoiceBox<String> EtudiantList;

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



    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void getProfInformations(int pf_id){
        List<Etudiant> etd = new EtudiantImpl().findEtudiantByClassId(new ClassImpl().getClassIdByProfId(pf_id));
        if(!etd.isEmpty()){
            for(Etudiant e : etd){
                System.out.println(e.toString());
                EtudiantList.getItems().add(e.getFirst_name() + " " + e.getLast_name());
            }
        }
        EtudiantList.getItems().addAll();
    }

}
