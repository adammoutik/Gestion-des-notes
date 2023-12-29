package sample;

// the controller for the login page

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {



    @FXML
    private Button button_login;

    @FXML
    private Button button_sign_up;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_password;







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //adding the functionality to the button
        button_login.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if(!tf_username.getText().isEmpty() && !tf_password.getText().isEmpty()) {

                    DBUtils.logInUser(event, tf_username.getText(), tf_password.getText());
                } else {
                    System.out.println("The information provided is empty");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in the information");
                    alert.show();
                }


            }
        });

        button_sign_up.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "sign-up.fxml", "Sign up!", null, -1);


            }
        });



    }
}
