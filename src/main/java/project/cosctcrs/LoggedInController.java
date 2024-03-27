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
    Button button_open_issue_scene;

    @FXML
    Label label_title;

    @FXML
    Label label_name;
    @FXML
    Label date;

    @FXML
    Button button_open_search_drivers;
    @FXML
    Button button_open_search_officers;

    Integer officer_id;
    String username;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_logout.setOnAction(event -> {
            UtilityDB.changeScene(event, "log-in.fxml", "Log In", null);
            officer_id = null;
            date.setText(Main.getDate());
        });

        button_open_issue_scene.setOnAction(event -> {
            UtilityDB.changeScene(event, "issue-citation.fxml", "Log In", username);
        });

        button_open_search_drivers.setOnAction(event -> {
            UtilityDB.openPopUp(event, "pop-up-drivers.fxml", "Search For A Driver");
        });

        button_open_search_officers.setOnAction(event -> {
            UtilityDB.openPopUp(event, "pop-up-officers.fxml", "Search For An Officer");
        });
    }

    public void setUserInformation(String title, String first_name, String last_name){
        label_title.setText(title);
        label_name.setText(first_name + " " + last_name);
        officer_id = UtilityDB.getOfficerID(first_name, last_name);
        username = UtilityDB.getUsername(officer_id);
    }
}
