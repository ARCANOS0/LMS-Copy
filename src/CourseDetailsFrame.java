import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class CourseDetailsFrame extends JFrame {

    private String courseTitle;
    private String description;
    private String imagePath;
    private ArrayList<String[]> allLectures; // We'll pass the full list

    // Constructor
    public CourseDetailsFrame(String courseTitle, String description, String imagePath, ArrayList<String[]> allLectures) {
        this.courseTitle = courseTitle;
        this.description = description;
        this.imagePath = imagePath;
        this.allLectures = allLectures;

        setTitle("Details for: " + courseTitle);
        setSize(800, 600); // Adjust size as needed
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window

        initComponents(); // Build the UI
    }

    private void initComponents() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(10, 10)); // Use BorderLayout for main layout
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        contentPane.setBackground(new Color(0xF0F0F0)); // Light gray background
        setContentPane(contentPane);

        // --- Header Panel (Image, Title, Description) ---
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS)); // Stack header elements vertically
        headerPanel.setOpaque(false); // Make transparent
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align header content left

        // Image Panel (reuse logic from setCourseCard)
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(0xEAEAEA)); // Light gray placeholder background
        Dimension imageSize = new Dimension(250, 150); // Slightly larger image in details
        imagePanel.setPreferredSize(imageSize);
        imagePanel.setMaximumSize(imageSize);
        imagePanel.setMinimumSize(imageSize);
        imagePanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align image panel left

        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                // Use getResource to load image from the classpath (same as in CoursesFrame)
                // Assuming images are in src/main/resources/images/
                java.net.URL imageUrl = CourseDetailsFrame.class.getResource("/" + imagePath);

                if (imageUrl != null) {
                    ImageIcon originalIcon = new ImageIcon(imageUrl);
                    Image scaledImage = originalIcon.getImage().getScaledInstance(imageSize.width, imageSize.height, Image.SCALE_SMOOTH);
                    imagePanel.add(new JLabel(new ImageIcon(scaledImage)));
                } else {
                    System.err.println("Image resource not found: " + imagePath);
                    imagePanel.add(new JLabel("Image Not Found"));
                }
            } catch (Exception e) {
                System.err.println("Error processing image: " + imagePath + " - " + e.getMessage());
                imagePanel.add(new JLabel("Image Error"));
            }
        } else {
            imagePanel.add(new JLabel("No Image Path"));
        }

        // Title Label
        JLabel titleLabel = new JLabel(courseTitle);
        titleLabel.setFont(new Font("JetBrainsMono NFM Medium", Font.BOLD, 28)); // Larger font for title
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Description Label
        JLabel descLabel = new JLabel("<html><body style='width: 400px;'>" + description + "</body></html>"); // Wrap description text
        descLabel.setFont(new Font("JetBrainsMono NFM Medium", Font.PLAIN, 16));
        descLabel.setForeground(Color.DARK_GRAY);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        headerPanel.add(imagePanel);
        headerPanel.add(Box.createVerticalStrut(10)); // Spacing
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5)); // Spacing
        headerPanel.add(descLabel);
        headerPanel.add(Box.createVerticalGlue()); // Push header elements up

        contentPane.add(headerPanel, BorderLayout.NORTH); // Add header to the top

        // --- Lectures Panel ---
        JPanel lecturesListPanel = new JPanel();
        lecturesListPanel.setLayout(new BoxLayout(lecturesListPanel, BoxLayout.Y_AXIS));
        lecturesListPanel.setOpaque(false);
        lecturesListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lecturesTitleLabel = new JLabel("Lectures:");
        lecturesTitleLabel.setFont(new Font("JetBrainsMono NFM Medium", Font.BOLD, 22));
        lecturesTitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        lecturesListPanel.add(lecturesTitleLabel);
        lecturesListPanel.add(Box.createVerticalStrut(10)); // Spacing

        // Filter and add lectures
        boolean foundLectures = false;
        if (allLectures != null) {
            for (String[] lecture : allLectures) {
                // Compare course title from lecture data (index 0) with the current courseTitle
                if (lecture.length > 0 && lecture[0] != null && lecture[0].equals(this.courseTitle)) {
                    String lectureInfo = lecture[1]; // Lecture title/description
                    // You could create clickable buttons or panels here if needed
                    JLabel lectureLabel = new JLabel("- " + lectureInfo);
                    lectureLabel.setFont(new Font("JetBrainsMono NFM Medium", Font.PLAIN, 16));
                    lectureLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                    // Add a tooltip showing the URL (optional)
                    if (lecture.length > 2 && lecture[2] != null) {
                        lectureLabel.setToolTipText("Click to open: " + lecture[2]);
                        // Add a mouse listener to open the URL (similar to the button in LecturesFrame)
                        lectureLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        lectureLabel.setForeground(Color.BLUE.darker()); // Make it look clickable
                        lectureLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                try {
                                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                                        Desktop.getDesktop().browse(new URI(lecture[2]));
                                    } else {
                                        JOptionPane.showMessageDialog(CourseDetailsFrame.this, "Cannot open link. Desktop browsing not supported.", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    JOptionPane.showMessageDialog(CourseDetailsFrame.this, "Error opening link: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            public void mouseEntered(java.awt.event.MouseEvent evt) {
                                lectureLabel.setForeground(Color.RED); // Hover effect
                            }
                            public void mouseExited(java.awt.event.MouseEvent evt) {
                                lectureLabel.setForeground(Color.BLUE.darker());
                            }
                        });
                    }


                    lecturesListPanel.add(lectureLabel);
                    // lecturesListPanel.add(Box.createVerticalStrut(5)); // Spacing between lectures
                    foundLectures = true;
                }
            }
        }

        if (!foundLectures) {
            JLabel noLecturesLabel = new JLabel("No lectures found for this course yet.");
            noLecturesLabel.setFont(new Font("JetBrainsMono NFM Medium", Font.ITALIC, 16));
            noLecturesLabel.setForeground(Color.GRAY);
            noLecturesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            lecturesListPanel.add(noLecturesLabel);
        }


        // Wrap the lectures list in a ScrollPane
        JScrollPane lecturesScrollPane = new JScrollPane(lecturesListPanel);
        lecturesScrollPane.setBorder(BorderFactory.createTitledBorder("Associated Lectures")); // Add a border with title
        lecturesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        lecturesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        lecturesScrollPane.getVerticalScrollBar().setUnitIncrement(10); // Faster scrolling


        contentPane.add(lecturesScrollPane, BorderLayout.CENTER); // Add scroll pane to the center

        // --- Set Global Font (Optional, apply manually if specific fonts are needed) ---
        Font globalFont = new Font("JetBrainsMono NFM Medium", Font.PLAIN, 16);
        setGlobalFont(this, globalFont); // Apply font to this frame and its components

        // Override specific fonts after global set
        titleLabel.setFont(globalFont.deriveFont(Font.BOLD, 28));
        lecturesTitleLabel.setFont(globalFont.deriveFont(Font.BOLD, 22));
        // The lectureLabel font was set explicitly

        // Pack the frame to size components (optional, can help if preferred sizes are set correctly)
        // pack(); // Be careful with pack() and fixed sizes, it might override your size
    }

    // Reusable helper method for setting global font (can be made static and moved to a utility class)
    public static void setGlobalFont(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setGlobalFont(child, font);
            }
        }
    }


    // Optional main method for testing this frame in isolation

//    public static void main(String[] args) {
//        // Create some dummy data for testing
//        String testTitle = "Java Programming";
//        String testDesc = "A comprehensive course on Java fundamentals and advanced topics.";
//        String testImagePath = "images/Java.png"; // Use a path from your resources
//
//        // Need LecturesFrame.allLectures to be populated for this test
//        // Ideally, you'd pass the actual populated list.
//        ArrayList<String[]> dummyLectures = new ArrayList<>(List.of(
//             new String[]{"Java Programming", "Lecture 11: Introduction", "url1"},
//             new String[]{"Java Programming", "Lecture 2: Classes", "url2"},
//             new String[]{"Data Structures & Algorithms", "Lecture 1: Arrays", "url3"} // Should NOT appear
//        ));
//
//
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                // Pass the dummy data
//                new CourseDetailsFrame(testTitle, testDesc, testImagePath, dummyLectures).setVisible(true);
//            }
//        });
//    }
//
}