import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.raven.swing.TimePicker;
import com.toedter.calendar.JDateChooser;

public class AdminPage extends JFrame implements ActionListener {
    private JPanel leftPanel, rightPanel, cardPanel, mainPanel2;
    private JButton activityButton, userButton, logoutButton;
    private CardLayout cardLayout, cardLayout2;
    private User selectedUser;
    private DefaultTableModel activityModel, goalsModel;


    public AdminPage() {
        initComponents();
    }

    private void initComponents() {
        // Frame settings
        setTitle("Admin Page - KU Fitness Tracker");
        setSize(1280, 720);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Left Sidebar
        setupSidebar();

        // Right Panel with Card Layout
        setupCardPanel();

        // Add Panels to Frame
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void setupSidebar() {
        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(62, 8, 76));
        leftPanel.setPreferredSize(new Dimension(250, getHeight()));

        // Sidebar Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(new Color(62, 8, 76));

        JLabel kuLogo = new JLabel(new ImageIcon("logo\\KU logo - 2.jpg"));
        kuLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        kuLogo.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        activityButton = createSidebarButton("Activity Management");
        userButton = createSidebarButton("User Management");
        logoutButton = createSidebarButton("Logout");

        buttonsPanel.add(kuLogo);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonsPanel.add(userButton);
        buttonsPanel.add(activityButton);
        buttonsPanel.add(Box.createVerticalGlue());

        leftPanel.add(buttonsPanel, BorderLayout.NORTH);
        leftPanel.add(logoutButton, BorderLayout.SOUTH);
    }

    private void setupCardPanel() {
        rightPanel = new JPanel(new BorderLayout());
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add panels for different sections
        cardPanel.add(createUserPanel(), "User");
        cardPanel.add(createActivityManagementPage(), "Activity");
        rightPanel.add(cardPanel, BorderLayout.CENTER);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(null);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(this);
        button.setMaximumSize(new Dimension(200, 60));
        return button;
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title Label
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("User Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titlePanel.add(title);

        // Table for users
        String[] columnNames = {"Name", "Email", "Phone", "Address", "Birthdate"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable userTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(userTable);

        // Search Box
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        searchField.setBorder(BorderFactory.createTitledBorder("Search by Name: "));
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(e -> {
            try {
                searchUser(searchField.getText(), tableModel);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error occurred while searching users: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        north.add(titlePanel);
        north.add(searchPanel);

        // Load users from the database
        List<User> userList = new ArrayList<>();
        try {
            userList = Main.getUsers();
            for (User user : userList) {
                tableModel.addRow(new Object[]{
                    user.getName(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getAddress(),
                    user.getBirthdate().toString()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "Error loading users: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton addUserButton = new JButton("Add User");
        JButton editUserButton = new JButton("Edit User");
        JButton deleteUserButton = new JButton("Delete User");

        // Add User Logic
        addUserButton.addActionListener(e -> {
            try {
                JTextField nameField = new JTextField();
                JTextField emailField = new JTextField();
                JTextField passwordField = new JTextField();
                JTextField phoneField = new JTextField();
                JTextField addressField = new JTextField();
                JDateChooser bdCalender = new JDateChooser();

                Object[] message = {
                    "Name:", nameField,
                    "Email:", emailField,
                    "Password:", passwordField,
                    "Phone Number:", phoneField,
                    "Address:", addressField,
                    "Date of Birth:", bdCalender
                };

                int option = JOptionPane.showConfirmDialog(panel, message, "Add User", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String name = nameField.getText().trim();
                    String email = emailField.getText().trim();
                    String password = passwordField.getText().trim();
                    String phone = phoneField.getText().trim();
                    String address = addressField.getText().trim();
                    KUDate birthdate = null;

                    if (bdCalender.getDate() != null) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(bdCalender.getDate());
                        birthdate = new KUDate(calendar.get(Calendar.DAY_OF_MONTH),
                                calendar.get(Calendar.MONTH) + 1,
                                calendar.get(Calendar.YEAR));
                    }

                    if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() &&
                            !phone.isEmpty() && !address.isEmpty() && birthdate != null) {
                        User newUser = new User(name, email, password, birthdate, phone, address);

                        // Add user to list and save to database
                        Main.addUser(newUser);
                        Database.saveUsers(Main.getUsers()); // Added try-catch for saving database

                        // Update the table
                        tableModel.addRow(new Object[]{
                            newUser.getName(),
                            newUser.getEmail(),
                            newUser.getPhoneNumber(),
                            newUser.getAddress(),
                            newUser.getBirthdate().toString()
                        });

                        JOptionPane.showMessageDialog(panel, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(panel, "All fields must be filled.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error occurred while adding user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        editUserButton.addActionListener(e -> {
            try {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow >= 0) {
                    // Get user details from the table
                    String selectedEmail = (String) tableModel.getValueAt(selectedRow, 1); // Assuming email is in column 1
                    User selectedUser = Main.getUsers().stream().filter(user -> user.getEmail().equals(selectedEmail)).findFirst().orElse(null);
        
                    if (selectedUser != null) {
                        JTextField nameField = new JTextField(selectedUser.getName());
                        JTextField emailField = new JTextField(selectedUser.getEmail());
                        JTextField passwordField = new JTextField(selectedUser.getPassword());
                        JTextField phoneField = new JTextField(selectedUser.getPhoneNumber());
                        JTextField addressField = new JTextField(selectedUser.getAddress());
                        JDateChooser dateChooser = new JDateChooser();
        
                        try {
                            String date = selectedUser.getBirthdate().toString();
                            java.util.Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                            dateChooser.setDate(date2);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(panel, "Error parsing date: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
        
                        Object[] message = {
                            "Name:", nameField,
                            "Email:", emailField,
                            "Password:", passwordField,
                            "Phone Number:", phoneField,
                            "Address:", addressField,
                            "Date of Birth:", dateChooser
                        };
        
                        int option = JOptionPane.showConfirmDialog(panel, message, "Edit User", JOptionPane.OK_CANCEL_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            selectedUser.setName(nameField.getText().trim());
                            selectedUser.setEmail(emailField.getText().trim());
                            selectedUser.setPassword(passwordField.getText().trim());
                            selectedUser.setPhone(phoneField.getText().trim());
                            selectedUser.setAddress(addressField.getText().trim());
        
                            if (dateChooser.getDate() != null) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(dateChooser.getDate());
                                selectedUser.setBirthdate(new KUDate(
                                        calendar.get(Calendar.DAY_OF_MONTH),
                                        calendar.get(Calendar.MONTH) + 1,
                                        calendar.get(Calendar.YEAR)));
                            }
        
                            try {
                                Database.saveUsers(Main.getUsers()); // Save updated user list to the database
                                tableModel.setValueAt(selectedUser.getName(), selectedRow, 0);
                                tableModel.setValueAt(selectedUser.getPhoneNumber(), selectedRow, 2);
                                tableModel.setValueAt(selectedUser.getAddress(), selectedRow, 3);
                                tableModel.setValueAt(selectedUser.getBirthdate().toString(), selectedRow, 4);
        
                                JOptionPane.showMessageDialog(panel, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(panel, "Error saving changes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Please select a user to edit.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        
        
        deleteUserButton.addActionListener(e -> {
            try {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure you want to delete this user?", "Delete User", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        String selectedEmail = (String) tableModel.getValueAt(selectedRow, 1); // Assuming email is in column 1
                        User selectedUser = Main.getUsers().stream().filter(user -> user.getEmail().equals(selectedEmail)).findFirst().orElse(null);
        
                        if (selectedUser != null) {
                            Main.getUsers().remove(selectedUser); // Remove from user list
        
                            try {
                                Database.saveUsers(Main.getUsers()); // Save updated list to the database
                                tableModel.removeRow(selectedRow); // Remove from table
        
                                JOptionPane.showMessageDialog(panel, "User deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(panel, "Error saving changes to database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Please select a user to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        


        buttonPanel.add(addUserButton);
        buttonPanel.add(editUserButton);
        buttonPanel.add(deleteUserButton);

        panel.add(north, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ################################################### //
    // ################################################### //
    // ################################################### //

    private JPanel createActivityManagementPage() {   
        // Create a new CardLayout for the secondary panel
        cardLayout2 = new CardLayout();
        mainPanel2 = new JPanel(cardLayout2);
    
        // Add the default card
        mainPanel2.add(createDefaultCard(), "DefaultCard");
    
        // Add the activities management card
        mainPanel2.add(createActivitiesManagementCard(), "ActivitiesManagementCard");
    
        // Add the manage goals card
        mainPanel2.add(createManageGoalsCard(), "ManageGoalsCard");
    
        return mainPanel2;
    }
    

    // Default card: Search box, table of users, and two buttons
    private JPanel createDefaultCard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // Title Label
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Activities & Goals Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titlePanel.add(title);

        // User Table
        String[] columnNames = {"Name", "Email", "Phone", "Address", "Birthdate"};
        DefaultTableModel userModel = new DefaultTableModel(columnNames, 0);
        JTable userTable = new JTable(userModel);
        JScrollPane tableScrollPane = new JScrollPane(userTable);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Search Box
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        searchField.setBorder(BorderFactory.createTitledBorder("Search by Name: "));
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(e -> searchUser(searchField.getText(), userModel));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);  

        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        north.add(titlePanel);
        north.add(searchPanel);
        panel.add(north, BorderLayout.NORTH);
    
        // Populate user table with data (simulated here)
        List<User> userList = Main.getUsers(); // Replace with your method
        for (User user : userList) {
            userModel.addRow(new Object[]{
                    user.getName(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getAddress(),
                    user.getBirthdate()
            });
        }
    
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton manageActivitiesButton = new JButton("Manage Activities");
        JButton manageGoalsButton = new JButton("Manage Goals");
    
        // Action Listeners
        manageActivitiesButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                selectedUser = Main.getUsers().get(selectedRow);
                // Populate activities table
                loadActivitiesIntoTable(activityModel);
                cardLayout2.show(mainPanel2, "ActivitiesManagementCard");
            } else {
                JOptionPane.showMessageDialog(panel, "Please select a user to manage activities.");
            }
        });
    
        manageGoalsButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                selectedUser = Main.getUsers().get(selectedRow);
                // Populate goals table
                loadGoalsIntoTable(goalsModel);
                cardLayout2.show(mainPanel2, "ManageGoalsCard");
            } else {
                JOptionPane.showMessageDialog(panel, "Please select a user to manage goals.");
            }
        });
    
        buttonPanel.add(manageActivitiesButton);
        buttonPanel.add(manageGoalsButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    
        return panel;
    }    


    // Activities management card: Activities table and management buttons
    private JPanel createActivitiesManagementCard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // Title Label
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Manage Activities");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titlePanel.add(title);
    
        // Activities Table
        String[] columnNames = {"Activity Name", "Duration", "Calories Burned", "Date", "Time", "Status"};
        activityModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only allow editing of the "Status" column
                return column == 5;
            }
        };
        JTable activityTable = new JTable(activityModel);

        // Adding Combo Box Editor for the "Status" Column
        String[] statusOptions = {"Approved", "Rejected"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);

        // Add an ActionListener to update the status
        statusComboBox.addActionListener(e -> {
            int selectedRow = activityTable.getSelectedRow();
            if (selectedRow != -1) {
                // Get the selected status from the combo box
                String selectedStatus = (String) statusComboBox.getSelectedItem();
                
                // Update the status in the table model
                activityTable.setValueAt(selectedStatus, selectedRow, 5);

                // Get the name of the activity from the table (assuming it's in the first column)
                String activityName = (String) activityTable.getValueAt(selectedRow, 0);

                // Search for the activity in the user's list of activities
                for (Activity activity : selectedUser.getActivities()) {
                    // Check if the activity's name matches the one selected in the table
                    if (activity.getName().equals(activityName)) {
                        // Set the status of the activity
                        activity.setStatus(selectedStatus);
                        break; // Stop the loop once the activity is found and updated
                    }
                }
                // Update the status in the table
                activityTable.setValueAt(selectedStatus, selectedRow, 5);
            }
        });

        activityTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(statusComboBox));

        JScrollPane tableScrollPane = new JScrollPane(activityTable);
        panel.add(tableScrollPane, BorderLayout.CENTER);

    
        // Search Box
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        searchField.setBorder(BorderFactory.createTitledBorder("Search Activities"));
        JButton searchButton = new JButton("Search");
    
        searchButton.addActionListener(e -> searchActivity(searchField.getText(), activityModel));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
    
        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        north.add(titlePanel);
        north.add(searchPanel);
    
        panel.add(north, BorderLayout.NORTH);
    
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Activity");
        JButton editButton = new JButton("Edit Activity");
        JButton deleteButton = new JButton("Delete Activity");
        JButton backButton = new JButton("Back");
    
        // Button Actions
        addButton.addActionListener(e -> addActivity(activityModel));
        editButton.addActionListener(e -> editActivity(activityTable, activityModel));
        deleteButton.addActionListener(e -> deleteActivity(activityTable, activityModel));
        backButton.addActionListener(e -> cardLayout2.show(mainPanel2, "DefaultCard"));
    
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    
        return panel;
    }

    // Search Method
    private void searchActivity(String searchQuery, DefaultTableModel activityModel) {
        // First, clear the existing rows in the table
        activityModel.setRowCount(0);

        // Iterate over the list of activities (assuming 'selectedUser' is the currently selected user)
        for (Activity activity : selectedUser.getActivities()) {
            // Check if the activity name contains the search query (case-insensitive)
            if (activity.getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                // If there's a match, add the activity to the table model
                Object[] row = {
                    activity.getName(),
                    activity.getDuration(),
                    activity.getCaloriesBurned(),
                    activity.getActivityDate(),
                    activity.getTime(),
                    activity.getStatus()
                };
                activityModel.addRow(row);
            }
        }

        // If no activities match, you can show a message or handle accordingly
        if (activityModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No activities found matching the search query.");
        }
    }
    // Simulated methods for activity actions
    private void loadActivitiesIntoTable(DefaultTableModel model) {
        // Clear the table before loading data
        model.setRowCount(0);
    
        // Check if selectedUser is not null
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(null, "No user selected. Please select a user to view activities.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Load activities of the selected user into the table
        for (Activity activity : selectedUser.getActivities()) {
            model.addRow(new Object[]{
                activity.getName(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getActivityDate(),
                activity.getTime(),
                activity.getStatus()
            });
        }
    }
    
    
    private void addActivity(DefaultTableModel model) {
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
                model.addRow(new Object[]{
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
                
                selectedUser.addActivity(activity); // Sync with user activity list
                JOptionPane.showMessageDialog(this, "Activity added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Invalid input!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void editActivity(JTable table, DefaultTableModel model) {
        // Logic to edit an activity
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "No activity selected for editing.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        // Retrieve current values
        String currentName = (String) model.getValueAt(selectedRow, 0);
        Time currentDuration = (Time) model.getValueAt(selectedRow, 1);
        int currentCalories = (Integer) model.getValueAt(selectedRow, 2);
        KUDate currentDate = (KUDate) model.getValueAt(selectedRow, 3);
        String currentTime = (String) model.getValueAt(selectedRow, 4);
    
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
                model.setValueAt(newName, selectedRow, 0);
                model.setValueAt(newDuration, selectedRow, 1);
                model.setValueAt(newCalories, selectedRow, 2);
                model.setValueAt(newDate, selectedRow, 3);
                model.setValueAt(newTime, selectedRow, 4);
    
               // Sync with user activity list
                String oldActivityName = (String) model.getValueAt(selectedRow, 0);
                selectedUser.updateActivity(oldActivityName, new Activity(newName, newDuration, newCalories, newDate, newTime));
                JOptionPane.showMessageDialog(this, "Activity updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Invalid Input!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteActivity(JTable table, DefaultTableModel model) {
        int selectedRow = table.getSelectedRow(); // Get the selected row
        if (selectedRow != -1) { 
            String activityName = (String) model.getValueAt(selectedRow, 0); // Retrieve activity name from the selected row
    
            // Call removeActivity on the User instance
            if (selectedUser.removeActivity(activityName)) { // Assuming the User class has a removeActivity(String) method
                model.removeRow(selectedRow); // Remove the row from the table
                JOptionPane.showMessageDialog(this, "Activity deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Activity not found in user's list.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No activity selected for deletion.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private JPanel createManageGoalsCard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    
        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel title = new JLabel("Manage Goals");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titlePanel.add(title);
    
        // Table for displaying goals
        String[] columns = {"Goal", "Target", "Progress", "Start Date", "End Date", "Progress %", "Status"};
        goalsModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only "Status" column is editable
            }
        };
    
        JTable goalTable = new JTable(goalsModel);
        setupProgressColumn(goalTable);

        // Adding Combo Box Editor for the "Status" Column
        String[] statusOptions = {"Approved", "Rejected"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);

        // Add an ActionListener to update the status
        statusComboBox.addActionListener(e -> {
            int selectedRow = goalTable.getSelectedRow();
            if (selectedRow != -1) {
                // Get the selected status from the combo box
                String selectedStatus = (String) statusComboBox.getSelectedItem();
                
                // Update the status in the table model
                goalTable.setValueAt(selectedStatus, selectedRow, 6);

                // Get the name of the activity from the table (assuming it's in the first column)
                String goalName = (String) goalTable.getValueAt(selectedRow, 0);

                // Search for the activity in the user's list of activities
                for (Goal goal : selectedUser.getGoals()) {
                    // Check if the activity's name matches the one selected in the table
                    if (goal.getGoalDescription().equals(goalName)) {
                        // Set the status of the activity
                        goal.setStatus(selectedStatus);
                        break; // Stop the loop once the activity is found and updated
                    }
                }
                // Update the status in the table
                goalTable.setValueAt(selectedStatus, selectedRow, 5);
            }
        });

        goalTable.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(statusComboBox));
    
        // Scroll pane for the table
        JScrollPane goalScrollPane = new JScrollPane(goalTable);
    
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        searchField.setBorder(BorderFactory.createTitledBorder("Search Goals"));
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchGoal(searchField.getText(), goalsModel));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
    
        // North Panel
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.add(titlePanel);
        northPanel.add(searchPanel);
    
        panel.add(northPanel, BorderLayout.NORTH);
        panel.add(goalScrollPane, BorderLayout.CENTER);
    
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addGoalButton = new JButton("Add Goal");
        JButton editGoalButton = new JButton("Edit Goal");
        JButton deleteGoalButton = new JButton("Delete Goal");
        JButton backButton = new JButton("Back");
    
        addGoalButton.addActionListener(e -> addGoal(goalsModel));
        editGoalButton.addActionListener(e -> editGoal(goalTable, goalsModel));
        deleteGoalButton.addActionListener(e -> deleteGoal(goalTable, goalsModel));
        backButton.addActionListener(e -> cardLayout2.show(mainPanel2, "DefaultCard"));
    
        buttonPanel.add(addGoalButton);
        buttonPanel.add(editGoalButton);
        buttonPanel.add(deleteGoalButton);
        buttonPanel.add(backButton);
    
        panel.add(buttonPanel, BorderLayout.SOUTH);
    
        return panel;
    }
    
    private void setupProgressColumn(JTable table) {
        table.getColumn("Progress %").setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            int target = (int) table1.getValueAt(row, 1);
            int progress = (int) table1.getValueAt(row, 2);
            int progressPercentage = target > 0 ? (int) ((double) progress / target * 100) : 0;
    
            JProgressBar progressBar = new JProgressBar(0, 100);
            progressBar.setValue(progressPercentage);
            progressBar.setString(progressPercentage + "%");
            progressBar.setStringPainted(true);
            progressBar.setForeground(getProgressColor(progressPercentage));
            return progressBar;
        });
    
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Approved", "Rejected"});
        statusComboBox.addActionListener(e -> updateGoalStatus(table, statusComboBox));
        table.getColumn("Status").setCellEditor(new DefaultCellEditor(statusComboBox));
    }
    
    private Color getProgressColor(int progress) {
        if (progress < 30) {
            return Color.RED;
        } else if (progress < 80) {
            return Color.CYAN;
        } else {
            return Color.BLUE;
        }
    }
    
    private void updateGoalStatus(JTable table, JComboBox<String> statusComboBox) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String selectedStatus = (String) statusComboBox.getSelectedItem();
            String goalName = (String) table.getValueAt(selectedRow, 0);
    
            for (Goal goal : selectedUser.getGoals()) {
                if (goal.getGoalDescription().equals(goalName)) {
                    goal.setStatus(selectedStatus);
                    break;
                }
            }
    
            table.setValueAt(selectedStatus, selectedRow, 6);
            Database.saveUsers(Main.getUsers());
        }
    }
    
    private void searchGoal(String searchQuery, DefaultTableModel goalsModel) {
        goalsModel.setRowCount(0);
        selectedUser.getGoals().stream()
            .filter(goal -> goal.getGoalDescription().toLowerCase().contains(searchQuery.toLowerCase()))
            .forEach(goal -> goalsModel.addRow(new Object[]{
                goal.getGoalDescription(),
                goal.getTargetValue(),
                goal.getProgress(),
                goal.getStartDate(),
                goal.getEndDate(),
                goal.getStatus()
            }));
    
        if (goalsModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No goals found matching the search query.");
        }
    }
    
    private void loadGoalsIntoTable(DefaultTableModel model) {    
        // Clear the table before loading data
        model.setRowCount(0);
    
        // Check if selectedUser is not null
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(null, "No user selected. Please select a user to view activities.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Load activities of the selected user into the table
        for (Goal goal : selectedUser.getGoals()) {
            int progressPercentage = goal.getTargetValue() > 0
            ? (int) ((double) goal.getProgress() / goal.getTargetValue() * 100)
                : 0;
            model.addRow(new Object[]{
                goal.getGoalDescription(),
                goal.getTargetValue(),
                goal.getProgress(),
                goal.getStartDate(),
                goal.getEndDate(),
                progressPercentage,
                goal.getStatus()
            });
        };
    }
    
    private void addGoal(DefaultTableModel model) {
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
                model.addRow(new Object[]{goal, target, progress, starDate, endDate, progress/target*100, "Pending"});
                JOptionPane.showMessageDialog(this, "Goal added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Add goal to User
                Goal newGoal = new Goal(goal, target, progress, starDate, endDate);
                selectedUser.addGoal(newGoal);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void editGoal(JTable table, DefaultTableModel model) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String currentGoal = (String) model.getValueAt(selectedRow, 0);
            int currentTarget = (int) model.getValueAt(selectedRow, 1);
            int currentProgress = (int) model.getValueAt(selectedRow, 2);
    
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
                    model.setValueAt(goal, selectedRow, 0);
                    model.setValueAt(target, selectedRow, 1);
                    model.setValueAt(progress, selectedRow, 2);
                    model.setValueAt(progress, selectedRow, 4);
                    JOptionPane.showMessageDialog(this, "Goal updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No goal selected for editing.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void deleteGoal(JTable table, DefaultTableModel model) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String goalName = (String) model.getValueAt(selectedRow, 0);
    
            // Interact with the User class to remove the goal
            if (selectedUser.removeGoal(goalName)) { // Assuming `user` is an instance of the User class
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Goal deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Goal not found in user's list.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No goal selected for deletion.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    

    

    // Getter for the main panel
    public JPanel getMainPanel() {
        return mainPanel2;
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

    // Search Logic
    private void searchUser(String query, DefaultTableModel tableModel) {
        // Clear current table rows
        tableModel.setRowCount(0);

        // Load users from database
        List<User> userList = Main.getUsers();
        for (User user : userList) {
            if (user.getName().toLowerCase().contains(query.toLowerCase())) {
                tableModel.addRow(new Object[]{
                    user.getName(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getAddress(),
                    user.getBirthdate().toString()
                });
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == activityButton) {
            cardLayout.show(cardPanel, "Activity");
            cardLayout2.show(mainPanel2, "DefaultCard");
        } else if (e.getSource() == userButton) {
            cardLayout.show(cardPanel, "User");
        } else if (e.getSource() == logoutButton) {
            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to log out?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                Database.saveUsers(Main.getUsers());
                this.dispose();
                SignIn SignInFrame = new SignIn();
                SignInFrame.setVisible(true);
                SignInFrame.pack();
                SignInFrame.setLocationRelativeTo(null); 
            }
        }
    }
}