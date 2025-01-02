public class Time {
    private int hours;
    private int minutes;
    private int seconds;

    public Time() {}

    public Time(int hours, int minutes, int seconds) {
        setHours(hours);
        setMinutes(minutes);
        setSeconds(seconds);
    }

    public void setTime(int hours, int minutes, int seconds) {
        setHours(hours);
        setMinutes(minutes);
        setSeconds(seconds);
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        if (hours < 0 || hours > 23) {
            throw new IllegalArgumentException("Hours must be between 0 and 23.");
        }
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        if (minutes < 0 || minutes > 59) {
            throw new IllegalArgumentException("Minutes must be between 0 and 59.");
        }
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        if (seconds < 0 || seconds > 59) {
            throw new IllegalArgumentException("Seconds must be between 0 and 59.");
        }
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds); // Formats time as HH:MM:SS
    }

    public int toSeconds() {
        return (hours * 3600) + (minutes * 60) + seconds; // Converts the time into total seconds
    }

    public static Time fromSeconds(int totalSeconds) {
        if (totalSeconds < 0) {
            throw new IllegalArgumentException("Total seconds cannot be negative.");
        }

        int hours = totalSeconds / 3600;
        totalSeconds %= 3600;

        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        return new Time(hours, minutes, seconds);
    }

    public static Time fromString(String timeString) {
        if (timeString == null || !timeString.matches("\\d{2}:\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("Time must be in HH:MM:SS format.");
        }

        String[] parts = timeString.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);

        return new Time(hours, minutes, seconds);
    }
}
