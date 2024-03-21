// Connects to database

package project.cosctcrs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.commons.dbutils.DbUtils;

public class MyJDBC {

    private static final String URL = "jdbc:mysql://localhost:3306/tcrs_database";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "toor";
    public static void main (String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            /*
            Establishes connection to MySql. Local host number is default for MySQL.
            DriverManager = Basic service for managing JDBC drivers
            Connection = connection with a specific database
            */

            // Change to your MySQL user + password
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Statement = interface representing a SQL statement.
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from citations");

            // next() returns a boolean, returns all data while true
            while (resultSet.next()) {
                System.out.println(resultSet.getString("licence_plate"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources quietly
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(connection);
        }

    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static String getURL() {
        return URL;
    }

    public static String getUSERNAME() {
        return USERNAME;
    }
}