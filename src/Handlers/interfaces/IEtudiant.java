package Handlers.interfaces;

import Handlers.Model.Etudiant;
import Handlers.Model.Note;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

public interface IEtudiant {
    public Etudiant findEtudiant(int eId);
    public void insertEtudiant(Etudiant etudiant);
    public void updateEtudiant(Etudiant etudiant);
    public void deleteEtudiant(int eId);
    public List<Etudiant> findAllEtudiants();
    public List<Etudiant> findEtudiantByClassId(int id);
    public int getEtudiantIdByUsername(String username);
}
