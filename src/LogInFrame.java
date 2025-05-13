import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class LogInFrame extends UsersStorage implements ActionListener{

    private JFrame logInFrame;
    private JTextField username;
    private JPasswordField password;
    private JLabel messageLabel;
    private JButton logInButton;

    public LogInFrame() {
        super();
        System.out.println("LogInFrame is created");
        // Frame setup
        logInFrame = new JFrame("Login");
        logInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logInFrame.setSize(1000, 600);
        logInFrame.setLocationRelativeTo(null);
        logInFrame.setIconImage(new ImageIcon("Images/icon.png").getImage());

        // Root panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0x000));

        // Left panel (form)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(0xffffff));
        formPanel.setPreferredSize(new Dimension(450, 500));
        formPanel.setBorder(BorderFactory.createEmptyBorder(60, 40, 40, 40));

        JLabel title = new JLabel("Welcome Back !");
        title.setForeground(Color.BLACK);
        title.setFont(new Font("JetBrainsMono NFM Medium", Font.BOLD, 30));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        usernameLabel.setFont(new Font("JetBrainsMono NFM Medium", Font.PLAIN, 22));

        username = new JTextField();
        username.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        username.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordLabel.setFont(new Font("JetBrainsMono NFM Medium", Font.PLAIN, 17));

        password = new JPasswordField();
        password.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        password.setAlignmentX(Component.LEFT_ALIGNMENT);

        messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED);
        messageLabel.setFont(new Font("JetBrainsMono NFM Medium", Font.BOLD, 14));
        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        logInButton = new JButton("Log In");
        logInButton.setBackground(new Color(0xF5ECD5));
        logInButton.setForeground(new Color(0x352F44));
        logInButton.setFocusPainted(false);
        logInButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        logInButton.setMaximumSize(new Dimension(150, 35));
        logInButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logInButton.setFont(new Font("JetBrainsMono NFM Medium", Font.BOLD, 14));
        logInButton.addActionListener(this);

        formPanel.add(title);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(usernameLabel);
        formPanel.add(username);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(passwordLabel);
        formPanel.add(password);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(logInButton);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(messageLabel);

        // Right panel (image)
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(0xffffff));
        imagePanel.setLayout(new BorderLayout());

        ImageIcon placeholder = new ImageIcon("Images/loginPhoto.jpg");
        Image scaled = placeholder.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaled));
        imageLabel.setHorizontalAlignment(JLabel.LEFT);
        imagePanel.add(imageLabel, BorderLayout.LINE_END);

        // Add both to main panel
        mainPanel.add(formPanel, BorderLayout.WEST);
        mainPanel.add(imagePanel, BorderLayout.CENTER);


        logInFrame.setContentPane(mainPanel);
        logInFrame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logInButton) {
            String name = username.getText();
            String passwordField = String.valueOf(password.getPassword());

            // Check if the entered username exists
            if (usersInfo.containsKey(name)) {
                // Check if the entered password matches the stored password for that user
                if (usersInfo.get(name).equals(passwordField)) {
                    // Password matches - Login is successful

                    messageLabel.setText("Login Successful ✅"); // Set success message

                    logInFrame.dispose(); // Close the login frame

                    // *** NOW, determine which screen to open based on the username ***
                    if (name.equals("admin")) {
                        // If the logged-in user is 'admin', open the Dashboard
                        new Dashboard();
                    } else {
                        // For any other valid user, open the MainFrame
                        new Assignments().initUI();
                    }

                } else {
                    // Username exists, but password is incorrect
                    messageLabel.setText("Incorrect Password ❌");
                }
            } else {
                // Username does not exist in the storage
                messageLabel.setText("User Not Found ❌");
            }
        }
    }

    public static void setGlobalFont(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setGlobalFont(child, font);
            }
        }
    }

    public static void main(String[] args) {


    }
}
