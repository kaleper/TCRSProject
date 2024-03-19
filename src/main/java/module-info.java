module project.cosctcrs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens project.cosctcrs to javafx.fxml;
    exports project.cosctcrs;
}