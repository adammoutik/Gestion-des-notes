package Handlers.implementaion;

import Handlers.Model.Class;
import Handlers.Model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassImpl {

    private String jdbcURL = "jdbc:mysql://localhost:3306/GestionNotes";
    private String jdbcUsername = "adamos";
    private String jdbcPassword = "password";

    private static final String INSERT_CLASS_SQL = "INSERT INTO Class" +
            "  (class_id, prof_id, className) VALUES " +
            " (?, ?, ?);";

    private static final String SELECT_CLASS_BY_ID = "select * from Class where class_id =?";
    private static final String SELECT_CLASS_BY_PROF_ID = "select * from Class where pf_id =?";
    private static final String SELECT_ALL_CLASSES = "select * from Class";
    private static final String DELETE_CLASS_SQL = "delete from Class where class_id = ?;";
    private static final String UPDATE_CLASS_SQL = "update Class set prof_id = ?, className = ? where class_id = ?;";

    /**
     *   getClassByProfId
     *
     *
     *
     * */
    protected Connection getConnection() {
        Connection connection = null;
        try {
            java.lang.Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }


    public void insertClass(Class clazz) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLASS_SQL)) {
            preparedStatement.setInt(1, clazz.getClass_id());
            preparedStatement.setInt(2, clazz.getPf_id()); // Assuming Professeur has an 'id' field
            preparedStatement.setString(3, clazz.getClassName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public Class findClass(int id) {
        Class clazz = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLASS_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int classId = rs.getInt("class_id");
                int profId = rs.getInt("pf_id");
                String className = rs.getString("className");


                List<Etudiant> etudiants = new EtudiantImpl().findEtudiantByClassId(classId);


                Map<Etudiant, Set<Note>> mapNotes = new HashMap<>();
                for(Etudiant et : etudiants){
                    mapNotes.put(et, et.getNotes());
                }



                clazz = new Class(classId, profId, etudiants, className);
                clazz.setMapNotes(mapNotes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clazz;
    }


    public List<Class> findAllClasses() {
        List<Class> classes = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLASSES)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int classId = rs.getInt("class_id");
                int profId = rs.getInt("pf_id");
                String className = rs.getString("className");

                List<Etudiant> etudiants = new EtudiantImpl().findEtudiantByClassId(classId);


                Map<Etudiant, Set<Note>> mapNotes = new HashMap<>();
                for(Etudiant et : etudiants){
                    mapNotes.put(et, et.getNotes());
                }

                Class clazz = new Class(classId, profId, etudiants, className);
                clazz.setMapNotes(mapNotes);
                classes.add(clazz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }


    public void updateClass(Class clazz) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CLASS_SQL)) {
            statement.setInt(1, clazz.getPf_id());
            statement.setString(2, clazz.getClassName());
            statement.setInt(3, clazz.getClass_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteClass(int id) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CLASS_SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Class findClassbyName(String name){
        List<Class> clss = new ClassImpl().findAllClasses();

        for(Class cl : clss){
            if(cl.getClassName().equals(name) ){
                return cl;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(new ClassImpl().findClassbyName("Java").getPf_id() == 0);
    }

}