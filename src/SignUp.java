import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import com.toedter.calendar.JDateChooser;


public class SignUp extends JFrame implements ActionListener {
    private JPanel left, right, rightCenter, rightBottom;
    private JButton signIn, signUp;
    private JLabel kuicon, leftH1, copyright, rightH1, nameLabel, emailLabel, bdLabel, phoneLabel, addressLabel, passLabel, signInLabel, separator1, separator2;
    private JTextField nametf, emailtf, phonetf, addresstf, passtf;
    private JDateChooser bdCalender;

    // Class constructor
    public SignUp() {
        initComponents();
    }

    private void initComponents() {
        // left SubPanel
        left = new JPanel();
        kuicon = new JLabel("", SwingConstants.CENTER);
        leftH1 = new JLabel("KU Fitness Tracker", SwingConstants.CENTER);
        copyright = new JLabel("copyright © KU Fitness Tracker. All rights reserved", SwingConstants.CENTER);
        // Right SubPanel
        right = new JPanel();
        rightCenter = new JPanel();
        rightBottom = new JPanel();

        rightH1 = new JLabel("Sign Up", SwingConstants.CENTER);
        nameLabel = new JLabel("Full Name", SwingConstants.LEFT);
        nametf = new JTextField();
        emailLabel = new JLabel("Email", SwingConstants.LEFT);
        emailtf = new JTextField();
        bdLabel = new JLabel("Date of Birth", SwingConstants.LEFT);
        bdCalender = new JDateChooser();
        phoneLabel = new JLabel("Phone Number", SwingConstants.LEFT);
        phonetf = new JTextField();
        addressLabel = new JLabel("Address", SwingConstants.LEFT); 
        addresstf = new JTextField();
        passLabel = new JLabel("Password", SwingConstants.LEFT);
        passtf = new JTextField();
        signUp = new JButton(" Sign Up  ");
        signInLabel = new JLabel("You have an account?    ", SwingConstants.LEFT);
        signIn = new JButton();
        separator1 = new JLabel("   "); // just a separator
        separator2 = new JLabel("   "); // just a separator

        // default settings for the main frame
        setTitle("Sign Up");
        setLayout(new GridLayout(1,2));
        setVisible(true);
        setMinimumSize(new Dimension(600, 400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // left SubPanel 
        left.setBackground(new Color(62, 8, 76));
        left.setLayout(new BorderLayout());

        kuicon.setIcon(new ImageIcon("logo//KU logo - 1.png"));
        kuicon.setAlignmentX(Component.CENTER_ALIGNMENT);
        kuicon.setBorder(BorderFactory.createEmptyBorder(20,0,-20,0));

        
        leftH1.setFont(new Font("Montserrat", 1, 24)); 
        leftH1.setForeground(new Color(255, 255, 255));
        leftH1.setBorder(BorderFactory.createEmptyBorder(-145,0,0,0));
        // leftH1.setAlignmentX(Component.CENTER_ALIGNMENT);

        copyright.setFont(new Font("Lato", 0, 14)); 
        copyright.setForeground(new Color(204, 204, 204));
        copyright.setBorder(BorderFactory.createEmptyBorder(0,20,30,20));
        copyright.setAlignmentX(Component.CENTER_ALIGNMENT);

        left.add(kuicon, BorderLayout.NORTH);
        left.add(leftH1, BorderLayout.CENTER);
        left.add(copyright, BorderLayout.SOUTH);

        // right SubPanel
        right.setMinimumSize(new Dimension(400, 250));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(new Color(134, 244, 238));
        rightCenter.setLayout(new BoxLayout(rightCenter, BoxLayout.Y_AXIS));
        rightCenter.setBackground(new Color(134, 244, 238));


        rightH1.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
        rightH1.setForeground(new Color(62, 8, 76));
        rightH1.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightH1.setBorder(BorderFactory.createEmptyBorder(30,0,30,0));

        rightCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightCenter.setBorder(BorderFactory.createEmptyBorder(0,20,25,20));

        nameLabel.setBackground(new Color(0,0,0));
        nameLabel.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

        nametf.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        nametf.setForeground(new Color(102, 102, 102));
        nametf.setAlignmentX(Component.LEFT_ALIGNMENT);

        emailLabel.setBackground(new Color(0,0,0));
        emailLabel.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailLabel.setBorder(BorderFactory.createEmptyBorder(15,0,10,0));

        emailtf.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        emailtf.setForeground(new Color(102, 102, 102));
        emailtf.setAlignmentX(Component.LEFT_ALIGNMENT);

        bdLabel.setBackground(new Color(0,0,0));
        bdLabel.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        bdLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        bdLabel.setBorder(BorderFactory.createEmptyBorder(15,0,10,0));

        bdCalender.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        bdCalender.setForeground(new Color(102, 102, 102));
        bdCalender.setAlignmentX(Component.LEFT_ALIGNMENT);

        phoneLabel.setBackground(new Color(0,0,0));
        phoneLabel.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        phoneLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        phoneLabel.setBorder(BorderFactory.createEmptyBorder(15,0,10,0));

        phonetf.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        phonetf.setForeground(new Color(102, 102, 102));
        phonetf.setAlignmentX(Component.LEFT_ALIGNMENT);

        addressLabel.setBackground(new Color(0,0,0));
        addressLabel.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        addressLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        addressLabel.setBorder(BorderFactory.createEmptyBorder(15,0,10,0));

        addresstf.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        addresstf.setForeground(new Color(102, 102, 102));
        addresstf.setAlignmentX(Component.LEFT_ALIGNMENT);

        passLabel.setBackground(new Color(0,0,0));
        passLabel.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
        passLabel.setText("Password");
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passLabel.setBorder(BorderFactory.createEmptyBorder(15,0,10,0));


        passtf.setForeground(new Color(102, 102, 102));
        passtf.setFont(new Font("Segoe UI", 0, 14)); // NOI18N
        passtf.setAlignmentX(Component.LEFT_ALIGNMENT);

        // separator1.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));

        signUp.setForeground(new Color(72, 24, 101));
        signUp.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        signUp.addActionListener(this);


        // signIn.setAlignmentX(Component.LEFT_ALIGNMENT);

        rightBottom.setLayout(new FlowLayout(0));
        rightBottom.setBorder(BorderFactory.createEmptyBorder(0,20,20,0));
        rightBottom.setBackground(new Color(134, 244, 238));


        signIn.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        signIn.setForeground(new Color(233, 28, 76));
        signIn.setText("SignIn");
        signIn.addActionListener(this);

        right.add(rightH1);
        rightCenter.add(nameLabel);
        rightCenter.add(nametf);
        rightCenter.add(emailLabel);
        rightCenter.add(emailtf);
        rightCenter.add(bdLabel);
        rightCenter.add(bdCalender);
        rightCenter.add(phoneLabel);
        rightCenter.add(phonetf);
        rightCenter.add(addressLabel);
        rightCenter.add(addresstf);
        rightCenter.add(passLabel);
        rightCenter.add(passtf);
        rightCenter.add(separator1);
        rightCenter.add(signUp);
        rightCenter.add(separator2);
        rightBottom.add(signInLabel);
        rightBottom.add(signIn);
        right.add(rightCenter);
        right.add(rightBottom);

        add(left);
        add(right);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == signIn){
            SignIn SignInFrame = new SignIn();
            SignInFrame.setVisible(true);
            SignInFrame.pack();
            SignInFrame.setLocationRelativeTo(null); 
            this.dispose();
        }

        if (e.getSource() == signUp) {
            boolean isEmailAvailable = true;
        
            // Check if the email already exists in the users list
            for (Account user : Main.getUsers()) {
                if (emailtf.getText().equals(((User) user).getEmail())) {
                    isEmailAvailable = false;
                    JOptionPane.showMessageDialog(null, "Email already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
        
            if (isEmailAvailable) {
                try {
                    // Get and validate input fields
                    String name = nametf.getText().trim();
                    String email = emailtf.getText().trim();
                    String password = passtf.getText().trim();
                    String phone = phonetf.getText().trim();
                    String address = addresstf.getText().trim();
                    int day = 0, month = 0, year = 0;
        
                    if (bdCalender.getDate() != null) {
                        // Extract day, month, and year from the calendar
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(bdCalender.getDate());
                        day = calendar.get(Calendar.DAY_OF_MONTH);
                        month = calendar.get(Calendar.MONTH) + 1; // Months are 0-based in Calendar
                        year = calendar.get(Calendar.YEAR);
                    }
        
                    // Validate required fields
                    if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || address.isEmpty() || bdCalender.getDate() == null) {
                        JOptionPane.showMessageDialog(null, "One or more input fields are empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Validate password length
                        if (password.length() < 8) {
                            throw new IllegalArgumentException("Password must be at least 8 characters long.");
                        }
        
                        // Create a new KUDate instance for the user's birthdate
                        KUDate birthDate = new KUDate(day, month, year);
        
                        // Create the new user and add it to the users list
                        User newUser = new User(name, email, password, birthDate, phone, address);
                        Main.getUsers().add(newUser);
        
                        // Save the updated users list to the database
                        Database.saveUsers(Main.getUsers());
                        System.out.println("User data saved successfully.");
        
                        // Show success message and reset input fields
                        JOptionPane.showMessageDialog(null, "Your account has been successfully signed up!", "Signed Up Successfully!", JOptionPane.INFORMATION_MESSAGE);
                        nametf.setText("");
                        emailtf.setText("");
                        passtf.setText("");
                        phonetf.setText("");
                        addresstf.setText("");
                        bdCalender.setDate(null);
                    }
                } catch (IllegalArgumentException ex) {
                    // Handle the IllegalArgumentException and show an error message dialog
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }             
}