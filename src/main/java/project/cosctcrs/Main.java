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
        //System.out.print(setInfo());
    }
    public static String getDate() {
        LocalDate localdate = LocalDate.now();
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String date = dateformatter.format(localdate);
        return date;
    }
    public static String officerSetInfo() {
        String officerinfo;
        String fName, lName, title;
        fName = "Patrick";
        lName = "Star";
        title = "Starfish";
        int ticketNum = 0;
        officerinfo = "First Name: " + fName+ "\nLast Name: "+ lName+ "\nTitle: "+ title+ "\nTickets Issued: "+ ticketNum;
        return officerinfo;
    }
    public static String driverSetInfo() {
        String driverinfo;
        String fName, lName, homeAdd;
        fName = "Marie";
        lName = "Star";
        homeAdd = "35 Addler Street, New Castle, Ontario";
        Boolean warrant = false;
        int driverNum = 0, phoneNum = 444444444;
        driverinfo = "Driver's License Number: "+ driverNum+"\nFirst Name: "+ fName+"\nLast Name: "+ lName+"\nHome_Address: "+ homeAdd+"\nPhone Number: "+ phoneNum+"\nWarrant: " + warrant;
        return driverinfo;
    }
}
