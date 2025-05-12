import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

public class CoursesFrame extends MainFrame{

    // Define your course data here
    // Each array can be: { "Course Title", "Description", "image/path.png" }
    // Use placeholder paths for now
    public static ArrayList<String[]> allCourses = new ArrayList<>(List.of(
            new String[]{"Java Programming", "Master the fundamentals of Java.", "images/Java.png"}, // Placeholder paths
            new String[]{"Data Structures & Algorithms", "Essential concepts for coding interviews.", "images/ds.png"},
            new String[]{"Computer Networks (CCNA)", "Learn networking basics and protocols.", "images/ccna.jpg"},
            new String[]{"Database Programming", "Design and interact with databases.", "images/db.png"},
            new String[]{"Web Development (Frontend)", "Build interactive user interfaces.", "images/php.png"}
    ));


    private JFrame coursesFrame;
    private JPanel sidePanel;
    private JPanel topPanel;
    private JPanel mainPanel; // Panel where course cards will be added

    // Re-use the same buttons or define them again if they have different listeners
    // Defining again for clarity for this example, but could pass references if in a shared structure
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




    // --- New Method to Create a Course Card ---
    public static JPanel setCourseCard(String courseTitle, String description, String imagePath) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS)); // Arrange items vertically
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x352F44), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10) // Inner padding
        ));

        // Define card size (closer to square, leave some height for text/button)
        Dimension cardSize = new Dimension(400, 300); // Width, Height
        card.setPreferredSize(cardSize);
        card.setMinimumSize(cardSize);
        card.setMaximumSize(cardSize);
        card.setAlignmentX(Component.CENTER_ALIGNMENT); // Center content within the card (not strictly needed with BoxLayout.Y_AXIS unless combined with other layouts)


        // Image Placeholder
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(0xEAEAEA)); // Light gray placeholder background
        Dimension imageSize = new Dimension(300, 200); // Image area size (adjust as needed)
        imagePanel.setPreferredSize(imageSize);
        imagePanel.setMaximumSize(imageSize);
        imagePanel.setMinimumSize(imageSize);
        imagePanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the image panel

        // Add an actual image if available, otherwise it's just the colored panel
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                ImageIcon originalIcon = new ImageIcon(imagePath);
                // Scale the image to fit the imagePanel size
                Image scaledImage = originalIcon.getImage().getScaledInstance(imageSize.width, imageSize.height, Image.SCALE_SMOOTH);
                imagePanel.add(new JLabel(new ImageIcon(scaledImage)));
            } catch (Exception e) {
                // Handle image loading errors, maybe display a broken image icon or nothing
                System.err.println("Error loading image: " + imagePath + " - " + e.getMessage());
                // Optionally add a text label indicating no image
                imagePanel.add(new JLabel("No Image"));
            }
        } else {
            imagePanel.add(new JLabel("No Image")); // Display placeholder text if no path
        }


        // Course Title Label
        JLabel titleLabel = new JLabel(courseTitle);
        titleLabel.setFont(new Font("JetBrainsMono NFM Medium", Font.BOLD, 18)); // Slightly larger/bolder font for title
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center title

        // Course Description Label (optional, keep brief or use tooltip)
        JLabel descLabel = new JLabel("<html><body style='text-align: center;'>" + description + "</body></html>"); // Use HTML for centering text
        descLabel.setFont(new Font("JetBrainsMono NFM Medium", Font.PLAIN, 14)); // Smaller font for description
        descLabel.setForeground(Color.GRAY); // Gray text for description
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center description
        // Consider adding a tooltip for the full description if it's long
        descLabel.setToolTipText(description);
        descLabel.setPreferredSize(new Dimension(cardSize.width - 20, 40)); // Give it some preferred height


        // Button
        JButton viewBtn = new JButton("View Course");
        viewBtn.setForeground(Color.BLACK);
        viewBtn.setBackground(new Color(0xF5ECD5)); // Assuming this is a preferred button background
        viewBtn.setFocusPainted(false);
        viewBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewBtn.setAlignmentX(Component.CENTER_ALIGNMENT); // Center button
        // Optional: Apply specific button style
        // buttonStyle(viewBtn, Color.BLACK, new Color(0xF5ECD5), new Color(0xEAEAEA)); // If using the overloaded style

        // Add ActionListener to open details frame
        viewBtn.addActionListener(e -> {
            // Pass course data and the STATIC list of all lectures
            CourseDetailsFrame detailsFrame = new CourseDetailsFrame(
                    courseTitle,
                    description,
                    imagePath,
                    LecturesFrame.allLectures // Pass the static list from LecturesFrame
            );
            detailsFrame.setVisible(true); // Show the details window
        });


        // Add components to the card
        card.add(imagePanel); // Add the image placeholder panel
        card.add(Box.createVerticalStrut(10)); // Spacing
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(5)); // Spacing
        // card.add(descLabel); // Add description if desired
        // card.add(Box.createVerticalStrut(10)); // Spacing after description
        card.add(Box.createVerticalGlue()); // Push title/desc/img up, button down
        card.add(viewBtn);
        card.add(Box.createVerticalStrut(5)); // Spacing below button

        return card;
    }


    // Method to initialize and display the CoursesFrame GUI
    public CoursesFrame () {
        coursesFrame = new JFrame("LMS - Courses");
        coursesFrame.setSize(1200, 800);
        // Assuming icon.png exists, otherwise remove/comment out
        // coursesFrame.setIconImage(new ImageIcon("Images/icon.png").getImage());
        coursesFrame.setLocationRelativeTo(null);
        coursesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        coursesFrame.getContentPane().setLayout(new BorderLayout());
        coursesFrame.getContentPane().setBackground(new Color(0xF0F0F0)); // Set light gray background for content pane


        // --- Side Panel --- (Identical to LecturesFrame)
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(0xF5ECD5));
        sidePanel.setPreferredSize(new Dimension(200, 800));

        Color textColor = new Color(0x000000);
        Color activeColor = new Color(0xEAEAEA);
        Color inactiveColor = new Color(0xF5ECD5);
        Color hoverColor = new Color(0x6D5D6E);

        buttonStyle(home, textColor, activeColor, inactiveColor, hoverColor);
        buttonStyle(courses, textColor, activeColor, inactiveColor, hoverColor);
        buttonStyle(lectures, textColor, activeColor, inactiveColor, hoverColor);
        buttonStyle(logout, textColor, activeColor, inactiveColor, hoverColor);

        // Highlight the Courses button since we are on this page
        courses.setBackground(activeColor);
        courses.setForeground(Color.BLACK); // Or a distinct active color

        sidePanel.add(Box.createVerticalGlue());
        sidePanel.add(home);
        sidePanel.add(Box.createVerticalStrut(40));
        sidePanel.add(courses);
        sidePanel.add(Box.createVerticalStrut(40));
        sidePanel.add(lectures);
        sidePanel.add(Box.createVerticalStrut(40));
        sidePanel.add(logout);
        sidePanel.add(Box.createVerticalGlue());


        // --- Top Panel --- (Identical to LecturesFrame)
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBackground(new Color(0xF5ECD5));
        topPanel.setPreferredSize(new Dimension(1200, 50));

        JLabel logo = new JLabel("LMS");
        logo.setForeground(Color.BLACK);
        logo.setFont(new Font("Monospaced", Font.BOLD, 25));

        JTextField searchField = new JTextField("Find courses, trainers, etc.");
        searchField.setMaximumSize(new Dimension(300, 30));
        searchField.setForeground(Color.GRAY);
        searchField.setPreferredSize(new Dimension(300, 30));

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

        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(logo);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(searchField);
        topPanel.add(Box.createHorizontalStrut(20));


        // --- Navigation Handlers ---
        home.addActionListener(e -> {
            coursesFrame.dispose();
             new Assignments().initUI();
        });

        lectures.addActionListener(e -> {
            coursesFrame.dispose();
            new LecturesFrame();
        });

        courses.addActionListener(e -> {
            coursesFrame.dispose();
            new CoursesFrame();
        });

        logout.addActionListener(e -> {
            coursesFrame.dispose();
             new LogInFrame();
        });


        // --- Main Content Panel (for Course Cards) ---
        mainPanel = new JPanel();
        // Use a vertical box layout for stacking rows of cards
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(0xF0F0F0)); // Light gray background
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the grid area


        // Add Title
        JLabel titleLabel = new JLabel("Our Courses"); // Changed title
        titleLabel.setFont(new Font("JetBrainsMono NFM Medium", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the title

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(30)); // Space after title


        // --- Add Course Cards in a 2-Column Grid ---
        JPanel coursesGridPanel = new JPanel();
        // Use FlowLayout to arrange rows horizontally and wrap
        // Or use GridLayout/GridBagLayout for more strict grid control.
        // Let's use a JPanel with BoxLayout.Y_AXIS to stack rows,
        // and each row uses BoxLayout.X_AXIS for the 2 cards.
        // This provides more control over spacing and centering than FlowLayout.

        coursesGridPanel.setLayout(new BoxLayout(coursesGridPanel, BoxLayout.Y_AXIS));
        coursesGridPanel.setOpaque(false); // Make transparent
        coursesGridPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the entire grid panel

        // Calculate desired spacing
        int horizontalCardSpacing = 20; // Space between cards in a row
        int verticalRowSpacing = 20; // Space between rows of cards


        for (int i = 0; i < allCourses.size(); i += 2) {
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS)); // Arrange cards horizontally in this row
            rowPanel.setOpaque(false); // Make transparent
            rowPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the row panel within coursesGridPanel

            // Add first card in the row
            String[] course1Data = allCourses.get(i);
            JPanel card1 = setCourseCard(course1Data[0], course1Data[1], course1Data[2]);
            rowPanel.add(card1);

            // Check if there's a second card for this row
            if (i + 1 < allCourses.size()) {
                // Add spacing between cards
                rowPanel.add(Box.createHorizontalStrut(horizontalCardSpacing));

                // Add second card in the row
                String[] course2Data = allCourses.get(i + 1);
                JPanel card2 = setCourseCard(course2Data[0], course2Data[1], course2Data[2]);
                rowPanel.add(card2);
            } else {
                // If only one card in the last row, add a flexible space to push it left
                // or add a fixed space to make it look centered if preferred.
                // Adding glue pushes it left if the parent rowPanel is centered.
                // rowPanel.add(Box.createHorizontalGlue());

                // Alternatively, add a placeholder panel to maintain alignment if the row is centered
                JPanel emptyPlaceholder = new JPanel();
                emptyPlaceholder.setPreferredSize(card1.getPreferredSize()); // Match size of card1
                emptyPlaceholder.setMaximumSize(card1.getMaximumSize());
                emptyPlaceholder.setMinimumSize(card1.getMinimumSize());
                emptyPlaceholder.setOpaque(false); // Make it invisible
                rowPanel.add(Box.createHorizontalStrut(horizontalCardSpacing));
                rowPanel.add(emptyPlaceholder);
            }

            // Add the completed row panel to the coursesGridPanel
            coursesGridPanel.add(rowPanel);

            // Add spacing between rows, except after the last row
            if (i + 2 < allCourses.size()) {
                coursesGridPanel.add(Box.createVerticalStrut(verticalRowSpacing));
            }
        }

        // Add the grid panel to the main content panel
        mainPanel.add(coursesGridPanel);

        mainPanel.add(Box.createVerticalGlue()); // Push everything above up


        // Wrap the mainPanel in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        // --- Add Panels to Frame ---
        coursesFrame.add(sidePanel, BorderLayout.WEST);
        coursesFrame.add(topPanel, BorderLayout.NORTH);
        coursesFrame.add(scrollPane, BorderLayout.CENTER);


        // --- Set Global Font ---
        Font globalFont = new Font("JetBrainsMono NFM Medium", Font.PLAIN, 16);
        setGlobalFont(coursesFrame, globalFont);

        // Override font for specific elements after global font is set
        logo.setFont(new Font("Monospaced", Font.BOLD, 25));
        titleLabel.setFont(globalFont.deriveFont(Font.BOLD, 30)); // Keep the title font size


        // --- Make Frame Visible ---
        coursesFrame.setVisible(true);
    }

    public static void main(String[] args) {
        // Run the GUI creation on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CoursesFrame(); // Start with the Courses frame for testing
            }
        });
    }
}