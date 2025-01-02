public class Activity {
    private String name;
    private Time duration;
    private int caloriesBurned;
    private KUDate activityDate;
    private String time;
    private String status;

    public Activity() {}

    public Activity(String name, Time duration, int caloriesBurned, KUDate activityDate, String time) {
        setName(name);
        setDuration(duration);
        setCaloriesBurned(caloriesBurned);
        setActivityDate(activityDate);
        setTime(time);
        status = "Approved";
    }

    public Activity(String name, Time duration, int caloriesBurned, KUDate activityDate, String time, String stat) {
        setName(name);
        setDuration(duration);
        setCaloriesBurned(caloriesBurned);
        setActivityDate(activityDate);
        setTime(time);
        status = stat;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String stat){
        status = stat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Activity name cannot be null or empty.");
        }
        this.name = name.trim();
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        if (duration == null || duration.getHours() < 0 || duration.getMinutes() < 0 || duration.getMinutes() >= 60) {
            throw new IllegalArgumentException("Duration must be valid and non-negative.");
        }
        this.duration = duration;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        if (caloriesBurned < 0) {
            throw new IllegalArgumentException("Calories burned cannot be negative.");
        }
        this.caloriesBurned = caloriesBurned;
    }

    public KUDate getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(KUDate activityDate) {
        if (activityDate == null) {
            throw new IllegalArgumentException("Activity date cannot be null.");
        }
        this.activityDate = activityDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        if (time == null) {
            throw new IllegalArgumentException("Time must be in the format HH:MM.");
        }
        this.time = time;
    }

    @Override
    public String toString() {
        return String.format(
                "Activity|%s|%s|%d|%s|%s",
                name, 
                duration, 
                caloriesBurned, 
                activityDate.toString(), 
                time
        );
    }
    
    public static Activity fromString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid activity data: " + line);
        }
        return new Activity(
                parts[0], 
                Time.fromString(parts[1]), 
                Integer.parseInt(parts[2]), 
                KUDate.fromString(parts[3]), 
                parts[4]
        );
    }
}