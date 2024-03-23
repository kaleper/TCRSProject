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

        // Uncomment for usual log-in

        //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("log-in.fxml")));

        // TEST - to see issue-citation page
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("issue-citation.fxml")));

        stage.setTitle("Log In");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }
}
