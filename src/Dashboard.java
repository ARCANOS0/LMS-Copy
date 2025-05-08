import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends JFrame { // It's fine to extend JFrame if Dashboard is its own window

    private JPanel mainPanel;
    private JPanel sidePanel;
    private JPanel topPanel;
    private JButton addLectureBtn;
    private JButton removeLectureBtn; // Note: Functionality not implemented yet
    private JButton showLectureBtn;   // Note: Functionality not implemented yet
    private JLabel welcomeLabel;

    // Color constants
    public Color bgColor = new Color(0xF5ECD5);
    public Color txtColor = new Color(0x000000);
    public Color hoverColor = new Color(0xFFD0C7); // Main content button hover

    // Sidebar button colors
    Color textColor = new Color(0x000000);
    Color activeColor = new Color(0xEAEAEA);
    Color inactiveColor = new Color(0xF5ECD5);
    Color sideHoverColor = new Color(0x6D5D6E); // Sidebar button hover

    public JButton home = new JButton("Home") {
        public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, super.getPreferredSize().height);
        }
    };

    public JButton lectures = new JButton("Lectures") {
        public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, super.getPreferredSize().height);
        }
    };

    public JButton courses = new JButton("Courses") {
        public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, super.getPreferredSize().height);
        }
    };

    public JButton logout = new JButton("Logout") {
        public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, super.getPreferredSize().height);
        }
    };

    // REMOVE the instance variable lecturesData
    // private ArrayList<String[]> lecturesData;


    // Static helper for sidebar button style
    public static void buttonStyle(JButton button, Color txt, Color active, Color inactive, Color hover) {
        button.setFont(new Font("JetBrainsMono NFM Medium", Font.BOLD, 17));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setBorderPainted(false);
        button.setFocusable(false);
        button.setBackground(inactive);
        button.setForeground(txt);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.WHITE); // Hover text color
                button.setBackground(hover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(txt);
                button.setBackground(inactive);
            }
        });
    }

    // Static helper for main content button style
    public static void buttonStyle(JButton button, Color txt, Color bg, Color hover) {
        button.setFont(new Font("JetBrainsMono NFM Medium", Font.BOLD, 17));
        button.setBorderPainted(false);
        button.setFocusable(false);
        button.setBackground(bg);
        button.setForeground(txt);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x352F44), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bg);
            }
        });
    }

    public Dashboard() {
        // REMOVE initialization of instance lecturesData
        /*
        lecturesData = new ArrayList<>(List.of(
                new String[]{"Java", "Intro to SDLC - Lecture 11", "https://batechu.com/storage/materials/1746561140681a6874a7896.pdf"},
                new String[]{"Data Structure", "Tree - Lecture 8", "https://batechu.com/storage/materials/174635688968174a995da6b.pdf"},
                new String[]{"CCNA", "Transport Layer - Lecture 9", "https://batechu.com/storage/materials/17462631296815dc597d302.pdf"},
                new String[]{"Operating Systems", "Concurrency and Deadlocks - Lecture 7 Notes", "https://example.com/os-lecture-7.pdf"}
        ));
        */
        // The static list in MainFrame is now the shared data source.

        // The Dashboard frame itself
        JFrame dashboard = new JFrame("Admin Dashboard"); // Using a local variable for the frame
        dashboard.setSize(1200, 800);
        dashboard.setLocationRelativeTo(null);
        dashboard.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close for this window
        dashboard.getContentPane().setLayout(new BorderLayout());

        // Main content panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

        welcomeLabel = new JLabel("Welcome admin!");
        welcomeLabel.setFont(new Font("JetBrainsMono NFM Medium", Font.BOLD, 28));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalStrut(100));
        mainPanel.add(welcomeLabel);
        mainPanel.add(Box.createVerticalStrut(40));

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 20)); // 3 rows, 1 column, 20px vertical gap
        buttonPanel.setMaximumSize(new Dimension(250, 200)); // Limit the size of the button panel
        buttonPanel.setBackground(Color.WHITE); // Match main panel background
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button panel horizontally

        addLectureBtn = new JButton("Add Lecture");
        removeLectureBtn = new JButton("Remove Lecture"); // Functionality not implemented
        showLectureBtn = new JButton("Show Lectures");   // Functionality not implemented

        // Apply button styles for main content buttons
        buttonStyle(addLectureBtn, txtColor, bgColor, hoverColor);
        buttonStyle(removeLectureBtn, txtColor, bgColor, hoverColor);
        buttonStyle(showLectureBtn, txtColor, bgColor, hoverColor);

        buttonPanel.add(addLectureBtn);
        buttonPanel.add(removeLectureBtn);
        buttonPanel.add(showLectureBtn);

        mainPanel.add(buttonPanel);


        // Side panel
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(bgColor); // Use bgColor constant
        sidePanel.setPreferredSize(new Dimension(200, 800)); // Fixed width

        // Apply button styles for sidebar buttons
        buttonStyle(home, textColor, activeColor, inactiveColor, sideHoverColor); // Use sideHoverColor
        buttonStyle(courses, textColor, activeColor, inactiveColor, sideHoverColor);
        buttonStyle(lectures, textColor, activeColor, inactiveColor, sideHoverColor);
        buttonStyle(logout, textColor, activeColor, inactiveColor, sideHoverColor);


        sidePanel.add(Box.createVerticalGlue()); // Push buttons to center
        sidePanel.add(home);
        sidePanel.add(Box.createVerticalStrut(40));
        sidePanel.add(courses);
        sidePanel.add(Box.createVerticalStrut(40));
        sidePanel.add(lectures);
        sidePanel.add(Box.createVerticalStrut(40));
        sidePanel.add(logout);
        sidePanel.add(Box.createVerticalGlue()); // Push buttons to center

        // Top panel
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBackground(bgColor); // Use bgColor constant
        topPanel.setPreferredSize(new Dimension(1200, 50)); // Fixed height

        JLabel logo = new JLabel("Admin Dashboard"); // Changed logo text
        logo.setForeground(Color.BLACK);
        logo.setFont(new Font("Monospaced", Font.BOLD, 20));

        topPanel.add(Box.createHorizontalStrut(20)); // Left padding
        topPanel.add(logo);
        topPanel.add(Box.createHorizontalGlue()); // Push elements
        topPanel.add(Box.createHorizontalStrut(20)); // Right padding


        dashboard.add(mainPanel, BorderLayout.CENTER);
        dashboard.add(sidePanel, BorderLayout.WEST);
        dashboard.add(topPanel, BorderLayout.NORTH);
        dashboard.setVisible(true);

        // ========= NAVIGATION HANDLERS =========
        // Note: This setup disposes the current window and creates a new one.
        // This means the state of the *new* window will reflect the static data.
        home.addActionListener(e -> {
            dashboard.dispose();
            new MainFrame().initUI(); // Create and show a NEW MainFrame
        });

        lectures.addActionListener(e -> {
            dashboard.dispose();
            new LecturesFrame(); // Assuming LecturesFrame exists
        });

        logout.addActionListener(e -> {
            dashboard.dispose();
            new LogInFrame(); // Assuming LogInFrame exists
        });


        // Add Lecture Button Action
        addLectureBtn.addActionListener(e -> {
            openAddLectureDialog();
        });

        // Implement action for Show Lectures button (Optional - currently does nothing)
        // If you want this to show the lectures in Dashboard, you'd need
        // to clear mainPanel and re-add components, perhaps similar to MainFrame's initUI
        showLectureBtn.addActionListener(e -> {
            // Option 1: Just confirm the list size
            // JOptionPane.showMessageDialog(this, "Total Lectures: " + MainFrame.allLectures.size());
            // Option 2: Open MainFrame with updated list
            dashboard.dispose();
            new MainFrame().initUI(); // Go to MainFrame to see the list
        });

        // Implement action for Remove Lecture button (Optional - currently does nothing)
        removeLectureBtn.addActionListener(e -> {
            // You would need UI to select which lecture to remove
            JOptionPane.showMessageDialog(this, "Remove Lecture functionality not implemented yet.");
        });

    }

    // Function to open the Add Lecture dialog
    private void openAddLectureDialog() {
        JTextField courseField = new JTextField(20);
        JTextField titleField = new JTextField(20);
        JTextField urlField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5)); // Added gaps
        panel.add(new JLabel("Course Name:"));
        panel.add(courseField);
        panel.add(new JLabel("Lecture Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Lecture URL:"));
        panel.add(urlField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Add Lecture", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String course = courseField.getText().trim(); // Use trim()
            String title = titleField.getText().trim();
            String url = urlField.getText().trim();

            if (!course.isEmpty() && !title.isEmpty() && !url.isEmpty()) {
                addLecture(course, title, url);
                // Optional: Show success message
                JOptionPane.showMessageDialog(this, "Lecture Added Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Function to add a lecture to the shared list
    public void addLecture(String courseName, String lectureTitle, String url) {
        // Add to the static list in MainFrame
        MainFrame.allLectures.add(new String[]{courseName, lectureTitle, url});
        // System.out.println("Added lecture: " + courseName + " - " + lectureTitle); // Debugging
        // System.out.println("Current total lectures: " + MainFrame.allLectures.size()); // Debugging
    }


    public static void main(String[] args) {
        // Typically the main entry point would be something that shows Login or MainFrame
        // But you can run Dashboard directly for testing purposes.
//        SwingUtilities.invokeLater(Dashboard::new);
    }
}