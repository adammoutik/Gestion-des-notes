package sample;

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




    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String favCar) {

        Parent root = null; //parent is a base class for all the nodes that have children in the scene, this var will be our new scene, turning the fxml file into a scene that we can load

        if(username != null && favCar != null) {
            try {
                FXMLLoader  loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));  //creates the scene from an fxml document
                root = loader.load(); //we can pass the object that is returned into our root var
                LoggedInController loggedInController = loader.getController(); // getting the controller so we can pass the data in, username and favCar
                loggedInController.setUserInformation(username, favCar);
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
        stage.setScene(new Scene(root, 600, 400 )); // scene, width and height
        stage.show();

    }





    public static void signUpUser(ActionEvent event, String username, String password, String favCar) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysqlp://localhost:3306/javafxsql", "root", "Borsec1D"); // forming a connection to the databse
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?"); // ? is the one item that will be targeted in order to be replaced by username
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst()) { //check if the result set is empty, if it returns true - the username is taken
                System.out.println("User already exist");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO users (username, password, favCar) VALUES (?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, favCar);
                psInsert.executeUpdate();


                changeScene(event, "logged-in.fxml", "Welcome!", username, favCar);
            }
        } catch (SQLException e) { // everytime you are done with the db connection, close it
            e.printStackTrace();
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            if(psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace(); //se poate ca change scene sa fie metoda se schimbat sceneul pentru logged in nu orice scene( loggeninController )
                }
            }

        }
    }





    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysqlp://localhost:3306/javafxsql", "root", "Borsec1D");
            preparedStatement = connection.prepareStatement("SELECT password, favCar FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.isBeforeFirst()) {   //if the user does not exist in the database
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect");
                alert.show();
            } else {
                while(resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password"); // only getting the password column
                    String retrievedCar = resultSet.getString("favCar");
                    if(retrievedPassword.equals(password)) {
                        changeScene(event, "logged-in.fxml", "Welcome", username, retrievedCar);
                    } else {
                        System.out.println("Password did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect!");
                        alert.show();
                    }
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {  //the order in which we close, result set, statement, connection
            if(resultSet != null) {
                try{
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(preparedStatement != null) {
                try{
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                try{
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
