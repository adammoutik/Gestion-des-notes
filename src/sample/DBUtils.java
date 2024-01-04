package sample;

import Handlers.Model.Class;
import Handlers.Model.Etudiant;
import Handlers.Model.Professeur;
import Handlers.Model.User;
import Handlers.implementaion.ClassImpl;
import Handlers.implementaion.EtudiantImpl;
import Handlers.implementaion.ProfesseurImpl;
import Handlers.implementaion.UserImpl;
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



    public static void changeProf(ActionEvent event, String fxmlFile, int pf_id, int classId){
        Parent root = null;
        if(pf_id != -1){
            try {
                FXMLLoader  loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));  //creates the scene from an fxml document
                root = loader.load();
                ProfController profController = loader.getController(); // getting the controller so we can pass the data in, pf_id and classid
                profController.getProfInformations(pf_id, classId);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //getting the source of the click, the scene from that, the window from that and casting it here
            stage.setTitle("Add Students");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, int note_id) {

        Parent root = null; //parent is a base class for all the nodes that have children in the scene, this var will be our new scene, turning the fxml file into a scene that we can load
        if(username != null && note_id != -1) {
            try {
                FXMLLoader  loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));  //creates the scene from an fxml document
                root = loader.load(); //we can pass the object that is returned into our root var
                LoggedInController loggedInController = loader.getController(); // getting the controller so we can pass the data in, username and favCar
                loggedInController.setUserInformation(username, note_id);
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

        try {

            UserImpl userImpl = new UserImpl();

            int targetedUserId = userImpl.findUserByUsername(username) ;
            if ( targetedUserId != -1) { //check if the result set is empty, if it returns true - the username is taken
                System.out.println("User already exist");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Error");
                alert.setHeaderText(null);
                alert.setContentText("You cannot use this username.");
                alert.showAndWait();
            } else{
                User usr = new User(firstName, lastName, username, password, role);
                int userId =userImpl.insertUser(usr);
                        // if the class is not taken by a professor
                        if (role.equals("Professeur") && new ClassImpl().findClassbyName(cls).getPf_id() == 0) {
                            ProfesseurImpl profImpl = new ProfesseurImpl();
                            Professeur prof = profImpl.insertProfessor(userId, cls);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Registred successfully");
                            alert.setHeaderText(null);
                            alert.setContentText("You've been successfully registered, login now !");
                            alert.showAndWait();
                            //changeProf(event, "prof.fxml", prof.getPf_id(),prof.getProvidedClass().getClass_id());
                        } else if (new ClassImpl().findClassbyName(cls).getPf_id() != 0 && role.equals("Professeur")) {
                            System.out.println("Class taken by another professeur");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Registration Error");
                            alert.setHeaderText(null);
                            alert.setContentText("This class is taken by another professor, contact administration for more informations!");
                            alert.showAndWait();
                        } else {
                            int classId = new ClassImpl().findClassbyName(cls).getClass_id();
                            Etudiant et = new Etudiant(classId, userId);
                            new EtudiantImpl().insertEtudiant(et);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Registred successfully");
                            alert.setHeaderText(null);
                            alert.setContentText("You've been successfully registered, login now !");
                            alert.showAndWait();
                            System.out.println("Etudiant inserted successfully.");
                        }
                    }


            } catch (Exception e){
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
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Authentication Error");
                alert.setHeaderText(null);
                alert.setContentText("Provided credentials are incorrect.");
                alert.showAndWait();
            } else {
                String retrievedPassword = resultSet.getString("password");
                String retrievedRole = resultSet.getString("role");
                // check password validity
                if (retrievedPassword.equals(password)) {
                    if(retrievedRole.equals("Professeur")){
                        int pfId = new ProfesseurImpl().getProfIdByUsername(username);
                        changeProf(event, "prof.fxml",pfId ,new ClassImpl().getClassIdByProfId(pfId));
                    }else{

                        changeScene(event, "logged-in.fxml", "Welcome", username,new EtudiantImpl().getEtudiantIdByUsername(username));
                    }



                } else {
                    System.out.println("Password did not match!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Authentication Error");
                    alert.setHeaderText(null);
                    alert.setContentText("The provided credentials are incorrect!");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
