package CentralAgency;

import java.util.ArrayList;

public class DatabaseManager extends CommunicationManager{
    public static void main(String[] args) {
        System.out.println(DBQuery("citations", "licence_plate"));
    }

    public String queryVehicleInfo(String licence_plate){
        final String TABLE = "vehicles";

        // checks that user inputted a proper value
        if (licence_plate.length() != 6){
            System.err.println("invalid plate number: must be 6 character");
            return null;
        }

        ArrayList plates = DBQuery(TABLE, licence_plate);

        if ()
    }
}
