import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;



public class Dashboard extends MainFrame {




    protected HashMap<String, String> loginSessionInfo;
    private JPanel mainPanel;
    private JPanel sidePanel;
    private JPanel topPanel;
    private JButton addLectureBtn;
    private JButton removeLectureBtn;
    private JButton showLectureBtn;
    private JLabel welcomeLabel;

    public Color bgColor = new Color(0xF5ECD5);
    public Color txtColor = new Color(0x000000);
    public Color hoverColor = new Color(0xFFD0C7);

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

    Color textColor = new Color(0x000000);
    Color activeColor = new Color(0xEAEAEA);
    Color inactiveColor = new Color(0xF5ECD5);

    private List<String[]> lecturesList;

    public Dashboard() {
        lecturesList = new ArrayList<>();
        addLecture("Java", "Intro to SDLC - Lecture 11", "https://batechu.com/...");
        addLecture("Data Structure", "Tree - Lecture 8", "https://batechu.com/...");
        addLecture("CCNA", "Transport Layer - Lecture 9", "https://batechu.com/...");
        addLecture("Operating Systems", "Concurrency and Deadlocks - Lecture 7", "https://example.com/...");

        JFrame dashboard = new JFrame("Admin Dashboard");
        dashboard.setSize(1200, 800);
        dashboard.setLocationRelativeTo(null);
        dashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 20));
        buttonPanel.setMaximumSize(new Dimension(250, 200));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        addLectureBtn = new JButton("Add Lecture");
        removeLectureBtn = new JButton("Remove Lecture");
        showLectureBtn = new JButton("Show Lectures");

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
        sidePanel.setBackground(bgColor);
        sidePanel.setPreferredSize(new Dimension(200, 800));

        buttonStyle(home, textColor, activeColor, inactiveColor, hoverColor);
        buttonStyle(courses, textColor, activeColor, inactiveColor, hoverColor);
        buttonStyle(lectures, textColor, activeColor, inactiveColor, hoverColor);
        buttonStyle(logout, textColor, activeColor, inactiveColor, hoverColor);

        sidePanel.add(Box.createVerticalGlue());
        sidePanel.add(home);
        sidePanel.add(Box.createVerticalStrut(40));
        sidePanel.add(courses);
        sidePanel.add(Box.createVerticalStrut(40));
        sidePanel.add(lectures);
        sidePanel.add(Box.createVerticalStrut(40));
        sidePanel.add(logout);
        sidePanel.add(Box.createVerticalGlue());

        // Top panel
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBackground(bgColor);
        topPanel.setPreferredSize(new Dimension(1200, 50));

        JLabel logo = new JLabel("Admin Dashboard");
        logo.setForeground(Color.BLACK);
        logo.setFont(new Font("Monospaced", Font.BOLD, 20));

        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(logo);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(Box.createHorizontalStrut(20));

        dashboard.add(mainPanel, BorderLayout.CENTER);
        dashboard.add(sidePanel, BorderLayout.WEST);
        dashboard.add(topPanel, BorderLayout.NORTH);
        dashboard.setVisible(true);

        // ========= NAVIGATION HANDLERS =========
        home.addActionListener(e -> {
            dashboard.dispose();
            new MainFrame().initUI();
        });

        lectures.addActionListener(e -> {
            dashboard.dispose();
            new LecturesFrame();
        });

        logout.addActionListener(e -> {
            dashboard.dispose();
            new LogInFrame(loginSessionInfo);
        });
    }

    public List<String[]> getLectures() {
        return lecturesList;
    }

    public void addLecture(String course, String lecture, String url) {
        lecturesList.add(new String[]{course, lecture, url});
    }

    public boolean removeLecture(String lectureTitle) {
        return lecturesList.removeIf(lecture -> lecture[1].equals(lectureTitle));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Dashboard::new);
    }
}
