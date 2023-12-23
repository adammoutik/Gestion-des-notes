package sample;

import Handlers.Model.Class;
import Handlers.Model.Etudiant;
import Handlers.implementaion.ClassImpl;
import Handlers.implementaion.EtudiantImpl;
import Handlers.implementaion.ProfesseurImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.*;

import java.io.IOException;

//only contains static methods, cannot be instatiated
public class DBUtils {



    public static void changeProf(ActionEvent event, String fxmlFile, int pf_id){
        Parent root = null;
        if(pf_id != -1){
            try {
                FXMLLoader  loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));  //creates the scene from an fxml document
                root = loader.load(); //we can pass the object that is returned into our root var
                ProfController profController = loader.getController(); // getting the controller so we can pass the data in, username and favCar
                profController.getProfInformations(pf_id);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //getting the source of the click, the scene from that, the window from that and casting it here
            stage.setTitle("Add Students");
            stage.setScene(new Scene(root)); // scene, width and height
            stage.show();
        }
    }
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String role) {

        Parent root = null; //parent is a base class for all the nodes that have children in the scene, this var will be our new scene, turning the fxml file into a scene that we can load

        if(username != null && role != null) {
            try {
                FXMLLoader  loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));  //creates the scene from an fxml document
                root = loader.load(); //we can pass the object that is returned into our root var
                LoggedInController loggedInController = loader.getController(); // getting the controller so we can pass the data in, username and favCar
                loggedInController.setUserInformation(username, role);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //getting the source of the click, the scene from that, the window from that and casting it here
        stage.setTitle(title);
        stage.setScene(new Scene(root)); // scene, width and height
        stage.show();

    }





    public static void signUpUser(ActionEvent event, String username, String password, String role, String firstName, String lastName, String cls) throws SQLException {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GestionNotes", "adamos", "password"); // forming a connection to the databse
            psCheckUserExists = connection.prepareStatement("SELECT * FROM User WHERE username = ?"); // ? is the one item that will be targeted in order to be replaced by username
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();
            String insertUserQuery = "INSERT INTO User (username, first_name, last_name, role, password) VALUES (?, ?, ?, ?, ?)";

            if (resultSet.isBeforeFirst()) { //check if the result set is empty, if it returns true - the username is taken
                System.out.println("User already exist");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username.");
                alert.show();
            } else{  //if the class is not taken by a professor
                psInsert = connection.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS);
                psInsert.setString(1, username);
                psInsert.setString(2, firstName);
                psInsert.setString(3, lastName);
                psInsert.setString(4, role);
                psInsert.setString(5, password);
                int aaffectedRows = psInsert.executeUpdate();


                try (ResultSet generatedKeys = psInsert.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);

                        if (role.equals("Professeur") && new ClassImpl().findClassbyName(cls).getPf_id() == 0) {
                            String insertProfesseurQuery = "INSERT INTO Professeur (user_id) VALUES (?)";
                            try (PreparedStatement psInsertProf = connection.prepareStatement(insertProfesseurQuery, Statement.RETURN_GENERATED_KEYS)) {
                                psInsertProf.setInt(1, userId);
                                int affectedRows = psInsertProf.executeUpdate();
                                if (affectedRows > 0) {
                                    ResultSet genKeys = psInsertProf.getGeneratedKeys();
                                    if (genKeys.next()) {
                                        int pf_id = genKeys.getInt(1);
                                        System.out.println("Professeur inserted successfully.");
                                        changeProf(event, "prof.fxml", pf_id);
                                    }
                                }
                            }
                        } else if (new ClassImpl().findClassbyName(cls).getPf_id() != 0) {
                            System.out.println("Class taken by another professeur");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("This class is taken by another professor!");
                            alert.show();
                        } else {
                            int classId = new ClassImpl().findClassbyName(cls).getClass_id();
                            Etudiant et = new Etudiant(classId, userId);
                            new EtudiantImpl().insertEtudiant(et);
                            System.out.println("Etudiant inserted successfully.");
                        }
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


        public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/GestionNotes", "adamos", "password");
            preparedStatement = connection.prepareStatement("SELECT password, role FROM User WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) { // Check if user doesn't exist
                System.out.println("User not found in the database!");
                // Display a generic error message without specifying user existence or password issues.
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            } else {
                String retrievedPassword = resultSet.getString("password");
                String retrievedRole = resultSet.getString("role");

                // Check password validity (consider using a secure password handling mechanism)
                if (retrievedPassword.equals(password)) {
                    if(retrievedRole.equals("Professeur")){
                        changeProf(event, "prof.fxml", 0);
                    }else{
                        changeScene(event, "logged-in.fxml", "Welcome", username, retrievedRole);
                    }



                } else {
                    System.out.println("Password did not match!");
                    // Display a generic error message without specifying password issues.
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("The provided credentials are incorrect!");
                    alert.show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources in reverse order of opening to avoid resource leaks
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
