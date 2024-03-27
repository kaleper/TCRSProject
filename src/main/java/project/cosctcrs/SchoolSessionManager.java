package project.cosctcrs;

import javafx.scene.control.Alert;
import java.sql.*;

public class SchoolSessionManager extends MyJDBC{
    void scheduleSession(int startTime, int endTime, String Location){
        //creates a new traffic school session

    }
    void enrollDriver(int driverId, int twoFourSession, int eightSession, int plansToAttend){ //to be adjusted once Database is updated
        //enrolls a driver in a traffic school session
        

        try
        {
            // create a mysql database connection
            if(checkId(driverId)){
            Connection conn = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD());

            // the mysql insert statement
            String query = " insert into traffic_school_attendees (offender_id, 2_4h_sessions_attended, 8h_session_attended,plans_to_attend)"
                    + " values (?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement prestatement = conn.prepareStatement(query);
            prestatement.setInt (1, driverId);
            prestatement.setInt (2, twoFourSession);
            prestatement.setInt   (3, eightSession);
            prestatement.setInt(4, plansToAttend);
            prestatement.execute();

            conn.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkId(int id) {
        Connection connection = null;
        PreparedStatement checkForDriver = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(getURL(), getUSERNAME(), getPASSWORD());
            checkForDriver = connection.prepareStatement("select * from offenders where offender_id" + " = ?");
            checkForDriver.setInt(1, id);
            resultSet = checkForDriver.executeQuery();

            return resultSet.isBeforeFirst();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close resources in a finally block
            try {
                if (resultSet != null) resultSet.close();
                if (checkForDriver != null) checkForDriver.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                // Handle or log any exceptions when closing resources
                e.printStackTrace();
            }
        }
    }
}
