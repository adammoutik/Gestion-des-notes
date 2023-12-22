package Handlers.implementaion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ProfesseurImpl {
    private String jdbcURL = "jdbc:mysql://localhost:3306/GestionNotes";
    private String jdbcUsername = "adamos";
    private String jdbcPassword = "password";

    private static final String SELECT_ETUDIANT_BY_ID = "SELECT etudiant_id, class_id, user_id FROM Etudiant WHERE etudiant_id = ?";
    private static final String SELECT_NOTES_BY_ETUDIANT_ID = "SELECT * FROM Note WHERE etudiant_id = ?";
    private static final String INSERT_ETUDIANT_SQL = "INSERT INTO Etudiant (etudiant_id, class_id, user_id) VALUES (?, ?, ?)";
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

    public int getProfIdByClassId(int classId){

    }
}
