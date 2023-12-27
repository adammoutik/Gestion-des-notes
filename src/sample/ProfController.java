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

    private ObservableList<fxNote> data;

    int index = -1;


    // we assume that we passed the prof_id after login


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] items = {"Validé", "Non validé", "Rattrapage"};
        statusSelect.getItems().addAll(items);


    }

    public void getProfInformations(int pf_id, int clsId) {
        int cs = new ClassImpl().getClassIdByProfId(pf_id);
        List<Etudiant> etd = new EtudiantImpl().findEtudiantByClassId(cs);
        if (!etd.isEmpty()) for (Etudiant e : etd) {
            System.out.println(e.toString());
            EtudiantList.getItems().add(e.geteId() + " " + e.getFirst_name() + " " + e.getLast_name());
        }
        else {
            System.out.println("There is no students in this class");
        }
        className.setText(new ClassImpl().findClass(clsId).getClassName());
        String clsName = className.getText();
        System.out.println(clsName);
        Class targetClass = new ClassImpl().findClassbyName(clsName);
        List<Etudiant> targetEtd = targetClass.getEtudiants();
        List<fxNote> list = new ArrayList<>();
        for(Etudiant etds : targetEtd){
            if(etds.getNotes() != null){
                for(Note nt : etds.getNotes()){
                    list.add(new fxNote(nt.getEtudiant_id(), nt.getStatus(), nt.getType(), nt.getNote(),etds.getLast_name(), etds.getLast_name(), nt.getNote_id()));
                }
            }

        }
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
                    list.add(new fxNote(nt.getEtudiant_id(), nt.getStatus(), nt.getType(), nt.getNote(),etds.getLast_name(), etds.getLast_name(), nt.getNote_id()));
                }
            }

        }
        ObservableList<fxNote> data = FXCollections.observableList(list);
        setupTableColumns();
        studentsTable.setItems(data);
    }


    private void getInitialTableData() {
        String clsName = className.getText();
        Class targetClass = new ClassImpl().findClassbyName(clsName);
        List<Etudiant> targetEtd = targetClass.getEtudiants();
        List<fxNote> list = new ArrayList<>();
        for(Etudiant etd : targetEtd){
            for(Note nt : etd.getNotes()){
                list.add(new fxNote(nt.getEtudiant_id(), nt.getStatus(), nt.getType(), nt.getNote(),etd.getLast_name(), etd.getLast_name(), nt.getNote_id()));
            }
        }



        ObservableList<fxNote> data = FXCollections.observableList(list);


        setupTableColumns();
        studentsTable.setItems(data);
    }



    public void addNote(){
        if(EtudiantList.getValue() != null && inNote.getText()!=null && inType.getText()!=null && statusSelect.getValue() != null){
            String[] res = EtudiantList.getValue().split(" ");
            Etudiant et = new EtudiantImpl().findEtudiant(Integer.parseInt(res[0]));
            Note nt = new Note(et.geteId(), Double.parseDouble(inNote.getText()),inType.getText(), statusSelect.getValue(),new ClassImpl().findClassbyName(className.getText()).getClass_id());
            new NoteImpl().insertNote(nt);
            refresh();
            clear();
        }
    }



    public void clear(){
        EtudiantList.setValue(null);
        inNote.setText("");
        inType.setText("");
        statusSelect.setValue("");
    }

    public void addToTable(javafx.scene.input.MouseEvent mouseEvent) {
        index = studentsTable.getSelectionModel().getSelectedIndex();

        if (index == -1) return;

        fxNote selectedNote = studentsTable.getItems().get(index);
        int selectedId = selectedNote.getId();
        for (String item : EtudiantList.getItems()) {
            // EtudiantList items are in the format "e.geteId() + " " + e.getFirst_name() + " " + e.getLast_name()"
            String[] parts = item.split(" ");
            if (parts.length >= 2) {
                int etudiantId = Integer.parseInt(parts[0]); // Assuming ID is at index 0
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
        hiddenNoteId.setText(String.valueOf(selectedNote.getId()));
    }


    public void updateStudent(ActionEvent event) {
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
    }
}
