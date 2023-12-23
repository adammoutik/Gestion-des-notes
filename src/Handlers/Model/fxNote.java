package Handlers.Model;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;


public class fxNote {
    private SimpleStringProperty status;
    private SimpleStringProperty type;
    private SimpleDoubleProperty note;
    private SimpleStringProperty nom;
    private SimpleStringProperty prenom;

    public fxNote(String status, String type, float note, String nom, String prenom) {
        this.status = new SimpleStringProperty(status);
        this.type = new SimpleStringProperty(type);
        this.note = new SimpleDoubleProperty(note);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
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

}

