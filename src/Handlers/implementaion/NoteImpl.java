package Handlers.implementaion;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Handlers.Model.Etudiant;
import Handlers.Model.Note;
import Handlers.interfaces.INote;

public class NoteImpl implements INote {
    private String jdbcURL = "jdbc:mysql://localhost:3306/GestionNotes";
    private String jdbcUsername = "adamos";
    private String jdbcPassword = "password";

    private static final String INSERT_NOTE_SQL = "INSERT INTO Note" +
            "  (note_id, etudiant_id, type, note, status, class_id) VALUES " +
            " (?, ?, ?, ?, ?, ?);";

    private static final String SELECT_NOTE_BY_ID = "select etudiant_id, type, note, status, class_id from Note where note_id =?";
    private static final String SELECT_ALL_NOTES = "select * from Note";
    private static final String DELETE_NOTE_SQL = "delete from Note where note_id = ?;";
    private static final String UPDATE_NOTE_SQL = "update Note set type = ?, etudiant_id= ?, type=?, note=?, status=? where note_id = ?;";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void insertNote(Note note) {
        //(note_id, etudiant_id, type, note, status, class_id)
        try {
            Connection conn = getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_NOTE_SQL);
            preparedStatement.setInt(1, note.getNote_id());
            preparedStatement.setInt(2, note.getEtudiant_id());
            preparedStatement.setString(3, note.getType());
            preparedStatement.setFloat(4, note.getNote());
            preparedStatement.setString(5, note.getStatus());
            preparedStatement.setInt(6, note.getClass_id());
            preparedStatement.executeUpdate(); // ...?
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteNote(int id) {
        try {
            Connection con = getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(DELETE_NOTE_SQL);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateNote(Note note) {
        try {          // type = ?, etudiant_id= ?, type=?, note=?, status=? where note_id = ?
            Connection con = getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(UPDATE_NOTE_SQL);
            preparedStatement.setString(1, note.getType());
            preparedStatement.setInt(2, note.getEtudiant_id());
            preparedStatement.setString(3, note.getType());
            preparedStatement.setFloat(4, note.getNote());
            preparedStatement.setString(5, note.getStatus());
            preparedStatement.setInt(6, note.getNote_id());
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Note findNote(int id) {
        //private static final String SELECT_NOTE_BY_ID = "select etudiant_id, type, note, status, class_id from Note where note_id =?";

        Note notee = null;
        try {
            Connection con = getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(SELECT_NOTE_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            notee = null;
            while (resultSet.next()) {
                int etId = resultSet.getInt("etudiant_id");
                String type = resultSet.getString("type");
                float note = resultSet.getFloat("note");
                String status = resultSet.getString("status");
                int class_id = resultSet.getInt("class_id");
                notee = new Note(id, etId, note, type, status, class_id);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notee;
    }

    @Override
    public List<Note> findAllNotes() {
        List<Note> notes = new ArrayList<>();
        String SELECT_ALL_NOTES = "SELECT note_id, etudiant_id, type, note, status, class_id FROM Note";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_NOTES)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int noteId = rs.getInt("note_id");
                int etudiantId = rs.getInt("etudiant_id");
                String type = rs.getString("type");
                float noteContent = rs.getFloat("note");
                String status = rs.getString("status");
                int classId = rs.getInt("class_id");

                EtudiantImpl e = new EtudiantImpl();

                Note note = new Note();
                note.setNote_id(noteId);
                note.setEtudiant_id(etudiantId);
                note.setType(type);
                note.setNote(noteContent);
                note.setStatus(status);
                note.setClass_id(classId);

                notes.add(note);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }

//    public List<Note> getNoteByEtudiantId(int id){
//        List<Note> notes = findAllNotes();
//        List<Note> res = new ArrayList<>();
//
//        for(Note note : notes){
//            if(note.getEtudiant_id() == id){
//                res.add(note);
//            }
//        }
//
//        return res;
//    }

    public static void main(String[] args) {
        NoteImpl noteImpl = new NoteImpl();

        // Create a new note
        Note noteToSave = new Note(1, 2, 18.5f,"Exam", "Validé", 2);
        noteImpl.insertNote(noteToSave);

        // Retrieve the created note
        Note retrievedNote = noteImpl.findNote(1);
        System.out.println(retrievedNote.toString());
    }


}



