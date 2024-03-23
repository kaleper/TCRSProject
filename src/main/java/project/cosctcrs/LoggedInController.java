// Controller for logged-in.fxml

package project.cosctcrs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {
    @FXML
    Button button_logout;

    @FXML
    Label label_title;

    @FXML
    Label label_name;
    @FXML
    Label date;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                UtilityDB.changeScene(event, "log-in.fxml", "Log In", null);

            }
        });
        date.setText(Main.getDate());
    }
    public void setUserInformation(String title, String first_name, String last_name){
        label_title.setText(title);
        label_name.setText(first_name + " " + last_name);
    }
}
