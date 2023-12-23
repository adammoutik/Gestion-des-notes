/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Handlers.Model;

import Handlers.implementaion.EtudiantImpl;

import java.util.*;

/**
 *
 * @author pc
 */
public class Etudiant extends User {
    private int eId;
    private int classId;
    private int user_id;
    private Set<Note> notes;

    public Etudiant(int eId, int classId,int user_id, int id, String username, String password, String role, String first_name, String last_name) {
        super(id, first_name, last_name, username, password, role);
        this.eId = eId;
        this.user_id = user_id;
        this.classId = classId;
        this.notes = new HashSet<>();
    }

    public Etudiant(int classId, int user_id){
        super();
        this.eId = -1;
        this.classId = classId;
        this.user_id = user_id;
        this.notes = new HashSet<>();

    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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


    public void setClassId(int classId) {
        this.classId = classId;
    }

    public void setNotes(Set<Note> notes) {
        this.notes = notes;
    }

    public void seteId(int eId) {
        this.eId = eId;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "eId=" + eId +
                ", classId=" + classId +
                ", user_id=" + user_id +
                ", notes=" + notes +
                "} " + super.toString();
    }





}
