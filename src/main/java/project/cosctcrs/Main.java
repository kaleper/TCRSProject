package project.cosctcrs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("log-in.fxml")));

        // Uncomment to test citation page
        // Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("issue-citation.fxml")));

        stage.setTitle("Log In");
        stage.setScene(new Scene(root, 800, 500));
        stage.show();
    }
    public static String getDate() {
        LocalDate localdate = LocalDate.now();
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String date = dateformatter.format(localdate);
        return date;
    }
}
