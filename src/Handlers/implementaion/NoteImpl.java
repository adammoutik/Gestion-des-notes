package Handlers.implementaion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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
    private static final String SELECT_ALL_USERS = "select * from Note";
    private static final String DELETE_USER_SQL = "delete from Note where note_id = ?;";
    private static final String UPDATE_USER_SQL = "update Note set type = ?, etudiant_id= ?, type=?, note=?, status=? where note_id = ?;";

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
            preparedStatement.setInt(2, note.getEtudiant().geteId());
            preparedStatement.setString(3, note.getType());
            preparedStatement.setFloat(4, note.getNote());
            preparedStatement.setString(5, note.getStatus());
            preparedStatement.setInt(6, note.getClass_id());
            preparedStatement.executeUpdate(); // ...?
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteNote(int id) {

    }

    @Override
    public void updateNote(Note note) {

    }

    @Override
    public Note findNote(int id) {
        return null;
    }

    @Override
    public List<Note> findAllNotes() {
        return null;
    }
}
