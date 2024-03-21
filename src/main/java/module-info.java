module project.cosctcrs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.commons.dbutils;


    opens project.cosctcrs to javafx.fxml;
    exports project.cosctcrs;
}