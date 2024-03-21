package project.cosctcrs;

import java.sql.*;
import java.util.ArrayList;

public class DBUtils extends MyJDBC {
    public static void main(String[] args) {
        System.out.println(DBQuery("citations", "licence_plate"));
        System.out.println(itemExists("citations", "licence_plate","CKR423"));
    }

    // checks if an item exists in a table
    public static boolean itemExists(String table, String column, String item){
        ResultSet resultSet = null;
        Boolean result;

        try (Connection connection = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD())) {
            PreparedStatement psItemExists = connection.prepareStatement("select * from " + table + " where " + column + " = ?");
            psItemExists.setString(1, item);
            resultSet = psItemExists.executeQuery();

            // returns true if item exist, false if item does not exist
            return resultSet.isBeforeFirst();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // returns a list of all items from a column in a specific table
    public static ArrayList<String> DBQuery(String table, String column){
        String string;
        ArrayList<String> strings = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from " + table);
            while (resultSet.next()) {
                strings.add(resultSet.getString(column));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            
        }
        return strings;
    }
}
