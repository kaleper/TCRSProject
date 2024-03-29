package project.cosctcrs;

import java.util.ArrayList;

public class DatabaseManager extends UtilityDB {

    public static String queryVehicleInfo(String licence_plate) {
        final String TABLE = "vehicles";
        final String COLUMN = "licence_plate";

        // Check that user inputted a proper value
        if (licence_plate.length() != 6) {
            System.err.println("Invalid plate number: must be 6 characters");
            return null;
        }

        ArrayList<String[]> vehicleInfoList = DBQueryRows(TABLE, COLUMN, licence_plate);

        if (!vehicleInfoList.isEmpty()) {
            // Assuming each row contains id, plate, make, model, year, color in order
            String[] vehicleInfo = vehicleInfoList.get(0); // Assuming only one row is retrieved

            // Extract individual fields
            String id = vehicleInfo[0];
            String plate = vehicleInfo[1];
            String make = vehicleInfo[2];
            String model = vehicleInfo[3];
            String year = vehicleInfo[4];
            String color = vehicleInfo[5];

            // Format the vehicle information string
            return String.format("Vehicle Info:\nID: %s\nPlate: %s\nMake: %s\nModel: %s\nYear: %s\nColor: %s",
                    id, plate, make, model, year, color);
        } else {
            return "No information found for the provided license plate.";
        }
    }
}
