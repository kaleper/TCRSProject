package project.cosctcrs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.dbutils.DbUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class IssueCitationController implements Initializable {
    @FXML
    Button button_back;

    @FXML
    Button button_issue;

    @FXML
    Label label_title;

    @FXML
    Label label_name;

    @FXML
    Label date;

    @FXML
    ComboBox cb_citation_type;

    @FXML
    TextField tf_first_name;

    @FXML
    TextField tf_last_name;

    @FXML
    TextField tf_drivers_licence;

    @FXML
    TextField tf_address;

    @FXML
    TextField tf_phone_number;

    @FXML
    TextField tf_amount;

    @FXML
    TextField tf_licence_plate;

    @FXML
    TextField tf_make;

    @FXML
    TextField tf_model;

    @FXML
    TextField tf_year;

    @FXML
    TextField tf_color;

    String username;
    Integer officer_id;
    String selected_violation_name;
    int selected_violation_code;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        date.setText(Main.getDate());
        // populates combo list
        ArrayList<String> violation_names = UtilityDB.DBQueryCol("violations", "violation_name");
        for (String names : violation_names){
            cb_citation_type.getItems().add(names);
        }

        button_issue.setOnAction(event -> {
            selected_violation_name = cb_citation_type.getValue().toString();
            selected_violation_code = UtilityDB.getViolationCode(selected_violation_name);
            UtilityDB.addCitationToDB(tf_first_name.getText(), tf_last_name.getText(), tf_drivers_licence.getText(), tf_address.getText(), tf_phone_number.getText(), tf_licence_plate.getText(), tf_make.getText(), tf_model.getText(), tf_year.getText(), tf_color. getText(), officer_id, selected_violation_code, Integer.valueOf(tf_amount.getText()));
        });

        button_back.setOnAction(event -> UtilityDB.changeScene(event, "logged-in.fxml", "Log In", username));
    }

    public void setUserInformation(String title, String first_name, String last_name){
        label_title.setText(title);
        label_name.setText(first_name + " " + last_name);
        officer_id = UtilityDB.getOfficerID(first_name, last_name);
        username = UtilityDB.getUsername(officer_id);
    }
}
