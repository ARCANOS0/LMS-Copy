import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URI;
import java.util.ArrayList; // Import ArrayList
import java.util.List;      // Import List

public class MainFrame {

    // Use a static list to hold all lecture data, accessible globally
    // Initialize it with the default lectures
    public static ArrayList<String[]> allLectures = new ArrayList<>(List.of(
            new String[]{"Java", "Intro to SDLC - Lecture 11", "https://batechu.com/storage/materials/1746561140681a6874a7896.pdf"},
            new String[]{"Data Structure", "Tree - Lecture 8", "https://batechu.com/storage/materials/174635688968174a995da6b.pdf"},
            new String[]{"CCNA", "Transport Layer - Lecture 9", "https://batechu.com/storage/materials/17462631296815dc597d302.pdf"},
            new String[]{"Operating Systems", "Concurrency and Deadlocks - Lecture 7 Notes and Examples", "https://example.com/os-lecture-7.pdf"}
    ));

    private JFrame mainFrame;
    private JPanel sidePanel;
    private JPanel topPanel;
    private JPanel mainPanel; // Panel where cards will be added

    public JButton home = new JButton("Home") {
        @Override
        public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, super.getPreferredSize().height);
        }
    };

    public JButton lectures = new JButton("Lectures") {
        @Override
        public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, super.getPreferredSize().height);
        }
    };

    public JButton courses = new JButton("Courses") {
        @Override
        public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, super.getPreferredSize().height);
        }
    };

    public JButton logout = new JButton("Logout") {
        @Override
        public Dimension getMaximumSize() {
            return new Dimension(Integer.MAX_VALUE, super.getPreferredSize().height);
        }
    };

    public static void buttonStyle(JButton button, Color txt, Color active, Color inactive, Color hover) {
        button.setFont(new Font("JetBrainsMono NFM Medium", Font.BOLD, 17));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setBorderPainted(false);
        button.setFocusable(false);
        button.setBackground(inactive);
        button.setForeground(txt);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.WHITE); // Example hover effect
                button.setBackground(hover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(txt);
                button.setBackground(inactive);
            }
        });
    }

    // Overloaded buttonStyle for main content buttons
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


    public static void setGlobalFont(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setGlobalFont(child, font);
            }
        }
    }

    public static JPanel setLectureCard(String courseTitle, String lectureTitle, String lectureURL) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x352F44), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        Dimension fixedSize = new Dimension(600, 120);
        card.setMinimumSize(fixedSize);
        card.setPreferredSize(fixedSize);
        card.setMaximumSize(fixedSize);

        JLabel courseLabel = new JLabel(courseTitle);
        JLabel lectureLabel = new JLabel(lectureTitle);

        // Add tooltips for full text on hover
        courseLabel.setToolTipText(courseTitle);
        lectureLabel.setToolTipText(lectureTitle);


        JButton openBtn = new JButton("Open Lecture");
        openBtn.setForeground(Color.BLACK);
        openBtn.setBackground(new Color(0xF5ECD5)); // Assuming this is a preferred button background
        openBtn.setFocusPainted(false);
        openBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        openBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add ActionListener to the button
        openBtn.addActionListener(e -> {
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(lectureURL));
                } else {
                    JOptionPane.showMessageDialog(null, "Cannot open link. Desktop browsing not supported.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace(); // Log the exception
                JOptionPane.showMessageDialog(null, "Error opening link: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        card.add(courseLabel);
        card.add(Box.createVerticalStrut(5)); // Spacing
        card.add(lectureLabel);
        card.add(Box.createVerticalStrut(10)); // Spacing
        card.add(openBtn);


        return card;
    }

    // Method to initialize and display the MainFrame GUI
    public void initUI() {
        mainFrame = new JFrame("LMS");
        mainFrame.setSize(1200, 800);
        // Assuming icon.png exists, otherwise remove/comment out
        // mainFrame.setIconImage(new ImageIcon("Images/icon.png").getImage());
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(new BorderLayout());
        mainFrame.getContentPane().setBackground(Color.WHITE); // Set background for content pane

        // --- Side Panel ---
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(0xF5ECD5)); // Use a color constant or define one
        sidePanel.setPreferredSize(new Dimension(200, 800)); // Fixed width

        Color textColor = new Color(0x000000); // Text color
        Color activeColor = new Color(0xEAEAEA); // Color when button is active/selected
        Color inactiveColor = new Color(0xF5ECD5); // Color when button is inactive
        Color hoverColor = new Color(0x6D5D6E); // Hover color

        buttonStyle(home, textColor, activeColor, inactiveColor, hoverColor);
        buttonStyle(courses, textColor, activeColor, inactiveColor, hoverColor);
        buttonStyle(lectures, textColor, activeColor, inactiveColor, hoverColor);
        buttonStyle(logout, textColor, activeColor, inactiveColor, hoverColor);


        sidePanel.add(Box.createVerticalGlue()); // Push buttons to the center vertically
        sidePanel.add(home);
        sidePanel.add(Box.createVerticalStrut(40)); // Spacing between buttons
        sidePanel.add(courses);
        sidePanel.add(Box.createVerticalStrut(40));
        sidePanel.add(lectures);
        sidePanel.add(Box.createVerticalStrut(40));
        sidePanel.add(logout);
        sidePanel.add(Box.createVerticalGlue()); // Push buttons to the center vertically

        // --- Top Panel ---
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBackground(new Color(0xF5ECD5)); // Match side panel color or use a different one
        topPanel.setPreferredSize(new Dimension(1200, 50)); // Fixed height

        JLabel logo = new JLabel("LMS");
        logo.setForeground(Color.BLACK); // Logo color
        logo.setFont(new Font("Monospaced", Font.BOLD, 25)); // Logo font

        JTextField searchField = new JTextField("Find courses, trainers, etc.");
        searchField.setMaximumSize(new Dimension(300, 30)); // Set preferred size for the search field
        searchField.setForeground(Color.GRAY); // Placeholder text color
        searchField.setPreferredSize(new Dimension(300, 30)); // Explicitly set preferred size


        // Add FocusListener for placeholder text behavior
        searchField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Find courses, trainers, etc.")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setForeground(Color.GRAY);
                    searchField.setText("Find courses, trainers, etc.");
                }
            }
        });

        topPanel.add(Box.createHorizontalStrut(20)); // Left padding
        topPanel.add(logo);
        topPanel.add(Box.createHorizontalGlue()); // Push elements to the sides
        topPanel.add(searchField);
        topPanel.add(Box.createHorizontalStrut(20)); // Right padding


        // --- Navigation Handlers ---
        home.addActionListener(e -> {
            // If clicking home from MainFrame, just keep it open (or refresh)
            // If you intend to close and open a new one, your current code does that
             mainFrame.dispose();
             new MainFrame().initUI();

        });

        lectures.addActionListener(e -> {
            mainFrame.dispose();
            new LecturesFrame(); // Assuming LecturesFrame exists
        });

        courses.addActionListener(e ->{
            mainFrame.dispose();
            new CoursesFrame();
        });
        logout.addActionListener(e -> {
            mainFrame.dispose();
            new LogInFrame(); // Assuming LogInFrame exists
        });

        // --- Main Content Panel (for Lecture Cards) ---
        mainPanel = new JPanel();
        // Use a vertical box layout for cards
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(0xF0F0F0)); // Light gray background for the content area

        // Wrap the mainPanel in a JScrollPane to make it scrollable
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null); // Remove the scroll pane border
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Make scrolling faster

        // --- Add Lecture Cards ---
        mainPanel.add(Box.createVerticalStrut(20)); // Space at the top

        JLabel recentLabel = new JLabel("Recent Lectures");
        recentLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label
        mainPanel.add(recentLabel);
        mainPanel.add(Box.createVerticalStrut(20)); // Space after the label

        // 👇 Loop through the *static* list to create cards
        for (String[] lec : allLectures) { // <-- Use the static list
            JPanel wrapper = new JPanel(); // Use a wrapper panel to center the card
            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
            wrapper.setOpaque(false); // Make wrapper transparent

            JPanel card = setLectureCard(lec[0], lec[1], lec[2]); // Create the card

            wrapper.add(Box.createHorizontalGlue()); // Push card to center horizontally
            wrapper.add(card);
            wrapper.add(Box.createHorizontalGlue()); // Push card to center horizontally

            mainPanel.add(wrapper); // Add the centered card (via its wrapper)
            mainPanel.add(Box.createVerticalStrut(20)); // Space after the card
        }

        mainPanel.add(Box.createVerticalGlue()); // Push everything above up

        // --- Add Panels to Frame ---
        mainFrame.add(sidePanel, BorderLayout.WEST);
        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(scrollPane, BorderLayout.CENTER); // Add the scrollable content pane to the center

        // --- Set Global Font ---
        // Define your preferred global font
        Font globalFont = new Font("JetBrainsMono NFM Medium", Font.PLAIN, 16); // Example font
        setGlobalFont(mainFrame, globalFont); // Apply the font to all components

        // Override font for specific elements if needed
        logo.setFont(new Font("Monospaced", Font.BOLD, 25)); // Make logo font bigger/bolder
        recentLabel.setFont(globalFont.deriveFont(Font.BOLD, 32)); // Make recent label font bigger/bolder

        // --- Make Frame Visible ---
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        // Run the GUI creation on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new MainFrame().initUI();
        });
    }
}