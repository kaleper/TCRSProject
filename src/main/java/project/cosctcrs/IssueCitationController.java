package project.cosctcrs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.apache.commons.dbutils.DbUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class IssueCitationController implements Initializable {
    @FXML
    Button button_back;

    @FXML
    Label label_title;

    @FXML
    Label label_name;

    @FXML
    ComboBox cb_citation_type;

    String username;
    Integer officer_id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // populates combo list
        ArrayList<String> violation_names = UtilityDB.DBQueryCol("violations", "violation_name");
        for (String names : violation_names){
            cb_citation_type.getItems().add(names);
        }

        button_back.setOnAction(event -> UtilityDB.changeScene(event, "logged-in.fxml", "Log In", username));
    }

    public void setUserInformation(String title, String first_name, String last_name){
        label_title.setText(title);
        label_name.setText(first_name + " " + last_name);
        officer_id = UtilityDB.getOfficerID(first_name, last_name);
        username = UtilityDB.getUsername(officer_id);
    }
}
