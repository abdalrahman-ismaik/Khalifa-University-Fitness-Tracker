// import javax.swing.*;
// import javax.swing.table.DefaultTableModel;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.util.Date;

// public class table extends JFrame {
//     private JTable activityTable;
//     private DefaultTableModel tableModel;
//     private JTextField nameField, durationField, caloriesField, dateField, timeField;
//     private JButton addButton, editButton, deleteButton;

//     public table() {
//         setTitle("Activity Tracker");
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setSize(800, 400);
//         setLayout(new BorderLayout());

//         // Table setup
//         String[] columnNames = {"Name", "Duration", "Calories Burned", "Date", "Time"};
//         tableModel = new DefaultTableModel(columnNames, 0);
//         activityTable = new JTable(tableModel);
//         JScrollPane scrollPane = new JScrollPane(activityTable);
//         add(scrollPane, BorderLayout.CENTER);

//         // Input fields
//         JPanel inputPanel = new JPanel(new GridLayout(2, 5, 5, 5));
//         nameField = new JTextField();
//         durationField = new JTextField();
//         caloriesField = new JTextField();
//         dateField = new JTextField();
//         timeField = new JTextField();

//         inputPanel.add(new JLabel("Name"));
//         inputPanel.add(new JLabel("Duration (HH:MM:SS)"));
//         inputPanel.add(new JLabel("Calories"));
//         inputPanel.add(new JLabel("Date (yyyy-mm-dd)"));
//         inputPanel.add(new JLabel("Time (HH:MM:SS)"));

//         inputPanel.add(nameField);
//         inputPanel.add(durationField);
//         inputPanel.add(caloriesField);
//         inputPanel.add(dateField);
//         inputPanel.add(timeField);

//         add(inputPanel, BorderLayout.NORTH);

//         // Buttons for actions
//         JPanel buttonPanel = new JPanel();
//         addButton = new JButton("Add");
//         editButton = new JButton("Edit");
//         deleteButton = new JButton("Delete");

//         buttonPanel.add(addButton);
//         buttonPanel.add(editButton);
//         buttonPanel.add(deleteButton);

//         add(buttonPanel, BorderLayout.SOUTH);

//         // Action listeners
//         addButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 addRecord();
//             }
//         });

//         editButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 editRecord();
//             }
//         });

//         deleteButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 deleteRecord();
//             }
//         });

//         activityTable.getSelectionModel().addListSelectionListener(e -> populateFields());

//         setVisible(true);
//     }

//     // Add record to table
//     private void addRecord() {
//         String name = nameField.getText();
//         String duration = durationField.getText();
//         String calories = caloriesField.getText();
//         String date = dateField.getText();
//         String time = timeField.getText();

//         if (name.isEmpty() || duration.isEmpty() || calories.isEmpty() || date.isEmpty() || time.isEmpty()) {
//             JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
//             return;
//         }

//         tableModel.addRow(new Object[]{name, duration, calories, date, time});
//         clearFields();
//     }

//     // Edit selected record
//     private void editRecord() {
//         int selectedRow = activityTable.getSelectedRow();
//         if (selectedRow == -1) {
//             JOptionPane.showMessageDialog(this, "Please select a record to edit!", "Error", JOptionPane.ERROR_MESSAGE);
//             return;
//         }

//         tableModel.setValueAt(nameField.getText(), selectedRow, 0);
//         tableModel.setValueAt(durationField.getText(), selectedRow, 1);
//         tableModel.setValueAt(caloriesField.getText(), selectedRow, 2);
//         tableModel.setValueAt(dateField.getText(), selectedRow, 3);
//         tableModel.setValueAt(timeField.getText(), selectedRow, 4);

//         clearFields();
//     }

//     // Delete selected record
//     private void deleteRecord() {
//         int selectedRow = activityTable.getSelectedRow();
//         if (selectedRow == -1) {
//             JOptionPane.showMessageDialog(this, "Please select a record to delete!", "Error", JOptionPane.ERROR_MESSAGE);
//             return;
//         }

//         tableModel.removeRow(selectedRow);
//         clearFields();
//     }

//     // Populate text fields with selected record
//     private void populateFields() {
//         int selectedRow = activityTable.getSelectedRow();
//         if (selectedRow == -1) return;

//         nameField.setText(tableModel.getValueAt(selectedRow, 0).toString());
//         durationField.setText(tableModel.getValueAt(selectedRow, 1).toString());
//         caloriesField.setText(tableModel.getValueAt(selectedRow, 2).toString());
//         dateField.setText(tableModel.getValueAt(selectedRow, 3).toString());
//         timeField.setText(tableModel.getValueAt(selectedRow, 4).toString());
//     }

//     // Clear all text fields
//     private void clearFields() {
//         nameField.setText("");
//         durationField.setText("");
//         caloriesField.setText("");
//         dateField.setText("");
//         timeField.setText("");
//     }

//     public static void main(String[] args) {
//         new table();
//     }
// }
