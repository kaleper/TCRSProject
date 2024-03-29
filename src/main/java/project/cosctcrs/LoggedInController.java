// Controller for logged-in.fxml

package project.cosctcrs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    @FXML
    TextField tf_driver_name_plate;

    @FXML
    TextField tf_off_name_id;


    Integer officer_id;
    String username;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        date.setText(Main.getDate());

        button_logout.setOnAction(event -> {
            UtilityDB.changeScene(event, "log-in.fxml", "Log In", null);
            officer_id = null;
        });

        button_open_issue_scene.setOnAction(event -> {
            UtilityDB.changeScene(event, "issue-citation.fxml", "Log In", username);
        });

        button_open_search_drivers.setOnAction(event -> {
            String offenderInfo = UtilityDB.getOffenderInformationByName(tf_driver_name_plate.getText());
            if (offenderInfo == null) {
                // Display an alert if offender not found in database
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Offender not found in database.");
                alert.showAndWait();
            } else {
                // Open pop-up with offender information
                UtilityDB.openPopUp(event, "pop-up-drivers.fxml", "Search For A Driver", offenderInfo);
            }
        });

        button_open_search_officers.setOnAction(event -> {
            String inputText = tf_off_name_id.getText().trim();
            if (inputText.isEmpty()) {
                // Display an alert if no input is provided
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Officer not found in database.");
                alert.showAndWait();
                return;
            }

            String officerInfo = UtilityDB.getOfficerInformation(inputText);
            if (officerInfo == null) {
                // Display an alert if officer not found in database
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Officer not found in database.");
                alert.showAndWait();
            } else {
                // Open pop-up with officer information
                UtilityDB.openPopUp(event, "pop-up-officers.fxml", "Search For An Officer", officerInfo);
            }
        });
    }

    public void setUserInformation(String title, String first_name, String last_name){
        label_title.setText(title);
        label_name.setText(first_name + " " + last_name);
        officer_id = UtilityDB.getOfficerID(first_name, last_name);
        username = UtilityDB.getUsername(officer_id);
    }
}
