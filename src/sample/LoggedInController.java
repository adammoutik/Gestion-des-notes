package sample;

import Handlers.Model.Class;
import Handlers.Model.Etudiant;
import Handlers.Model.Note;
import Handlers.Model.fxNote;
import Handlers.implementaion.ClassImpl;
import Handlers.implementaion.EtudiantImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable { //Interface used for controller initialization, initialize is called after all @FXML annotated mamebers have been injected, you can work with your components

    @FXML
    private Button button_logout;

    @FXML
    private Label label_fav_car;

    @FXML
    private Label label_welcome;

    @FXML
    private AnchorPane logout;

    @FXML
    private TableColumn<fxNote, Double> noteCol;

    @FXML
    private TableView<fxNote> notesTable;

    @FXML
    private TableColumn<fxNote, String> statusCol;

    @FXML
    private TableColumn<fxNote, String> titleCol;

    @FXML
    private TableColumn<fxNote, String> typeCol;







    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        button_logout.setOnAction(event -> DBUtils.changeScene(event, "sample.fxml", "Log In!", null, -1));

    }

    public void setUserInformation(String username, int note_id) {
        label_welcome.setText("Welcome " + username);
        label_fav_car.setText(String.valueOf(note_id)); // etudiant id
        refresh();
    }

    private void refresh() {
        int etudiant_id = Integer.parseInt(label_fav_car.getText());
        Etudiant etd = new EtudiantImpl().findEtudiant(etudiant_id);
        List<fxNote> notes = new ArrayList<>();
        for (Note nt : etd.getNotes()) {
            notes.add(new fxNote(nt.getNote_id(), nt.getStatus(), nt.getType(), nt.getNote(), new ClassImpl().findClass(etd.getClassId()).getClassName()));
        }
        ObservableList<fxNote> data = FXCollections.observableList(notes);
        notesTable.setItems(data);
    }

    private void setupTableColumns() {
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("className"));

    }

}
