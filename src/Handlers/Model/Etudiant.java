/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handlers.Model;

import java.util.*;

/**
 *
 * @author pc
 */
public class Etudiant extends User {
    private int eId;
    private String eName;
    private int classId;
    private Set<Note> notes;

    public Etudiant(int eId, String eName, int classId, int id, String username, String password, String role) {
        super(id, username, password, role);
        this.eId = eId;
        this.eName = eName;
        this.classId = classId;
        this.notes = new HashSet<>();
    }

    
    public int getClassId() {
        return classId;
    }

    public Set<Note> getNotes() {
        return notes;
    }

    public int geteId() {
        return eId;
    }

    public String geteName() {
        return eName;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public void seteId(int eId) {
        this.eId = eId;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }
    
    
}
