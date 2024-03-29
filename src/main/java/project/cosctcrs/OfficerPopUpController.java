package project.cosctcrs;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class OfficerPopUpController implements Initializable {

    @FXML
    public Label officerInfo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        officerInfo.setText(Main.officerSetInfo());

    }


}
