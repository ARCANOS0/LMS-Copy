import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URI;
import java.util.HashMap;

public class MainFrame {

    protected HashMap<String, String> loginSessionInfo;
    private JFrame mainFrame;
    private JPanel sidePanel;
    private JPanel topPanel;
    private JPanel mainPanel;

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
                button.setForeground(Color.WHITE);
                button.setBackground(hover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(txt);
                button.setBackground(inactive);
            }
        });
    }

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

        courseLabel.setToolTipText(courseTitle);
        lectureLabel.setToolTipText(lectureTitle);

        JButton openBtn = new JButton("Open Lecture");
        openBtn.setForeground(Color.BLACK);
        openBtn.setBackground(new Color(0xF5ECD5));
        openBtn.setFocusPainted(false);
        openBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        openBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        openBtn.addActionListener(e -> {
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(lectureURL));
                } else {
                    JOptionPane.showMessageDialog(null, "Cannot open link. Desktop browsing not supported.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error opening link: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        card.add(courseLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(lectureLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(openBtn);

        return card;
    }

    public void initUI() {
        mainFrame = new JFrame("LMS");
        mainFrame.setSize(1200, 800);
        mainFrame.setIconImage(new ImageIcon("Images/icon.png").getImage());
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(new BorderLayout());
        mainFrame.getContentPane().setBackground(Color.WHITE);

        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(new Color(0xF5ECD5));
        sidePanel.setPreferredSize(new Dimension(200, 800));

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

        Color textColor = new Color(0x000000);
        Color activeColor = new Color(0xEAEAEA);
        Color inactiveColor = new Color(0xF5ECD5);
        Color hoverColor = new Color(0x6D5D6E);

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

        home.addActionListener(e -> {
            mainFrame.dispose();
            new MainFrame().initUI();
        });

        lectures.addActionListener(e -> {
            mainFrame.dispose();
            new LecturesFrame();
        });

        logout.addActionListener(e -> {
            mainFrame.dispose();
            new LogInFrame(loginSessionInfo);
        });

        // 👇 Your moved scrollable panel section
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(0xF0F0F0));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        String[][] lecturesData = {
                {"Java", "Intro to SDLC - Lecture 11", "https://batechu.com/storage/materials/1746561140681a6874a7896.pdf"},
                {"Data Structure", "Tree - Lecture 8", "https://batechu.com/storage/materials/174635688968174a995da6b.pdf"},
                {"CCNA", "Transport Layer - Lecture 9", "https://batechu.com/storage/materials/17462631296815dc597d302.pdf"},
                {"Operating Systems", "Concurrency and Deadlocks - Lecture 7 Notes and Examples", "https://example.com/os-lecture-7.pdf"}
        };

        mainPanel.add(Box.createVerticalStrut(20));

        JLabel recentLabel = new JLabel("Recent Lectures");
        recentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(recentLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        for (String[] lec : lecturesData) {
            JPanel wrapper = new JPanel();
            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.X_AXIS));
            wrapper.setOpaque(false);

            JPanel card = setLectureCard(lec[0], lec[1], lec[2]);

            wrapper.add(Box.createHorizontalGlue());
            wrapper.add(card);
            wrapper.add(Box.createHorizontalGlue());

            mainPanel.add(wrapper);
            mainPanel.add(Box.createVerticalStrut(20));
        }

        mainPanel.add(Box.createVerticalGlue());

        mainFrame.add(sidePanel, BorderLayout.WEST);
        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(scrollPane, BorderLayout.CENTER);

        Font globalFont = new Font("JetBrainsMono NFM Medium", Font.PLAIN, 16);
        setGlobalFont(mainFrame, globalFont);

        logo.setFont(new Font("Monospaced", Font.BOLD, 25));
        recentLabel.setFont(globalFont.deriveFont(Font.BOLD, 32));

        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame().initUI();
    }
}
