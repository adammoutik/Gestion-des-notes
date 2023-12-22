package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button button_signup;

    @FXML
    private Button button_log_in;

    @FXML
    private RadioButton rb_miata;

    @FXML
    private RadioButton rb_irelevant;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_password;

    @FXML
    private TextField tf_firstName;

    @FXML
    private TextField tf_lastName;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ToggleGroup toggleGroup = new ToggleGroup();
        rb_miata.setToggleGroup(toggleGroup);
        rb_irelevant.setToggleGroup(toggleGroup);  // so that only one radiobutton can be selected

        rb_miata.setSelected(true); //when you enter the sign up page, the miata radio button is selected

        button_signup.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText(); //name of the radio button that is selected

                if(!tf_username.getText().isEmpty() && !tf_password.getText().isEmpty() && !tf_firstName.getText().isEmpty() && !tf_lastName.getText().isEmpty()) {
                    DBUtils.signUpUser(event, tf_username.getText(), tf_password.getText(),toggleName, tf_firstName.getText(), tf_lastName.getText());
                } else {
                    System.out.println("Please fill in all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up!");
                    alert.show();
                }
            }
        });

        button_log_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "sample.fxml", "Log In!", null, null);
            }
        });



    }
}
