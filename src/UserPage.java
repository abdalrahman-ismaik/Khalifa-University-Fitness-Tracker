import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import com.raven.swing.TimePicker;
import com.toedter.calendar.JDateChooser;

public class UserPage extends JFrame implements ActionListener {

    // Sidebar and Card Layout Components
    private JPanel leftPanel, rightPanel, cardPanel, sidebarButtonsPanel;
    private JButton profileButton, fitnessButton, goalButton, logoutButton;
    private CardLayout cardLayout;

    // Profile Components
    private JTextField nametf, emailtf, phonetf, addresstf, idtf;
    private JPasswordField passtf;
    private JButton editButton, saveButton;

    // Simulated User Object
    private User user;

    /**
     * Constructor: Initializes the UserPage frame and components.
     */
    public UserPage() {
        user = (User) Main.getUsers().get(Main.getIdx()); // Simulated user object from Main
        initComponents();
    }

    /**
     * Initializes and organizes all components in the UserPage.
     */
    private void initComponents() {
        setTitle("User Page - KU Fitness Tracker");
        setSize(1280, 720);
        setMinimumSize(new Dimension(900, 600));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setupSidebar();
        setupCardPanel();

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Sets up the sidebar (left panel) with navigation buttons.
     */
    private void setupSidebar() {
        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(62, 8, 76));
        leftPanel.setPreferredSize(new Dimension(250, getHeight()));

        // Sidebar Buttons Panel
        sidebarButtonsPanel = new JPanel();
        sidebarButtonsPanel.setLayout(new BoxLayout(sidebarButtonsPanel, BoxLayout.Y_AXIS));
        sidebarButtonsPanel.setBackground(new Color(62, 8, 76));

        JLabel kuIcon = new JLabel(new ImageIcon("logo\\KU logo - 2.jpg"));
        kuIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        kuIcon.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        // Create Navigation Buttons
        profileButton = createSidebarButton("My Profile");
        fitnessButton = createSidebarButton("Fitness Activity");
        goalButton = createSidebarButton("Goals");
        logoutButton = createSidebarButton("Logout");

        // Add components to the sidebar
        sidebarButtonsPanel.add(kuIcon);
        sidebarButtonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebarButtonsPanel.add(profileButton);
        sidebarButtonsPanel.add(fitnessButton);
        sidebarButtonsPanel.add(goalButton);

        leftPanel.add(sidebarButtonsPanel, BorderLayout.NORTH);
        leftPanel.add(logoutButton, BorderLayout.SOUTH);
    }

    /**
     * Sets up the main content area with a CardLayout for switching views.
     */
    private void setupCardPanel() {
        rightPanel = new JPanel(new BorderLayout());
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add sub-panels for each section
        cardPanel.add(createProfilePanel(), "Profile");
        cardPanel.add(createFitnessPanel(), "Fitness");
        cardPanel.add(createGoalPanel(), "Goals");
        rightPanel.add(cardPanel, BorderLayout.CENTER);
    }

    /**
     * Creates a styled button for the sidebar.
     * @param text The text to display on the button.
     * @return The styled JButton.
     */
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(null);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(this);
        button.setMaximumSize(new Dimension(200, 50));
        return button;
    }

    /**
     * Creates the Profile Panel with user details and actions.
     * @return JPanel representing the Profile view.
     */
    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(134, 244, 238));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("My Profile", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(62, 8, 76));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // User Details Form
        JPanel detailsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        detailsPanel.setBackground(new Color(134, 244, 238));

        idtf = createTextField(user.getId(), "User Id");
        idtf.setEditable(false);
        nametf = createTextField(user.getName(), "Full Name");
        nametf.setEditable(false);
        emailtf = createTextField(user.getEmail(), "Email");
        emailtf.setEditable(false);
        phonetf = createTextField(user.getPhoneNumber(), "Phone Number");
        phonetf.setEditable(false);
        addresstf = createTextField(user.getAddress(), "Address");
        addresstf.setEditable(false);
        passtf = createPasswordField(user.getPassword(), "Password");
        passtf.setEditable(false);

        detailsPanel.add(idtf);
        detailsPanel.add(nametf);
        detailsPanel.add(emailtf);
        detailsPanel.add(phonetf);
        detailsPanel.add(addresstf);
        detailsPanel.add(passtf);

        // Action Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        editButton = createButton("Edit", e -> enableEditing(true));
        saveButton = createButton("Save", e -> saveChanges());
        saveButton.setEnabled(false);

        JButton printReportButton = createButton("Print Progress Report", e -> user.generateProgressReport());

        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(printReportButton);

        panel.add(title, BorderLayout.NORTH);
        panel.add(detailsPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Creates a JTextField with specified text and placeholder.
     */
    private JTextField createTextField(String text, String placeholder) {
        JTextField textField = new JTextField(text);
        textField.setBorder(BorderFactory.createTitledBorder(placeholder));
        textField.setEditable(true);
        return textField;
    }

    /**
     * Creates a JPasswordField with specified text and placeholder.
     */
    private JPasswordField createPasswordField(String text, String placeholder) {
        JPasswordField passwordField = new JPasswordField(text);
        passwordField.setBorder(BorderFactory.createTitledBorder(placeholder));
        passwordField.setEditable(false);
        return passwordField;
    }

    /**
     * Creates a JButton with specified text and ActionListener.
     */
    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(new Color(62, 8, 76));
        button.addActionListener(action);
        return button;
    }

    /**
     * Enables or disables editing of user profile fields.
     */
    private void enableEditing(boolean editable) {
        nametf.setEditable(editable);
        emailtf.setEditable(editable);
        phonetf.setEditable(editable);
        addresstf.setEditable(editable);
        passtf.setEditable(editable);

        editButton.setEnabled(!editable);
        saveButton.setEnabled(editable);
    }

    /**
     * Saves the changes made to the user profile.
     */
    private void saveChanges() {
        try {
            // Validate fields before updating
            if (!emailtf.getText().matches("[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}")) {
                throw new IllegalArgumentException("Invalid email format.");
            }
            if (!phonetf.getText().matches("\\d{10}")) {
                throw new IllegalArgumentException("Phone number must be 10 digits.");
            }
    
            // Update user object
            user.setName(nametf.getText().trim());
            user.setEmail(emailtf.getText().trim());
            user.setPhone(phonetf.getText().trim());
            user.setAddress(addresstf.getText().trim());
            user.setPassword(new String(passtf.getPassword()));
    
            enableEditing(false); // Disable editing after save
            JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to update profile. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    /**
     * Creates the Fitness Activity Panel.
     * @return JPanel representing the Fitness view.
     */
    private JPanel createFitnessPanel() {
        JPanel fitnessPanel = new JPanel(new BorderLayout());
        fitnessPanel.setBackground(new Color(200, 255, 200));
        fitnessPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // Title label
        JLabel title = new JLabel("Fitness Activities", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(62, 8, 76));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    
        // Table for displaying fitness activities
        String[] columns = {"Name", "Duration", "Calories Burned", "Date", "Time", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable fitnessTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(fitnessTable);
    
        // Summary Panel
        JPanel summaryPanel = createSummaryPanel();
    
        // Buttons for managing activities
        JPanel buttonPanel = createButtonPanel(tableModel, fitnessTable, summaryPanel);
    
        // Center layout: Table + Summary Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(tableScrollPane);
        centerPanel.add(summaryPanel);
    
        // Add components to the fitness panel
        fitnessPanel.add(title, BorderLayout.NORTH);
        fitnessPanel.add(centerPanel, BorderLayout.CENTER);
        fitnessPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Load user's activities into the table
        user.getActivities().forEach(activity -> {
            tableModel.addRow(new Object[]{
                activity.getName(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getActivityDate(),
                activity.getTime(),
                activity.getStatus()
            });
        });

        updateSummaryPanel(tableModel, summaryPanel);
        
        return fitnessPanel;
    }
    
    // Create Summary Panel
    private JPanel createSummaryPanel() {
        JPanel summaryPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // Adjust layout to horizontal
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));
        summaryPanel.setBackground(new Color(240, 240, 240));
    
        JLabel totalCaloriesLabel = new JLabel("Total Calories Burned: 0");
        totalCaloriesLabel.setName("totalCaloriesLabel");
        totalCaloriesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    
        JLabel totalTimeLabel = new JLabel("Total Time Spent: 00:00:00");
        totalTimeLabel.setName("totalTimeLabel");
        totalTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    
        summaryPanel.add(totalCaloriesLabel);
        summaryPanel.add(totalTimeLabel);
    
        return summaryPanel;
    }
    
    // Update Summary Panel
    private void updateSummaryPanel(DefaultTableModel tableModel, JPanel summaryPanel) {
        int totalCalories = 0;
        int totalSeconds = 0;
    
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            // Summing up calories
            totalCalories += (int) tableModel.getValueAt(i, 2);
    
            // Summing up duration (convert Time object to seconds)
            Time duration = (Time) tableModel.getValueAt(i, 1);
            totalSeconds += duration.getHours() * 3600 + duration.getMinutes() * 60 + duration.getSeconds();
        }
    
        // Calculate hours, minutes, and seconds
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
    
        // Update labels in the summary panel
        for (Component component : summaryPanel.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                if ("totalCaloriesLabel".equals(label.getName())) {
                    label.setText("Total Calories Burned: " + totalCalories);
                } else if ("totalTimeLabel".equals(label.getName())) {
                    label.setText(String.format("Total Time Spent: %02d:%02d:%02d", hours, minutes, seconds));
                }
            }
        }
    }
    
    // Create Button Panel with Summaries
    private JPanel createButtonPanel(DefaultTableModel tableModel, JTable fitnessTable, JPanel summaryPanel) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        // Add button
        JButton addButton = new JButton("Add Activity");
        addButton.addActionListener(e -> {
            addActivity(tableModel);
            if (summaryPanel != null) {
                updateSummaryPanel(tableModel, summaryPanel);
            }
        });

        // Edit button
        JButton editButton = new JButton("Edit Activity");
        editButton.addActionListener(e -> {
            editActivity(fitnessTable, tableModel, user); // Pass the User object
            if (summaryPanel != null) {
                updateSummaryPanel(tableModel, summaryPanel);
            }
        });

        // Delete button
        JButton deleteButton = new JButton("Delete Activity");
        deleteButton.addActionListener(e -> {
            deleteActivity(fitnessTable, tableModel, user);
            if (summaryPanel != null) {
                updateSummaryPanel(tableModel, summaryPanel);
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        return buttonPanel;
    }
    
    // Add Activity
    private void addActivity(DefaultTableModel tableModel) {
        JTextField nameField = createTextField("", "Activity Name");
        
        // Create Duration Panel
        JPanel durationPanel = new JPanel(new GridLayout(1, 3, 10, 0)); // Use GridLayout for better alignment
        
        // Duration Fields
        JTextField hoursField = createTextField("", "Hours");
        JTextField minutesField = createTextField("", "Minutes");
        JTextField secondsField = createTextField("", "Seconds");
        
        // Add Duration Fields to Panel
        durationPanel.add(hoursField);
        durationPanel.add(minutesField);
        durationPanel.add(secondsField);


        // Create Calories Field
        JTextField caloriesField = createTextField("", "Calories Burned");

        // Create Date Field
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");

        // Create Time Button
        JButton timeButton = new JButton("Select Time");
        TimePicker timePicker = new TimePicker();
        timePicker.setBorder(BorderFactory.createTitledBorder("Select Time"));

        // Action Listener for Time Button
        timeButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, timePicker, "Select Time", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                // Capture selected time
                String selectedTime = timePicker.getSelectedTime(); // Assuming TimePicker provides this method
                timeButton.setText(selectedTime); // Update button text with selected time
            }
        });

        // Displaying input fields in dialog
        Object[] message = {
            "", nameField,
            "", durationPanel,
            "", caloriesField,
            "Date", dateChooser,
            "", timeButton
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Activity", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            try {
                // Validate and parse input
                String name = nameField.getText().trim();
                if (name.isEmpty()) throw new IllegalArgumentException("Activity name is required.");

                // Parse Duration (hours, minutes, seconds)
                int hours = Integer.parseInt(hoursField.getText().trim());
                int minutes = Integer.parseInt(minutesField.getText().trim());
                int seconds = Integer.parseInt(secondsField.getText().trim());
                Time duration = new Time(hours, minutes, seconds);

                int calories = Integer.parseInt(caloriesField.getText().trim());
                if (calories <= 0) throw new IllegalArgumentException("Calories must be a positive number.");

                // Parse Date
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateChooser.getDate());
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH) + 1; // Calendar months are 0-based
                int year = calendar.get(Calendar.YEAR);
                KUDate activityDate = new KUDate(day, month, year);

                // Time validation
                String time = timeButton.getText();
                if (time.equals("Select Time")) {
                    throw new IllegalArgumentException("Time must be selected.");
                }

                // Create Activity object and add to table
                Activity activity = new Activity(name, duration, calories, activityDate, time);
                tableModel.addRow(new Object[]{
                    activity.getName(), activity.getDuration(), activity.getCaloriesBurned(),
                    activity.getActivityDate(), activity.getTime(), activity.getStatus()
                });
                
                // Clear input fields
                nameField.setText("");
                hoursField.setText("");
                minutesField.setText("");
                secondsField.setText("");
                caloriesField.setText("");
                dateChooser.setDate(null);
                timeButton.setText("Select Time");
                
                user.addActivity(activity); // Sync with user activity list
                JOptionPane.showMessageDialog(this, "Activity added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Invalid input!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Edit Activity (Placeholder, functionality to be implemented)
    private void editActivity(JTable fitnessTable, DefaultTableModel tableModel, User user) {
        int selectedRow = fitnessTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "No activity selected for editing.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        // Retrieve current values
        String currentName = (String) tableModel.getValueAt(selectedRow, 0);
        Time currentDuration = (Time) tableModel.getValueAt(selectedRow, 1);
        int currentCalories = (Integer) tableModel.getValueAt(selectedRow, 2);
        KUDate currentDate = (KUDate) tableModel.getValueAt(selectedRow, 3);
        String currentTime = (String) tableModel.getValueAt(selectedRow, 4);
    
        // Create input fields pre-filled with current values
        JTextField nameField = new JTextField(currentName);
        JTextField hoursField = createTextField(String.valueOf(currentDuration.getHours()), "Hours");
        JTextField minutesField = createTextField(String.valueOf(currentDuration.getMinutes()), "Minutes");
        JTextField secondsField = createTextField(String.valueOf(currentDuration.getSeconds()), "Seconds");
        JTextField caloriesField = createTextField(String.valueOf(currentCalories), "Calories Burned");
    
        JDateChooser dateChooser = new JDateChooser();
        try {
            String date = currentDate.toString();
            java.util.Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            dateChooser.setDate(date2);
        } catch (Exception e) {
            System.out.println(e);
        }
    
        JButton timeButton = new JButton(currentTime);
        TimePicker timePicker = new TimePicker();
    
        timeButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, timePicker, "Select Time", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                timeButton.setText(timePicker.getSelectedTime());
            }
        });
    
        JPanel durationPanel = new JPanel(new FlowLayout());
        durationPanel.add(hoursField);
        durationPanel.add(minutesField);
        durationPanel.add(secondsField);
    
        Object[] message = {
            "Edit Activity Name:", nameField,
            "Edit Duration:", durationPanel,
            "Edit Calories Burned:", caloriesField,
            "Edit Date:", dateChooser,
            "Edit Time:", timeButton
        };
    
        int option = JOptionPane.showConfirmDialog(this, message, "Edit Activity", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                // Validate and parse input
                String newName = nameField.getText().trim();
                if (newName.isEmpty()) throw new IllegalArgumentException("Activity name is required.");
    
                int hours = Integer.parseInt(hoursField.getText().trim());
                int minutes = Integer.parseInt(minutesField.getText().trim());
                int seconds = Integer.parseInt(secondsField.getText().trim());
                Time newDuration = new Time(hours, minutes, seconds);
    
                int newCalories = Integer.parseInt(caloriesField.getText().trim());
                if (newCalories <= 0) throw new IllegalArgumentException("Calories must be positive.");
    
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateChooser.getDate());
                KUDate newDate = new KUDate(
                    calendar.get(Calendar.DAY_OF_MONTH),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.YEAR)
                );
    
                String newTime = timeButton.getText();
                if (newTime.equals("Select Time")) {
                    throw new IllegalArgumentException("Time must be selected.");
                }
    
                // Update table
                tableModel.setValueAt(newName, selectedRow, 0);
                tableModel.setValueAt(newDuration, selectedRow, 1);
                tableModel.setValueAt(newCalories, selectedRow, 2);
                tableModel.setValueAt(newDate, selectedRow, 3);
                tableModel.setValueAt(newTime, selectedRow, 4);
    
               // Sync with user activity list
                String oldActivityName = (String) tableModel.getValueAt(selectedRow, 0);
                user.updateActivity(oldActivityName, new Activity(newName, newDuration, newCalories, newDate, newTime));
                JOptionPane.showMessageDialog(this, "Activity updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Invalid Input!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteActivity(JTable fitnessTable, DefaultTableModel tableModel, User user) {
        int selectedRow = fitnessTable.getSelectedRow(); // Get the selected row
        if (selectedRow != -1) { 
            String activityName = (String) tableModel.getValueAt(selectedRow, 0); // Retrieve activity name from the selected row
    
            // Call removeActivity on the User instance
            if (user.removeActivity(activityName)) { // Assuming the User class has a removeActivity(String) method
                tableModel.removeRow(selectedRow); // Remove the row from the table
                JOptionPane.showMessageDialog(this, "Activity deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Activity not found in user's list.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No activity selected for deletion.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
      

    /**
     * Creates the Goals Panel.
     * @return JPanel representing the Goals view.
     */
    private JPanel createGoalPanel() {
        JPanel goalPanel = new JPanel(new BorderLayout());
        goalPanel.setBackground(new Color(255, 229, 204));
        goalPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // Title label
        JLabel title = new JLabel("Goals", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(62, 8, 76));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    
        // Table for displaying goals
        String[] columns = {"Goal", "Target", "Progress", "Start Date", "End Date", "Progress %", "Status"};
        DefaultTableModel goalTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Prevent direct editing of table cells
            }
        };
        JTable goalTable = new JTable(goalTableModel);
    
        // Add a progress bar renderer for the "Progress %" column
        goalTable.getColumn("Progress %").setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                int progressValue = 0;
                int targetValue = (int) table.getValueAt(row, 1);  // Get the target value for the current row
                int progress = (int) table.getValueAt(row, 2);  // Get the current progress for the current row
    
                // Calculate percentage
                if (targetValue != 0) {
                    progressValue = (int) ((double) progress / targetValue * 100);
                }
    
                JProgressBar progressBar = new JProgressBar(0, 100);
                progressBar.setValue(progressValue);
                progressBar.setString(progressValue + "%");
                progressBar.setStringPainted(true);
    
                // Set progress bar color based on progress
                if (progressValue < 30) {
                    progressBar.setForeground(Color.RED); // Low progress
                } else if (progressValue < 80) {
                    progressBar.setForeground(Color.CYAN); // Medium progress
                } else {
                    progressBar.setForeground(Color.BLUE); // High progress
                }
    
                return progressBar;
            }
        });
    
        JScrollPane goalScrollPane = new JScrollPane(goalTable);
    
        // Buttons for managing goals
        JPanel buttonPanel = createGoalButtonPanel(goalTableModel, goalTable);
    
        // Add components to the panel
        goalPanel.add(title, BorderLayout.NORTH);
        goalPanel.add(goalScrollPane, BorderLayout.CENTER);
        goalPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Load user's goals into the table
        user.getGoals().forEach(goal -> {
            int progressValue = (goal.getTargetValue() != 0) 
                ? (int) ((double) goal.getProgress() / goal.getTargetValue() * 100)
                : 0;

            goalTableModel.addRow(new Object[]{
                goal.getGoalDescription(),
                goal.getTargetValue(),
                goal.getProgress(),
                goal.getStartDate(),
                goal.getEndDate(),
                progressValue,
                goal.getStatus()
            });
        });
        
        return goalPanel;
    }    

    
    // Create button panel with additional functionality
    private JPanel createGoalButtonPanel(DefaultTableModel tableModel, JTable goalTable) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    
        // Add Goal Button
        JButton addButton = new JButton("Add Goal");
        addButton.addActionListener(e -> addGoal(tableModel));
    
        // Edit Goal Button
        JButton editButton = new JButton("Edit Selected Goal");
        editButton.addActionListener(e -> editGoal(goalTable, tableModel));

        // Delete Goal Button
        JButton deleteButton = new JButton("Delete Selected Goal");
        deleteButton.addActionListener(e -> deleteGoal(goalTable, tableModel));
    
        // Print Progress Report Button
        JButton printButton = new JButton("Print Progress Report");
        printButton.addActionListener(e -> printProgressReport(tableModel));
    
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(printButton);

        return buttonPanel;
    }
    
    // Add Goal Logic
    private void addGoal(DefaultTableModel tableModel) {
        JTextField goalField = new JTextField();
        JTextField targetField = new JTextField();
        JTextField progressField = new JTextField();
        JDateChooser startDatetf = new JDateChooser();
        JDateChooser endDatetf = new JDateChooser();
    
        Object[] message = {
            "Goal Description:", goalField,
            "Target Value:", targetField,
            "Current Progress:", progressField,
            "Start Date:", startDatetf,
            "End Date:", endDatetf
        };
    
        int option = JOptionPane.showConfirmDialog(this, message, "Add New Goal", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String goal = goalField.getText().trim();
                int target = Integer.parseInt(targetField.getText().trim());
                int progress = Integer.parseInt(progressField.getText().trim());
                
                startDatetf.setDateFormatString("yyyy-MM-dd");
                endDatetf.setDateFormatString("yyyy-MM-dd");

                // Parse Date
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDatetf.getDate());
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH) + 1; // Calendar months are 0-based
                int year = calendar.get(Calendar.YEAR);
                KUDate starDate = new KUDate(day, month, year);

                // Parse Date
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(endDatetf.getDate());
                int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
                int month2 = calendar2.get(Calendar.MONTH) + 1; // Calendar months are 0-based
                int year2 = calendar2.get(Calendar.YEAR);
                KUDate endDate = new KUDate(day2, month2, year2);
                
    
                if (goal.isEmpty()) throw new IllegalArgumentException("Goal description cannot be empty.");
                if (progress > target) throw new IllegalArgumentException("Progress cannot exceed target.");
    
                // Add new goal with pending status and progress value
                tableModel.addRow(new Object[]{goal, target, progress, starDate, endDate, progress/target*100, "Pending"});
                JOptionPane.showMessageDialog(this, "Goal added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Add goal to User
                Goal newGoal = new Goal(goal, target, progress, starDate, endDate);
                user.addGoal(newGoal);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Edit Goal Logic
    private void editGoal(JTable goalTable, DefaultTableModel tableModel) {
        int selectedRow = goalTable.getSelectedRow();
        if (selectedRow != -1) {
            String currentGoal = (String) tableModel.getValueAt(selectedRow, 0);
            int currentTarget = (int) tableModel.getValueAt(selectedRow, 1);
            int currentProgress = (int) tableModel.getValueAt(selectedRow, 2);
    
            JTextField goalField = new JTextField(currentGoal);
            JTextField targetField = new JTextField(String.valueOf(currentTarget));
            JTextField progressField = new JTextField(String.valueOf(currentProgress));
    
            Object[] message = {
                "Goal Description:", goalField,
                "Target Value:", targetField,
                "Current Progress:", progressField
            };
    
            int option = JOptionPane.showConfirmDialog(this, message, "Edit Goal", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String goal = goalField.getText().trim();
                    int target = Integer.parseInt(targetField.getText().trim());
                    int progress = Integer.parseInt(progressField.getText().trim());
    
                    if (goal.isEmpty()) throw new IllegalArgumentException("Goal description cannot be empty.");
                    if (progress > target) throw new IllegalArgumentException("Progress cannot exceed target.");
    
                    // Update goal details
                    tableModel.setValueAt(goal, selectedRow, 0);
                    tableModel.setValueAt(target, selectedRow, 1);
                    tableModel.setValueAt(progress, selectedRow, 2);
                    tableModel.setValueAt(progress, selectedRow, 4);
                    JOptionPane.showMessageDialog(this, "Goal updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No goal selected for editing.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteGoal(JTable goalTable, DefaultTableModel tableModel) {
        int selectedRow = goalTable.getSelectedRow();
        if (selectedRow != -1) {
            String goalName = (String) tableModel.getValueAt(selectedRow, 0);
    
            // Interact with the User class to remove the goal
            if (user.removeGoal(goalName)) { // Assuming `user` is an instance of the User class
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Goal deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Goal not found in user's list.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No goal selected for deletion.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    
    // Print Progress Report Logic
    private void printProgressReport(DefaultTableModel tableModel) {
        StringBuilder report = new StringBuilder("Progress Report:\n\n");
        StringBuilder fileContent = new StringBuilder("Goals Progress Report:\n\n");

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String goal = (String) tableModel.getValueAt(i, 0);
            int target = (int) tableModel.getValueAt(i, 1);
            int progress = (int) tableModel.getValueAt(i, 2);
            String status = tableModel.getValueAt(i, 3).toString();

            // Calculate percentage
            int progressPercentage = (int) ((double) progress / target * 100);

            // Text-based progress bar (e.g., [#####-----] 50%)
            String progressBar = "[" + "#".repeat(progressPercentage / 10) +
                                "-".repeat(10 - progressPercentage / 10) + "] " + progressPercentage + "%";

            // Add to the message and file content
            String entry = String.format(
                "Goal: %s | Target: %d | Progress: %d | Status: %s | Progress: %s\n",
                goal, target, progress, status, progressBar
            );
            report.append(entry);
            fileContent.append(entry);
        }

        // Save the report to a text file in a user-specific folder
        try {
            // Create a folder named with the user's ID and name
            String folderName = user.getId() + "_" + user.getName().replaceAll("\\s+", "_");
            File userFolder = new File(folderName);

            // Ensure the folder exists; create it if it doesn't
            if (!userFolder.exists()) {
                if (!userFolder.mkdir()) {
                    throw new IOException("Failed to create directory: " + userFolder.getAbsolutePath());
                }
            }

            // Create the report file inside the user-specific folder
            File reportFile = new File(userFolder, "ProgressReport.txt");
            FileWriter writer = new FileWriter(reportFile);

            // Write the report content to the file
            writer.write(fileContent.toString());
            writer.close();

            // Show confirmation dialog
            JOptionPane.showMessageDialog(
                this,
                "Progress report saved to: " + reportFile.getAbsolutePath(),
                "Progress Report Saved",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (IOException e) {
            // Handle exceptions related to file operations
            JOptionPane.showMessageDialog(
                this,
                "Failed to save the report to a file.\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    

    /**
     * Handles button actions for navigation and other operations.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        profileButton.setBackground(null);
        fitnessButton.setBackground(null);
        goalButton.setBackground(null);

        if (e.getSource() == profileButton) {
            cardLayout.show(cardPanel, "Profile");
            profileButton.setBackground(Color.CYAN);
        } else if (e.getSource() == fitnessButton) {
            cardLayout.show(cardPanel, "Fitness");
            fitnessButton.setBackground(Color.CYAN);
        } else if (e.getSource() == goalButton) {
            cardLayout.show(cardPanel, "Goals");
            goalButton.setBackground(Color.CYAN);
        } else if (e.getSource() == logoutButton) {
            Database.saveUsers(Main.getUsers());
            this.dispose(); // Close current frame
            SignIn SignInFrame = new SignIn();        // Redirect to login page
            SignInFrame.setVisible(true);
            SignInFrame.pack();
            SignInFrame.setLocationRelativeTo(null); 
        }
    }
}