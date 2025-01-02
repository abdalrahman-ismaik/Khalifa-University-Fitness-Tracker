public class Goal {
    private String goalDescription;
    private KUDate startDate;
    private KUDate endDate;
    private String status = "Pending";
    private int targetValue;
    private int current;
    private int progress;

    public Goal() {}

    public Goal(String goalDescription, int targetValue, int progress, KUDate startDate, KUDate endDate) {
        setGoalDescription(goalDescription);
        setTargetValue(targetValue);
        setProgress(progress);
        setStartDate(startDate);
        setEndDate(endDate);
    }

    public Goal(String goalDescription, int targetValue, int progress, KUDate startDate, KUDate endDate, String stat) {
        setGoalDescription(goalDescription);
        setTargetValue(targetValue);
        setProgress(progress);
        setStartDate(startDate);
        setEndDate(endDate);
        setStatus(stat);
    }


    public String getGoalDescription() {
        return goalDescription;
    }

    public void setGoalDescription(String goalDescription) {
        if (goalDescription == null || goalDescription.isEmpty()) {
            throw new IllegalArgumentException("Goal description cannot be null or empty.");
        }
        if (goalDescription.length() > 500) {
            throw new IllegalArgumentException("Goal description must be 500 characters or less.");
        }
        this.goalDescription = goalDescription;
    }

    public int getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(int targetValue) {
        if (targetValue < 0) {
            throw new IllegalArgumentException("Target value cannot be negative.");
        }
        this.targetValue = targetValue;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current < 0) {
            throw new IllegalArgumentException("Current value cannot be negative.");
        }
        this.current = current;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public KUDate getStartDate() {
        return startDate;
    }

    public void setStartDate(KUDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null.");
        }
        this.startDate = startDate;
    }

    public KUDate getEndDate() {
        return endDate;
    }

    public void setEndDate(KUDate endDate) {
        if (endDate == null) {
            throw new IllegalArgumentException("End date cannot be null.");
        }
        if (startDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty.");
        }
        if (!status.equalsIgnoreCase("pending") &&
            !status.equalsIgnoreCase("approved") &&
            !status.equalsIgnoreCase("rejected")) {
            throw new IllegalArgumentException("Invalid status. Allowed values are 'pending', 'approved', or 'completed'.");
        }
        this.status = status.toLowerCase();
    }

    @Override
    public String toString() {
        return String.format("Description|%s\nTargetValue|%d\nProgress|%d\nStartDate|%s\nEndDate|%s\nStatus|%s",
                goalDescription, targetValue, progress, startDate, endDate, status);
    }

    public static Goal fromString(String[] goalData) {
        return new Goal(
                goalData[0].split("\\|", 2)[1],  // Description
                Integer.parseInt(goalData[1].split("\\|", 2)[1]),  // TargetValue
                Integer.parseInt(goalData[2].split("\\|", 2)[1]),  // Progress
                KUDate.fromString(goalData[3].split("\\|", 2)[1]),  // StartDate
                KUDate.fromString(goalData[4].split("\\|", 2)[1]),  // EndDate
                goalData[5].split("\\|", 2)[1]  // Status
        );
    }
}
