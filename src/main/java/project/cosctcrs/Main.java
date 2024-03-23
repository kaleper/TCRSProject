package project.cosctcrs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("log-in.fxml")));
        stage.setTitle("Log In");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }
}
