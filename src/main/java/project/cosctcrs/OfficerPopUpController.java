package project.cosctcrs;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class OfficerPopUpController implements Initializable {
    @FXML
    Label label_officer_info;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void setLabel_officer_info(String string){
        label_officer_info.setText(string);
    }
}
