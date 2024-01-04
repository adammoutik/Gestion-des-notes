package Handlers.implementaion;

import Handlers.Model.Class;
import Handlers.Model.Professeur;
import Handlers.Model.User;
import Handlers.interfaces.IProfesseur;

import java.sql.*;

public class ProfesseurImpl implements IProfesseur {
    private String jdbcURL = "jdbc:mysql://localhost:3306/GestionNotes";
    private String jdbcUsername = "adamos";
    private String jdbcPassword = "password";


    private static final String SELECT_PROF_BY_CLASS_ID = "SELECT * FROM Professeur WHERE pf_id = ?";


    protected Connection getConnection() {
        Connection connection = null;
        try {
            java.lang.Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }



    public int getProfIdByUsername(String username) {
        try {
            Connection con = getConnection();
            PreparedStatement preparedStatement = con.prepareStatement("SELECT pf_id FROM Professeur WHERE user_id = ?");
            preparedStatement.setInt(1, new UserImpl().findUserByUsername(username));
            ResultSet res = preparedStatement.executeQuery();
            if (res.next()) {
                return res.getInt("pf_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }

    public Professeur insertProfessor(int user_id, String class_name) throws SQLException {
        Connection connection = getConnection();
        String insertProfesseurQuery = "INSERT INTO Professeur (user_id) VALUES (?)";
        try (PreparedStatement psInsertProf = connection.prepareStatement(insertProfesseurQuery, Statement.RETURN_GENERATED_KEYS)) {
            psInsertProf.setInt(1, user_id);
            int affectedRows = psInsertProf.executeUpdate();
            if (affectedRows > 0) {
                ResultSet genKeys = psInsertProf.getGeneratedKeys();
                if (genKeys.next()) {
                    int pf_id = genKeys.getInt(1);
                    //UPDATE CLASS OWNERSHIP
                    ClassImpl los = new ClassImpl();
                    Class cl = los.findClassbyName(class_name);
                    cl.setPf_id(pf_id);
                    los.updateClass(cl);
                    Professeur prof = new Professeur(user_id, cl);
                    System.out.println("Professeur inserted successfully.");
                    return prof;
                }
            }
        }
        return null;
    }


}
