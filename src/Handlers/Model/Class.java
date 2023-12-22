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
public class Class {
    private int class_id;
    private int pf_id;
    private List<Etudiant> etudiants;
    private String className;
    protected Map<Etudiant, Set<Note>> mapNotes;

    public Class(int classId, int prof, List<Etudiant> etudiants, String className) {
        this.class_id = classId;
        this.pf_id = prof;
        this.etudiants = etudiants;
        this.className = className;
        this.mapNotes = new HashMap<>();
    }

    public void setPf_id(int pf_id) {
        this.pf_id = pf_id;
    }

    public int getPf_id() {
        return pf_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public String getClassName() {
        return className;
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public Map<Etudiant, Set<Note>> getMapNotes() {
        return mapNotes;
    }



    public void setClassName(String className) {
        this.className = className;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public void setMapNotes(Map<Etudiant, Set<Note>> mapNotes) {
        this.mapNotes = mapNotes;
    }


    
    
    
    
}
