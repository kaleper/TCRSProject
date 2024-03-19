// Connects to database

package project.cosctcrs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class MyJDBC {

    public static void main (String[] args) {

        try {

            /* Establishes connection to MySql. Local host number is default for MySQL.

           DriverManager = Basic service for managing JDBC drivers
           Connection = connection with a specific database */
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/TCRS_Database",
                    "root", "toor"); // Change to your MySQL user + password

            // Statement = interface representing a SQL statement.
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from Users");

            // next() returns a boolean, returns all data while true
            while (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}