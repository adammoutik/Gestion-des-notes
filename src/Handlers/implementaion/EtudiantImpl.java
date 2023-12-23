package Handlers.implementaion;
import Handlers.Model.Etudiant;
import Handlers.Model.Note;
import Handlers.Model.User;

import java.sql.*;
import java.util.*;

public class EtudiantImpl {
    private String jdbcURL = "jdbc:mysql://localhost:3306/GestionNotes";
    private String jdbcUsername = "adamos";
    private String jdbcPassword = "password";

    private static final String SELECT_ETUDIANT_BY_ID = "SELECT etudiant_id, class_id, user_id FROM Etudiant WHERE etudiant_id = ?";
    private static final String SELECT_NOTES_BY_ETUDIANT_ID = "SELECT * FROM Note WHERE etudiant_id = ?";
    private static final String INSERT_ETUDIANT_SQL = "INSERT INTO Etudiant ( class_id, user_id) VALUES ( ?, ?)";
    private static final String UPDATE_ETUDIANT_SQL = "UPDATE Etudiant SET class_id = ?, user_id = ? WHERE etudiant_id = ?";
    private static final String DELETE_ETUDIANT_SQL = "DELETE FROM Etudiant WHERE etudiant_id = ?";


    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Etudiant findEtudiant(int eId) {
        Etudiant etudiant = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ETUDIANT_BY_ID)) {
            preparedStatement.setInt(1, eId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int etudiantId = rs.getInt("etudiant_id");
                int classId = rs.getInt("class_id");
                int userId = rs.getInt("user_id");
                UserImpl u = new UserImpl();
                User us = u.findUser(userId);
                String username = us.getUsername();
                String password = us.getPassword();
                String role = us.getRole();
                String first_name = us.getFirst_name();
                String last_name = us.getLast_name();

                //Etudiant(int etudiant_id, int classId, int id, String username, String password, String role, String first_name, String last_name)
                etudiant = new Etudiant(etudiantId, classId,userId, userId, username, password, role, first_name, last_name);
                Set<Note> notes = retrieveNotesForEtudiant(eId);
                etudiant.setNotes(notes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiant;
    }

    public void insertEtudiant(Etudiant etudiant) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ETUDIANT_SQL)) {
            preparedStatement.setInt(1, etudiant.getClassId());
            preparedStatement.setInt(2, etudiant.getUser_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEtudiant(Etudiant etudiant) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ETUDIANT_SQL)) {
            preparedStatement.setInt(1, etudiant.getClassId());
            preparedStatement.setInt(2, etudiant.geteId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEtudiant(int eId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ETUDIANT_SQL)) {
            preparedStatement.setInt(1, eId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected Set<Note> retrieveNotesForEtudiant(int eId) {
        Set<Note> notes = new HashSet<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NOTES_BY_ETUDIANT_ID)) {
            preparedStatement.setInt(1, eId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int noteId = rs.getInt("note_id");
                // Retrieve other note attributes from the ResultSet
                int etId = rs.getInt("etudiant_id");
                String type = rs.getString("type");
                float n = rs.getFloat("note");
                String status = rs.getString("status");
                int class_id = rs.getInt("class_id");
                Note note = new Note(noteId, etId, n,type ,status, class_id);
                notes.add(note);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

    public List<Etudiant> findAllEtudiants() {
        List<Etudiant> etudiants = new ArrayList<>();
        String SELECT_ALL_ETUDIANTS = "SELECT * FROM Etudiant";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ETUDIANTS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int etudiantId = rs.getInt("etudiant_id");
                int classId = rs.getInt("class_id");
                int userId = rs.getInt("user_id");

                // Fetch User details using userId
                UserImpl u = new UserImpl();
                User us = u.findUser(userId);
                String username = us.getUsername();
                String password = us.getPassword();
                String role = us.getRole();
                String first_name = us.getFirst_name();
                String last_name = us.getLast_name();

                // Fetch Notes for the Etudiant
                Set<Note> notes = retrieveNotesForEtudiant(etudiantId);

                // Create Etudiant object and add it to the list
                Etudiant etudiant = new Etudiant(etudiantId, classId, userId, userId, username, password, role, first_name, last_name);
                etudiant.setNotes(notes);
                etudiants.add(etudiant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etudiants;
    }

    public List<Etudiant> findEtudiantByClassId(int id) {
        List<Etudiant> result = new ArrayList<>();
        String query = "SELECT * FROM Etudiant WHERE class_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int etudiantId = rs.getInt("etudiant_id");
                int classId = rs.getInt("class_id");
                int userId = rs.getInt("user_id");
                UserImpl u = new UserImpl();
                User us = u.findUser(userId);
                String username = us.getUsername();
                String password = us.getPassword();
                String role = us.getRole();
                String first_name = us.getFirst_name();
                String last_name = us.getLast_name();

                // Fetch Notes for the Etudiant
                Set<Note> notes = retrieveNotesForEtudiant(etudiantId);

                // Create Etudiant object and add it to the list
                Etudiant etudiant = new Etudiant(etudiantId, classId, userId, userId, username, password, role, first_name, last_name);
                etudiant.setNotes(notes);
                result.add(etudiant);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) {
        // Assuming you have an instance of EtudiantImpl called etudiantImpl

        List<Etudiant> etd = new EtudiantImpl().findEtudiantByClassId(1);
        if (!etd.isEmpty()) {
            for (Etudiant e : etd) {
                System.out.println(e.toString());
            }

//        // Updating an Etudiant
//        // Assuming you have an Etudiant object with updated details
//        Etudiant updatedEtudiant = new Etudiant();
//        etudiantImpl.updateEtudiant(updatedEtudiant);

            // Deleting an Etudiant by ID
//        int etudiantIdToDelete = /* provide an existing etudiant_id */;
//        etudiantImpl.deleteEtudiant(etudiantIdToDelete);

            // Retrieving all notes for a specific Etudiant
            // Assuming you have an etudiantId for which you want to retrieve notes
//        Set<Note> notesForEtudiant = etudiantImpl.retrieveNotesForEtudiant(/* etudiantId */);
//        for (Note note : notesForEtudiant) {
//            System.out.println("Note: " + note);
//        }

        }
    }

}
