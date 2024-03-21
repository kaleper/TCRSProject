package project.cosctcrs;

import org.apache.commons.dbutils.DbUtils;

import java.sql.*;
import java.util.ArrayList;

public class UtilityDB extends MyJDBC {
    public static void main(String[] args) {
        System.out.println(DBQuery("citations", "licence_plate"));
        System.out.println(itemExists("citations", "licence_plate","CKR423"));
    }

    // checks if an item exists in a table
    public static boolean itemExists(String table, String column, String item){
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement psItemExists = null;

        try {
            connection = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD());
            psItemExists = connection.prepareStatement("select * from " + table + " where " + column + " = ?");
            psItemExists.setString(1, item);
            resultSet = psItemExists.executeQuery();

            // returns true if item exist, false if item does not exist
            return resultSet.isBeforeFirst();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(psItemExists);
            DbUtils.closeQuietly(connection);
        }

        return false;
    }

    // returns a list of all items from a column in a specific table
    public static ArrayList<String> DBQuery(String table, String column) {
        ArrayList<String> strings = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD());
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from " + table);
            while (resultSet.next()) {
                strings.add(resultSet.getString(column));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(connection);
        }
        return strings;
    }
}
