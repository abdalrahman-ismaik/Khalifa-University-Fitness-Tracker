import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class SignIn extends JFrame implements ActionListener {
    private JPanel left, right, rightCenter, rightBottom;
    private JButton signIn, signUp;
    private JLabel kuicon, leftH1, copyright, rightH1, emailLabel, passLabel, signUpLabel, separator1, separator2;
    private JTextField emailtf;
    private JPasswordField passtf;

    // Class constructor
    public SignIn() {
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
        rightH1 = new JLabel("SignIn", SwingConstants.CENTER);
        emailLabel = new JLabel("Email", SwingConstants.LEFT);
        emailtf = new JTextField();
        passLabel = new JLabel("Password", SwingConstants.LEFT);
        passtf = new JPasswordField();
        signIn = new JButton(" SignIn  ");
        signUpLabel = new JLabel("Don't have an account?    ", SwingConstants.LEFT);
        signUp = new JButton();
        separator1 = new JLabel("   "); // just a separator
        separator2 = new JLabel("   "); // just a separator

        // default settings for the main frame
        setTitle("SignIn");
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
        copyright.setForeground(new Color(134, 244, 238));
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

        rightH1.setFont(new Font("Segoe UI", 1, 36)); // NOI18N
        rightH1.setForeground(new Color(62, 8, 76));
        rightH1.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightH1.setBorder(BorderFactory.createEmptyBorder(60,0,30,0));

        rightCenter.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightCenter.setBackground(new Color(134, 244, 238));
        rightCenter.setBorder(BorderFactory.createEmptyBorder(0,20,25,20));

        emailLabel.setBackground(new Color(0,0,0));
        emailLabel.setFont(new Font("Segoe UI", 0, 20)); // NOI18N
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailLabel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

        emailtf.setFont(new Font("Segoe UI", 0, 15)); // NOI18N
        emailtf.setForeground(new Color(102, 102, 102));
        emailtf.setAlignmentX(Component.LEFT_ALIGNMENT);


        passLabel.setBackground(new Color(0,0,0));
        passLabel.setFont(new Font("Segoe UI", 0, 20)); // NOI18N
        passLabel.setText("Password");
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passLabel.setBorder(BorderFactory.createEmptyBorder(15,0,10,0));


        passtf.setForeground(new Color(102, 102, 102));
        passtf.setFont(new Font("Segoe UI", 0, 15)); // NOI18N
        passtf.setAlignmentX(Component.LEFT_ALIGNMENT);

        // separator1.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));

        signIn.setForeground(new Color(72, 24, 101));
        signIn.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        signIn.addActionListener(this);


        // signIn.setAlignmentX(Component.LEFT_ALIGNMENT);

        rightBottom.setLayout(new FlowLayout(0));
        rightBottom.setBackground(new Color(134, 244, 238));
        rightBottom.setBorder(BorderFactory.createEmptyBorder(0,20,20,0));

        signUp.setFont(new Font("Segoe UI", 1, 12)); // NOI18N
        signUp.setForeground(new Color(233, 28, 76));
        signUp.setText("Sign Up");
        signUp.addActionListener(this);

        right.add(rightH1);
        rightCenter.add(emailLabel);
        rightCenter.add(emailtf);
        rightCenter.add(passLabel);
        rightCenter.add(passtf);
        rightCenter.add(separator1);
        rightCenter.add(signIn);
        rightCenter.add(separator2);
        rightBottom.add(signUpLabel);
        rightBottom.add(signUp);
        right.add(rightCenter);
        right.add(rightBottom);

        add(left);
        add(right);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signIn) {
            String email = emailtf.getText().trim();
            String password = new String(passtf.getPassword()).trim();
            
            if (email.equalsIgnoreCase("admin") && password.equals("admin2024")) {
                JOptionPane.showMessageDialog(this, "Welcome Admin!");
                new AdminPage(); // Redirect to Admin Page
                dispose(); // Close SignIn page
                return;
            }

            boolean userFound = false;
            for (int i = 0; i < Main.getUsers().size(); i++) {
                User user = Main.getUsers().get(i);
                if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                    Main.setIdx(i);
                    Main.setChecked(true);
                    userFound = true;
                    break;
                }
            }
    
            if (userFound) {
                dispose(); // Close the SignIn window
                new UserPage().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password!");
            }
        }


        if(e.getSource() == signUp){
            SignUp SignUpFrame = new SignUp();
            SignUpFrame.pack();
            SignUpFrame.setLocationRelativeTo(null); 
            this.dispose();
        }
    }
    
}
