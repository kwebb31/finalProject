package finalProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UserLoader {
    public static ArrayList<User> loadUsersFromFile(String filePath) {
        ArrayList<User> userList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the CSV line into individual values
                String[] values = line.split(",");
                
                // Extract values and create User object
                String userName = values[2];
                Integer id = Integer.parseInt(values[0]);
                Role userRole = Role.valueOf(values[1]);
                String userPassword = values[3];
                Boolean userIsOnline = false;
                Boolean userLoginSuccessful = false;

                // Create User object and add to the list
                User user = new User(userName, userRole, userPassword, userIsOnline, userLoginSuccessful);
                user.id = id;  // Set the ID explicitly as it's already assigned
                userList.add(user);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }

        return userList;
    }
}

