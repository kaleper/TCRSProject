// Connects to database

package project.cosctcrs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.commons.dbutils.DbUtils;

public class MyJDBC {

    // Change to your database name
    private static final String URL = "jdbc:mysql://localhost:3306/tcrs";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "toor";

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