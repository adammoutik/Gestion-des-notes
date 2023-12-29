package Handlers.Model;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class fxNote {
    private SimpleIntegerProperty id;
    private SimpleStringProperty status;
    private SimpleStringProperty type;
    private SimpleDoubleProperty note;
    private SimpleStringProperty nom;
    private SimpleStringProperty prenom;
    private SimpleIntegerProperty note_id;
    private SimpleStringProperty className;

    public fxNote(int id, String status, String type, double note, String nom, String prenom,int note_id) {
        this.id = new SimpleIntegerProperty(id);
        this.status = new SimpleStringProperty(status);
        this.type = new SimpleStringProperty(type);
        this.note = new SimpleDoubleProperty(note);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.note_id = new SimpleIntegerProperty(note_id);
    }

    public fxNote(int id, String status, String type, Double note, String className){
        this.id = new SimpleIntegerProperty(id);
        this.status = new SimpleStringProperty(status);
        this.type = new SimpleStringProperty(type);
        this.note = new SimpleDoubleProperty(note);
        this.className = new SimpleStringProperty(className);
    }

    // Getters and setters for each property


    public double getNote() {
        return note.get();
    }

    public SimpleDoubleProperty noteProperty() {
        return note;
    }

    public SimpleStringProperty nomProperty() {
        return nom;
    }

    public SimpleStringProperty prenomProperty() {
        return prenom;
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public String getNom() {
        return nom.get();
    }

    public String getPrenom() {
        return prenom.get();
    }

    public String getStatus() {
        return status.get();
    }

    public String getType() {
        return type.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public void setNote(double note) {
        this.note.set(note);
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setClassName(String className) {
        this.className.set(className);
    }

    public SimpleStringProperty classNameProperty() {
        return className;
    }

    public int getNote_id() {
        return note_id.get();
    }

    public String getClassName() {
        return className.get();
    }


}



