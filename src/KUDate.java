import java.util.Objects;

public class KUDate {
    private int day;
    private int month;
    private int year;

    public KUDate() {
        this.day = 1;
        this.month = 1;
        this.year = 2000;
    }

    public KUDate(String str) {
		this.day = Integer.parseInt(str.substring(0, 2));
		this.month = Integer.parseInt(str.substring(2, 4));
		this.year = Integer.parseInt(str.substring(4, 8));
	}
    
    public KUDate(int day, int month, int year) {
        setDate(day, month, year); // Use the setter to ensure validation
    }

    public void setDate(int day, int month, int year) {
        if (!isValidDate(day, month, year)) {
            throw new IllegalArgumentException("Invalid date: " + day + "/" + month + "/" + year);
        }
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        if (!isValidDate(day, this.month, this.year)) {
            throw new IllegalArgumentException("Invalid day for the current month and year.");
        }
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        if (!isValidDate(this.day, month, this.year)) {
            throw new IllegalArgumentException("Invalid month.");
        }
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (!isValidDate(this.day, this.month, year)) {
            throw new IllegalArgumentException("Invalid year.");
        }
        this.year = year;
    }

    @Override
    public String toString() {
        return String.format("%04d-%02d-%02d", year, month, day); // Formats the date as YYYY-MM-DD
    }

    public static KUDate fromString(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            throw new IllegalArgumentException("Date string cannot be null or empty.");
        }
        String[] parts = dateStr.split("-");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid date format. Expected format: YYYY-MM-DD.");
        }
        try {
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            return new KUDate(day, month, year);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid date components. Ensure the date is in the format YYYY-MM-DD.", e);
        }
    }

    // Utility Methods
    private boolean isValidDate(int day, int month, int year) {
        if (month < 1 || month > 12) {
            return false;
        }
        if (day < 1 || day > daysInMonth(month, year)) {
            return false;
        }
        return year > 0; // Assume no negative years
    }

    private int daysInMonth(int month, int year) {
        switch (month) {
            case 2: // February
                return isLeapYear(year) ? 29 : 28;
            case 4: case 6: case 9: case 11: // April, June, September, November
                return 30;
            default:
                return 31; // All other months
        }
    }

    private boolean isLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        }
        if (year % 100 == 0 && year % 400 != 0) {
            return false;
        }
        return true;
    }

    // Compare Dates (Optional)
    public boolean isBefore(KUDate other) {
        if (this.year < other.year) return true;
        if (this.year == other.year && this.month < other.month) return true;
        if (this.year == other.year && this.month == other.month && this.day < other.day) return true;
        return false;
    }

    public boolean isAfter(KUDate other) {
        return !isBefore(other) && !equals(other);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        KUDate kuDate = (KUDate) obj;
        return day == kuDate.day && month == kuDate.month && year == kuDate.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }
}
