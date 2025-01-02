import java.util.*;
import java.io.*;

public class User extends Account {
    private static final String ID_FILE = "id_counter.txt";
    private String email;
    private KUDate birthdate;
    private String phoneNumber;
    private String address;
    private int totalCaloriesBurned;
    private List<Activity> activities = new ArrayList<>();
    private List<Goal> goals = new ArrayList<>();

    public User() {}

    public User(String name, String email, String password, KUDate birthdate, String phoneNumber, String address) {
        super(name, password);
        setEmail(email);
        setBirthdate(birthdate);
        setPhone(phoneNumber);
        setAddress(address);
        this.totalCaloriesBurned = 0;
        generateUniqueId();
    }

    // New constructor with explicit ID
    public User(String id, String name, String email, String phoneNumber, String address, String password) {
        super(name, password);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.totalCaloriesBurned = 0;
        this.birthdate = null; // Default value if birthdate is not passed
        this.id = id;
    }

    public void setID(String id){
        this.id = id;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }
    
    public void setPhone(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty.");
        }
        if (!phoneNumber.matches("\\d{10,15}")) {
            throw new IllegalArgumentException("Phone number must contain 10 to 15 digits.");
        }
        this.phoneNumber = phoneNumber;
    }
    
    public void setBirthdate(KUDate birthdate) {
        if (birthdate == null) {
            throw new IllegalArgumentException("Birthdate cannot be null.");
        }
        this.birthdate = birthdate;
    }
    
    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) 
            throw new IllegalArgumentException("Address cannot be null or empty.");
        this.address = address;
    }
    
    // Validation for total calories burned
    public void logCalories(int calories) {
        if (calories < 0) {
            throw new IllegalArgumentException("Calories burned cannot be negative.");
        }
        this.totalCaloriesBurned += calories;
    }

    // Getter methods
    public String getEmail() {
        return email;
    }

    public KUDate getBirthdate() {
        return birthdate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public int getTotalCaloriesBurned() {
        return totalCaloriesBurned;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }

    private void generateUniqueId() {
        int currentId = 1000; // Default starting ID
        File file = new File(ID_FILE);

        try {
            if (!file.exists()) {
                file.createNewFile();
                writeIdToFile(currentId); // Write the starting ID
            } else {
                Scanner reader = new Scanner(new FileReader(file));
                if (reader.hasNextLine()) {
                    currentId = Integer.parseInt(reader.nextLine().trim());
                }
                reader.close();
            }

            int newId = currentId + 1;
            writeIdToFile(newId);

            id = "KU" + currentId;
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Error generating unique ID: " + e.getMessage());
        }
    }

    private static void writeIdToFile(int id) throws IOException {
        try (PrintWriter writer = new PrintWriter(ID_FILE)) {
            writer.write(String.valueOf(id));
        }
    }

    public void addActivity(Activity activity){
        activities.add(activity);
        Database.saveUsers(Main.getUsers());
    }
    
    public void addGoal(Goal goal){
        goals.add(goal);
        Database.saveUsers(Main.getUsers());
    }

    public void generateProgressReport() {
        String fileName = this.id + "_Progress_Report.txt"; // Unique file for each user
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Write user information and progress data to the file
            writer.println("Progress Report for User: " + getEmail());
            writer.println("User ID: " + this.id);
            writer.println("Total Calories Burned: " + this.totalCaloriesBurned);
            writer.println("\nFitness Activities:");
    
            // Check if the user has logged any activities
            if (activities.isEmpty()) {
                writer.println("No activities logged yet.");
            } else {
                for (Activity activity : activities) {
                    writer.println(activity.toString()); // Uses Activity's toString method
                }
            }
    
            writer.println("\nGoals:");
    
            // Check if the user has set any goals
            if (goals.isEmpty()) {
                writer.println("No goals set yet.");
            } else {
                for (Goal goal : goals) {
                    writer.println(goal.toString()); // Uses Goal's toString method
                }
            }
    
            writer.println("\nThank you for using the KU Fitness Tracker!");
            System.out.println("Progress report saved as: " + fileName); // Confirmation message
        } catch (IOException e) {
            System.err.println("Error generating progress report: " + e.getMessage());
        }
    }
    
    public boolean removeActivity(String activityName) {
        for (Activity activity : activities) { // Assuming activities is a list of Activity objects
            if (activity.getName().equals(activityName)) {
                activities.remove(activity); // Remove the activity
                Database.saveUsers(Main.getUsers());
                return true;
            }
        }
        return false; // Activity not found
    }
    

    public boolean removeGoal(String goalName) {
        for (Goal goal : goals) {
            if (goal.getGoalDescription().equals(goalName)) {
                goals.remove(goal);
                Database.saveUsers(Main.getUsers());
                return true; // Indicate successful removal
            }
        }
        return false; // Goal not found
    }

    // Update an activity by its name
    public boolean updateActivity(String oldActivityName, Activity newActivity) {
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i).getName().equals(oldActivityName)) {
                activities.set(i, newActivity);
                Database.saveUsers(Main.getUsers());
                return true;
            }
        }
        return false; // Activity not found
    }
}