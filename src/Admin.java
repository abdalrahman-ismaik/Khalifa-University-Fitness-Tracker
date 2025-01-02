public class Admin extends Account {
    // Constructors
    public Admin(String name, String password) {
        super(name, password);
    }

    // Method to set the status of a goal
    public void setGoalStatus(Goal goal, String status) {
        if (goal != null) {
            goal.setStatus(status);
            System.out.println("Goal status updated to: " + status);
        } else {
            System.out.println("Goal cannot be null.");
        }
    }
}