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
    private Professeur prof;
    private List<Etudiant> etudiants;
    private String className;
    protected Map<Etudiant, Set<Note>> mapNotes;

    public String getClassName() {
        return className;
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public Map<Etudiant, Set<Note>> getMapNotes() {
        return mapNotes;
    }

    public Professeur getProf() {
        return prof;
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

    public void setProf(Professeur prof) {
        this.prof = prof;
    }
    
    
    
    
}
