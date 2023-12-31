package sample;

import Handlers.Model.Class;
import Handlers.Model.Etudiant;
import Handlers.Model.Note;
import Handlers.Model.fxNote;
import Handlers.implementaion.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.events.MouseEvent;

import java.sql.*;
import java.util.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfController implements Initializable {

    @FXML
    private ChoiceBox<String> EtudiantList;

    @FXML
    private Button addStudent;

    @FXML
    private TextField inNote;

    @FXML
    private TextField inType;

    @FXML
    private TableColumn<fxNote, String> nomCol;

    @FXML
    private TableColumn<fxNote, Double> noteCol;

    @FXML
    private TableColumn<fxNote, Integer> noteIdCol;

    @FXML
    private TableColumn<fxNote, String> prenomCol;

    @FXML
    private Button removeStudent;

    @FXML
    private TableColumn<fxNote, String> statusCol;

    @FXML
    private ChoiceBox<String> statusSelect;

    @FXML
    private TableView<fxNote> studentsTable;

    @FXML
    private TableColumn<fxNote, String> typeCol;

    @FXML
    private Button updateButton;

    @FXML
    private Label className;

    @FXML
    private TextField hiddenNoteId;

    @FXML
    private Button button_logout;

    private ObservableList<fxNote> data;

    int index = -1;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] items = {"Validé", "Non validé", "Rattrapage"};
        statusSelect.getItems().addAll(items);


        button_logout.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "sample.fxml", "Log In!", null, -1);
            }
        });


    }

    public void getProfInformations(int pf_id, int clsId) {
        // fill the choicebox with students
        int cs = new ClassImpl().getClassIdByProfId(pf_id);
        List<Etudiant> etd = new EtudiantImpl().findEtudiantByClassId(cs);
        if (!etd.isEmpty())for (Etudiant e : etd) {
            System.out.println(e.toString());
            EtudiantList.getItems().add(e.geteId() + " " + e.getFirst_name() + " " + e.getLast_name());
        }
        else {
            System.out.println("There is no students in this class");
        }
        className.setText(new ClassImpl().findClass(clsId).getClassName());
        String clsName = className.getText();
        Class targetClass = new ClassImpl().findClassbyName(clsName);
        List<Etudiant> targetEtd = targetClass.getEtudiants();
        List<fxNote> list = new ArrayList<>();
        for(Etudiant etds : targetEtd){
            if(etds.getNotes() != null){
                for(Note nt : etds.getNotes()){
                    list.add(new fxNote(nt.getEtudiant_id(), nt.getStatus(), nt.getType(), nt.getNote(), etds.getLast_name(),etds.getFirst_name(), nt.getNote_id()));
                }
            }
        }
        // initialize the table with data
        ObservableList<fxNote> data = FXCollections.observableList(list);
        setupTableColumns();
        studentsTable.setItems(data);

    }

    private void setupTableColumns() {
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        noteCol.setCellValueFactory(new PropertyValueFactory<fxNote, Double>("note"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        noteIdCol.setCellValueFactory(new PropertyValueFactory<fxNote, Integer>("note_id"));
    }


    private void refresh(){
        String clsName = className.getText();
        System.out.println(clsName);
        Class targetClass = new ClassImpl().findClassbyName(clsName);
        List<Etudiant> targetEtd = targetClass.getEtudiants();
        List<fxNote> list = new ArrayList<>();
        for(Etudiant etds : targetEtd){
            if(etds.getNotes() != null){
                for(Note nt : etds.getNotes()){
                    list.add(new fxNote(nt.getEtudiant_id(), nt.getStatus(), nt.getType(), nt.getNote(), etds.getLast_name() ,etds.getFirst_name(), nt.getNote_id()));
                }
            }

        }
        ObservableList<fxNote> data = FXCollections.observableList(list);
        setupTableColumns();
        studentsTable.setItems(data);
    }



    // Grade Insertion
    public void addNote(){
        try{
            if(EtudiantList.getValue() != null && inNote.getText()!=null && inType.getText()!=null && statusSelect.getValue() != null){
                String[] res = EtudiantList.getValue().split(" ");
                Etudiant et = new EtudiantImpl().findEtudiant(Integer.parseInt(res[0]));
                Note nt = new Note(et.geteId(), Double.parseDouble(inNote.getText()),inType.getText(), statusSelect.getValue(),new ClassImpl().findClassbyName(className.getText()).getClass_id());
                new NoteImpl().insertNote(nt);
                refresh();
                clear();
            }
        }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid grade.");
            alert.show();
        }

    }


    //clear inputs
    public void clear(){
        EtudiantList.setValue(null);
        inNote.setText("");
        inType.setText("");
        statusSelect.setValue("");
    }

    // add data to inputs when i click a row
    public void addToTable(javafx.scene.input.MouseEvent mouseEvent) {
        index = studentsTable.getSelectionModel().getSelectedIndex();

        if (index == -1) return;

        fxNote selectedNote = studentsTable.getItems().get(index);
        int selectedId = selectedNote.getId();
        for (String item : EtudiantList.getItems()) {
            // EtudiantList items are in the format "ETUDIANT_ID + " " + ETUDIANT_FIRSTNAME + " " + ETUDIANT_LASTNAME"
            String[] parts = item.split(" ");
            if (parts.length >= 2) {
                int etudiantId = Integer.parseInt(parts[0]); // ID is at index 0
                String firstName = parts[1];    //  First Name is at index 1
                String lastName = parts[2];     // Last Name is at index 2
                if (selectedId == etudiantId &&
                        selectedNote.getPrenom().equals(firstName) &&
                        selectedNote.getNom().equals(lastName)) {
                    EtudiantList.getSelectionModel().select(item);
                    break; // stop looping once the item is selected
                }
            }
        }
        inType.setText(selectedNote.getType());
        inNote.setText(String.valueOf(selectedNote.getNote()));
        statusSelect.setValue(selectedNote.getStatus());
        hiddenNoteId.setText(String.valueOf(selectedNote.getNote_id()));
    }


    public void updateStudent(ActionEvent event) {



        try{
            if(EtudiantList.getValue() != null && inNote.getText()!=null && inType.getText()!=null && statusSelect.getValue() != null){

                String[] res = EtudiantList.getValue().split(" ");
                NoteImpl noteImpl = new NoteImpl();
                Etudiant et = new EtudiantImpl().findEtudiant(Integer.parseInt(res[0]));
                Note nt = noteImpl.findNote(Integer.parseInt(hiddenNoteId.getText()));
                nt.setEtudiant_id(Integer.parseInt(res[0]));
                nt.setNote(Double.parseDouble(inNote.getText()));
                nt.setType(inType.getText());
                nt.setStatus(statusSelect.getValue());
                new NoteImpl().updateNote(nt);
                refresh();
                clear();
            }
        }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid grade.");
            alert.show();
        }

    }

    public void deleteStudent(ActionEvent event){
        if(EtudiantList.getValue() != null && inNote.getText()!=null && inType.getText()!=null && statusSelect.getValue() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this item ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                NoteImpl noteImpl = new NoteImpl();
                Note nt = noteImpl.findNote(Integer.parseInt(hiddenNoteId.getText()));
                noteImpl.deleteNote(nt.getNote_id());
                refresh();
                System.out.println("Item deleted.");
            } else {
                System.out.println("Deletion canceled.");
            }
            clear();

        }
    }
}
