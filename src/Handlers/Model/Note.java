/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handlers.Model;

/**
 *
 * @author pc
 */
public class Note {
    private int note_id;
    private Etudiant etudiant;    // etudiant_id in the database
    private float note;
    private String type;
    private String status;
    private int class_id;

    Note(int note_id, Etudiant e, float n, String t, String s, int id){
        this.note_id = note_id;
        this.etudiant = e;
        this.note = n;
        this.type = t;
        this.status = s;
        this.class_id = id;
    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public float getNote() {
        return note;
    }

    public int getClass_id() {
        return class_id;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }


}
