package project.cosctcrs;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class DriverPopUpController implements Initializable {
    @FXML
    Label label_driver_info;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void setLabel_driver_info(String string){
        label_driver_info.setText(string);
    }
}
