package project.cosctcrs;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DriverPopUpController implements Initializable {

    public Label officerInfo;
    @FXML
    Label driverInfo;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        driverInfo.setText(Main.driverSetInfo());
    }
}
