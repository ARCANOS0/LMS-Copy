import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Line2D; // Import Line2D
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

// Import for Graphics2D and Stroke
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;

public class LecturesFrame {

    public static ArrayList<String[]> allLectures = new ArrayList<>(List.of(
            new String[]{"Java", "Intro to SDLC - Lecture 11", "https://batechu.com/storage/materials/1746561140681a6874a7896.pdf"},
            new String[]{"Data Structure", "Tree - Lecture 8", "https://batechu.com/storage/materials/174635688968174a995da6b.pdf"},
            new String[]{"CCNA", "Transport Layer - Lecture 9", "https://batechu.com/storage/materials/17462631296815dc597d302.pdf"},
            new String[]{"Operating Systems", "Concurrency and DeadLocks - Lecture 7 Notes and Examples", "https://example.com/os-lecture-7.pdf"},
            new String[]{"Data Structure", "Queue - Lecture 7", "https://batechu.com/storage/materials/174517442668053f9aa229b.pdf"},
            new String[]{"Data Structure", "Sheet 7 - Solutions", "https://batechu.com/storage/materials/1745734466680dcb429e8c4.pdf"},
            new String[]{"Data Structure", "Assignment 1", "https://batechu.com/storage/materials/17462652636815e4af7cda9.pdf"},
            new String[]{"CCNA", "Sheet 9 - Solutions", "https://batechu.com/storage/materials/17463314666816e74aae4bd.pdf"},
            new String[]{"Database Programming 2", "Database Normalization - Lecture 9", "https://batechu.com/storage/materials/17460029406811e3fcecd42.pdf"},
            new String[]{"Java", "Java GUI - Lecture 10", "https://batechu.com/storage/materials/1745760794680e321a6f2d9.pdf"},
            new String[]{"Java", "Java Exceptions - Lecture 9", "https://batechu.com/storage/materials/1745760440680e30b86bc11.pdf"}
    ));

    private JFrame lectureFrame;
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

        Dimension fixedSize = new Dimension(600, 120); // Keep the card fixed size
        card.setMinimumSize(fixedSize);
        card.setPreferredSize(fixedSize);
        card.setMaximumSize(fixedSize);
        card.setAlignmentX(Component.LEFT_ALIGNMENT); // Card content aligns left

        JLabel courseLabel = new JLabel(courseTitle);
        JLabel lectureLabel = new JLabel(lectureTitle);

        // Set font specifically for card labels
        Font cardFont = new Font("JetBrainsMono NFM Medium", Font.PLAIN, 16);
        courseLabel.setFont(cardFont);
        lectureLabel.setFont(cardFont);

        // Add tooltips for full text on hover
        courseLabel.setToolTipText(courseTitle);
        lectureLabel.setToolTipText(lectureTitle);

        // Ensure labels take up full width
        courseLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        lectureLabel.setAlignmentX(Component.LEFT_ALIGNMENT);


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

        // Apply style to the open button if needed, perhaps using the overloaded method
        // buttonStyle(openBtn, Color.BLACK, new Color(0xF5ECD5), new Color(0xEAEAEA));


        card.add(courseLabel);
        card.add(Box.createVerticalStrut(5)); // Spacing
        card.add(lectureLabel);
        card.add(Box.createVerticalStrut(10)); // Spacing
        card.add(openBtn);


        return card;
    }


    // Inner class for drawing the timeline line and dot
    class LineDotPanel extends JPanel {
        private boolean isLast;
        private static final int DOT_SIZE = 12; // Slightly larger dot
        private static final int LINE_THICKNESS = 2; // Thicker line
        // Using colors from the theme
        private static final Color LINE_COLOR = new Color(0x6D5D6E); // Using hover color
        private static final Color DOT_COLOR = new Color(0x352F44); // Using border color
        private static final int PANEL_WIDTH = 40; // Fixed width for this panel

        public LineDotPanel(boolean isLast) {
            this.isLast = isLast;
            setOpaque(false); // Make background transparent

            // Allow height to be determined by layout, but suggest a minimum
            setPreferredSize(new Dimension(PANEL_WIDTH, DOT_SIZE + 10));
            setMinimumSize(new Dimension(PANEL_WIDTH, DOT_SIZE + 10));
            setMaximumSize(new Dimension(PANEL_WIDTH, Integer.MAX_VALUE)); // Allow flexible height
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Smooth edges

            int dotX = PANEL_WIDTH / 2;
            int dotY = getHeight() / 2; // Center dot vertically within the panel height

            g2d.setColor(LINE_COLOR);
            g2d.setStroke(new BasicStroke(LINE_THICKNESS));

            // Draw line from top to the point *before* the dot
            // Ensure line doesn't go below 0
            if (dotY - DOT_SIZE / 2 > 0) {
                g2d.draw(new Line2D.Double(dotX, 0, dotX, dotY - DOT_SIZE / 2));
            }


            // Draw the dot
            g2d.setColor(DOT_COLOR);
            g2d.fillOval(dotX - DOT_SIZE / 2, dotY - DOT_SIZE / 2, DOT_SIZE, DOT_SIZE);

            // Draw line from the point *after* the dot to the bottom (if not last)
            // Ensure line doesn't go above getHeight()
            if (!isLast) {
                if (dotY + DOT_SIZE / 2 < getHeight()) {
                    g2d.draw(new Line2D.Double(dotX, dotY + DOT_SIZE / 2, dotX, getHeight()));
                }
            }
        }

    }

    // Inner class to combine the line/dot panel and the lecture card
    class TimelineEntryPanel extends JPanel {
        public TimelineEntryPanel(JPanel card, boolean isLast) {
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setOpaque(false); // Transparent background

            // Add LineDotPanel
            add(new LineDotPanel(isLast));
            // Add spacing between line and card
            add(Box.createHorizontalStrut(20)); // Adjust spacing as needed
            // Add the lecture card
            add(card);

            // Ensure the panel's height is dictated by the card's preferred/maximum height
            setPreferredSize(new Dimension(getPreferredSize().width, card.getPreferredSize().height));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, card.getMaximumSize().height));
            setMinimumSize(new Dimension(getPreferredSize().width, card.getMinimumSize().height));
        }
    }


    // Method to initialize and display the MainFrame GUI
    public LecturesFrame () {
        lectureFrame = new JFrame("LMS");
        lectureFrame.setSize(1200, 800);
        // Assuming icon.png exists, otherwise remove/comment out
        // lectureFrame.setIconImage(new ImageIcon("Images/icon.png").getImage());
        lectureFrame.setLocationRelativeTo(null);
        lectureFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lectureFrame.getContentPane().setLayout(new BorderLayout());
        lectureFrame.getContentPane().setBackground(new Color(0xF0F0F0)); // Set light gray background for content pane


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
            lectureFrame.dispose();
             new MainFrame().initUI();
        });

        lectures.addActionListener(e -> {
            lectureFrame.dispose();
             new LecturesFrame();
        });

        logout.addActionListener(e -> {
            lectureFrame.dispose();
             new LogInFrame();
        });

        courses.addActionListener(e -> {
            lectureFrame.dispose();
             new CoursesFrame();
        });

        // --- Main Content Panel (for Lecture Cards) ---
        mainPanel = new JPanel();
        // Use a vertical box layout for cards
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(0xF0F0F0)); // Light gray background for the content area
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); // Add some horizontal padding to main scrollable area


        // Wrap the mainPanel in a JScrollPane to make it scrollable
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null); // Remove the scroll pane border
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Make scrolling faster
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Hide horizontal scrollbar




        mainPanel.add(Box.createVerticalStrut(30)); // Space at the top of the timeline section within mainPanel
        mainPanel.add(Box.createVerticalStrut(20)); // Space between title and first card


        // 👇 Loop through the *static* list to create cards
        for (int i = 0; i < allLectures.size(); i++) {
            String[] lec = allLectures.get(i);
            boolean isLast = (i == allLectures.size() - 1);

            JPanel card = setLectureCard(lec[0], lec[1], lec[2]);

            // Create a panel for the line/dot and the card
            TimelineEntryPanel entryPanel = new TimelineEntryPanel(card, isLast);
            entryPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the entry panel horizontally within the mainPanel

            mainPanel.add(entryPanel); // Add the entry panel directly to mainPanel
            if (!isLast) {
                mainPanel.add(Box.createVerticalStrut(20)); // Space between entries in mainPanel
            }
        }

        mainPanel.add(Box.createVerticalGlue()); // Push everything above up in the mainPanel
        mainPanel.add(Box.createVerticalStrut(30)); // Space at the bottom of mainPanel


        // --- Add Panels to Frame ---
        lectureFrame.add(sidePanel, BorderLayout.WEST);
        lectureFrame.add(topPanel, BorderLayout.NORTH);
        lectureFrame.add(scrollPane, BorderLayout.CENTER); // Add the scrollable content pane to the center

        // --- Set Global Font ---
        // Define your preferred global font
        Font globalFont = new Font("JetBrainsMono NFM Medium", Font.PLAIN, 16); // Example font
        setGlobalFont(lectureFrame, globalFont); // Apply the font to all components

        // Override font for specific elements if needed - this needs to be done *after* setGlobalFont
        logo.setFont(new Font("Monospaced", Font.BOLD, 25)); // Make logo font bigger/bolder
        // recentLabel font is set explicitly above the loop


        // --- Make Frame Visible ---
        lectureFrame.setVisible(true);
    }

    public static void main(String[] args) {
        // Run the GUI creation on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LecturesFrame();
            }
        });
    }
}