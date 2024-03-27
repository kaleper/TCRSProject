package project.cosctcrs;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.apache.commons.dbutils.DbUtils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class UtilityDB extends MyJDBC {
    public static void main(String[] args) {
        //System.out.println(DBQueryCol("citations", "licence_plate"));
        //System.out.println(itemExists("citations", "licence_plate","CKR423"));
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
    public static ArrayList<String> DBQueryCol(String table, String column) {
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

    // returns all the information from a row based on if an item matches in a specific column
    public static ArrayList<String[]> DBQueryRows(String table, String conditionColumn, String conditionValue) {
        ArrayList<String[]> rows = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD());
            String query = "SELECT * FROM " + table + " WHERE " + conditionColumn + " = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, conditionValue);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                String[] rowData = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getString(i);
                }
                rows.add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(preparedStatement);
            DbUtils.closeQuietly(connection);
        }
        return rows;
    }

    public static void LogInUser(ActionEvent event, String username, String pass){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD());
            preparedStatement = connection.prepareStatement("SELECT password FROM officer_logins WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("User not found in database.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Incorrect username or password.");
                alert.show();
            } else {
                String retrievedPassword = resultSet.getString("password");
                if (retrievedPassword.equals(pass)){
                    changeScene(event, "logged-in.fxml", "Database Query", username);
                } else {
                    System.out.println("Password is incorrect.");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Incorrect username or password.");
                    alert.show();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in a finally block
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                // Handle or log any exceptions when closing resources
                e.printStackTrace();
            }
        }
    }

    public static void changeScene(ActionEvent event, String fxmlFile, String stage_title, String username){
        Parent root = null;

        if (username != null){
            try {
                FXMLLoader loader = new FXMLLoader(UtilityDB.class.getResource(fxmlFile));
                root = loader.load();
                if (fxmlFile.equals("logged-in.fxml")) {
                    LoggedInController loggedInController = loader.getController();
                    loggedInController.setUserInformation(getUserTitle(username), getUserFirstName(username), getUserLastName(username));
                }
                if (fxmlFile.equals("issue-citation.fxml")) {
                    IssueCitationController issueCitationController = loader.getController();
                    issueCitationController.setUserInformation(getUserTitle(username), getUserFirstName(username), getUserLastName(username));
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                root = FXMLLoader.load(Objects.requireNonNull(UtilityDB.class.getResource(fxmlFile)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(stage_title);
        stage.setScene(new Scene(root,800, 500));
        stage.show();
    }

    public static void openPopUp(ActionEvent event, String fxmlFile) {
        try {
                FXMLLoader popUpLoader = new FXMLLoader(UtilityDB.class.getResource(fxmlFile));
                Parent root2 = (Parent) popUpLoader.load();
                Stage popUpStage = new Stage();
                popUpStage.setTitle(fxmlFile);
                popUpStage.setScene(new Scene (root2));
                popUpStage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
        }
    }


    public static String getUserTitle(String username){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int officer_id;

        try {
            connection = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD());

            // Get officer_id to use in officer table
            preparedStatement = connection.prepareStatement("SELECT officer_id FROM officer_logins WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("User not found in database.");
                return null;
            } else {
                officer_id = resultSet.getInt("officer_id");

                preparedStatement = connection.prepareStatement("SELECT title FROM officers WHERE officer_id = ?");
                preparedStatement.setInt(1, officer_id);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getString("title");
                } else {
                    System.out.println("Title not found for officer_id: " + officer_id);
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in a finally block
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getUserFirstName(String username){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int officer_id = -1; // Initialize officer_id with a default value

        try {
            connection = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD());

            // Get officer_id to use in officer table
            preparedStatement = connection.prepareStatement("SELECT officer_id FROM officer_logins WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("User not found in database.");
                return null;
            } else {
                officer_id = resultSet.getInt("officer_id");

                preparedStatement = connection.prepareStatement("SELECT first_name FROM officers WHERE officer_id = ?");
                preparedStatement.setInt(1, officer_id);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getString("first_name");
                } else {
                    System.out.println("First name not found for officer_id: " + officer_id);
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in a finally block
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getUserLastName(String username){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int officer_id = -1; // Initialize officer_id with a default value

        try {
            connection = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD());

            // Get officer_id to use in officer table
            preparedStatement = connection.prepareStatement("SELECT officer_id FROM officer_logins WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("User not found in database.");
                return null;
            } else {
                officer_id = resultSet.getInt("officer_id");

                preparedStatement = connection.prepareStatement("SELECT last_name FROM officers WHERE officer_id = ?");
                preparedStatement.setInt(1, officer_id);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getString("last_name");
                } else {
                    System.out.println("Last name not found for officer_id: " + officer_id);
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in a finally block
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Integer getOfficerID(String first_name, String last_name){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD());

            // Get officer_id to use in officer table
            preparedStatement = connection.prepareStatement("SELECT officer_id FROM officers WHERE first_name = ? AND last_name = ?");
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("User not found in database.");
                return null;
            } else {
                return resultSet.getInt("officer_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in a finally block
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getUsername(Integer officer_id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD());

            // Get officer_id to use in officer table
            preparedStatement = connection.prepareStatement("SELECT username FROM officer_logins WHERE officer_id = ?");
            preparedStatement.setString(1, String.valueOf(officer_id));
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("User not found in database.");
                return null;
            } else {
                return resultSet.getString("username");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in a finally block
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addCitationToDB(String first_name, String last_name, String drivers_licence, String address, String phone_num, String licence_plate, String make, String model, String year, String color, Integer officer_id, Integer code, Integer amount) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int offender_id;
        int vehicle_id;

        try {
            connection = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD());

            // Check if the offender already exists
            preparedStatement = connection.prepareStatement("SELECT offender_id FROM offenders WHERE first_name = ? AND last_name = ?");
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            resultSet = preparedStatement.executeQuery();

            // If offender doesn't exist, add the offender to the DB and get their offender id
            if (!resultSet.next()) {
                System.out.println("Offender not found in database. Added to the table of offenders");
                offender_id = addOffenderToDB(drivers_licence, first_name, last_name, address, phone_num);
            } else {
                offender_id = resultSet.getInt("offender_id");
            }

            // Check if the vehicle already exists
            preparedStatement = connection.prepareStatement("SELECT vehicle_id FROM vehicles WHERE licence_plate = ?");
            preparedStatement.setString(1, licence_plate);
            resultSet = preparedStatement.executeQuery();

            // If vehicle doesn't exist, add the vehicle to the DB and get its vehicle id
            if (!resultSet.next()) {
                System.out.println("Vehicle not found in database. Added to the table of vehicles");
                vehicle_id = addVehicleToDB(licence_plate, make, model, year, color);
            } else {
                vehicle_id = resultSet.getInt("vehicle_id");
            }

            // Insert citation into citations table
            preparedStatement = connection.prepareStatement("INSERT INTO citations (offender_id, issuing_officer_id, licence_plate, violation_code, paid, amount) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, offender_id);
            preparedStatement.setString(2, String.valueOf(officer_id));
            preparedStatement.setString(3, licence_plate);
            preparedStatement.setString(4, String.valueOf(code));
            preparedStatement.setInt(5, 0); // Assuming citation is not paid initially
            preparedStatement.setInt(6, amount);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // adds a new offender to the DB and returns their offender_id
    public static int addOffenderToDB(String drivers_licence, String first_name, String last_name, String address, String phone_number) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD());
            String query = "INSERT INTO offenders (licence_number, first_name, last_name, home_address, phone_number, warrant) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, drivers_licence);
            preparedStatement.setString(2, first_name);
            preparedStatement.setString(3, last_name);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, phone_number);
            preparedStatement.setString(6, String.valueOf(0));
            preparedStatement.executeUpdate();

            // Retrieve the generated keys (offender_id)
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new SQLException("No keys generated.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static int addVehicleToDB(String licence_plate, String make, String model, String year, String color) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD());
            String query = "INSERT INTO vehicles (licence_plate, make, model, year, color) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, licence_plate);
            preparedStatement.setString(2, make);
            preparedStatement.setString(3, model);
            preparedStatement.setString(4, year);
            preparedStatement.setString(5, color);
            preparedStatement.executeUpdate();

            // Retrieve the generated keys (vehicle_id)
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new SQLException("No keys generated.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getViolationCode(String name) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD());
            String query = "SELECT violation_code FROM violations WHERE violation_name = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("violation_code");
            } else {
                throw new SQLException("Violation not found in database.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}