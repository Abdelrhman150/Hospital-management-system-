package Package4;

import Package1.User;
import Package2.AuthController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Login GUI
 */
public class LoginGUI extends JFrame {

    private AuthController authController;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginGUI() {
        authController = new AuthController();
        setTitle("Hospital Management - Login");
        setSize(480, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Gradient Background Panel
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(200, 230, 255);
                Color color2 = Color.WHITE;
                g2d.setPaint(new GradientPaint(0, 0, color1, 0, h, color2));
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setOpaque(false);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // --- CENTER SECTION (CARD) ---
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE); // Pure white card
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(30, 40, 30, 40) // Inner padding
        ));

        // Transparent wrapper panel to center the card
        JPanel cardWrapper = new JPanel(new GridBagLayout());
        cardWrapper.setOpaque(false);
        cardWrapper.add(centerPanel);

        JLabel lblLoginTitle = new JLabel("Login");
        lblLoginTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblLoginTitle.setForeground(new Color(46, 117, 180));
        lblLoginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(lblLoginTitle);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Inputs
        txtUsername = addStyledInputField(centerPanel, "👤", "Username");
        txtPassword = addStyledPasswordField(centerPanel, "🔒", "Password");

        // Login Button
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(new Color(46, 117, 180)); // Bold blue
        btnLogin.setContentAreaFilled(false);
        btnLogin.setOpaque(true);
        btnLogin.setFocusPainted(false);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(350, 45));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(e -> attemptLogin());

        centerPanel.add(btnLogin);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Links
        JPanel linksPanel = new JPanel(new BorderLayout());
        linksPanel.setBackground(Color.WHITE);
        linksPanel.setMaximumSize(new Dimension(350, 30));
        linksPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblForgot = new JLabel("<html><u>Forgot Password?</u></html>");
        lblForgot.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblForgot.setForeground(new Color(46, 117, 180));
        lblForgot.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblCreate = new JLabel("<html><u>Create Account</u></html>");
        lblCreate.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblCreate.setForeground(new Color(46, 117, 180));
        lblCreate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblCreate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new RegisterGUI().setVisible(true);
                dispose();
            }
        });

        linksPanel.add(lblForgot, BorderLayout.WEST);
        linksPanel.add(lblCreate, BorderLayout.EAST);
        centerPanel.add(linksPanel);

        mainPanel.add(cardWrapper, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JTextField addStyledInputField(JPanel parent, String iconText, String tooltip) {
        JPanel panel = createInputOutline(iconText);
        JTextField tf = new JTextField();
        tf.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10)); // Fixed clipping on Nimbus
        tf.setToolTipText(tooltip);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(tf, BorderLayout.CENTER);
        parent.add(panel);
        parent.add(Box.createRigidArea(new Dimension(0, 15)));
        return tf;
    }

    private JPasswordField addStyledPasswordField(JPanel parent, String iconText, String tooltip) {
        JPanel panel = createInputOutline(iconText);
        JPasswordField pf = new JPasswordField();
        pf.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10)); // Fixed clipping on Nimbus
        pf.setToolTipText(tooltip);
        pf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(pf, BorderLayout.CENTER);
        parent.add(panel);
        parent.add(Box.createRigidArea(new Dimension(0, 20)));
        return pf;
    }

    private JPanel createInputOutline(String iconText) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panel.setMaximumSize(new Dimension(350, 42));

        JLabel icon = new JLabel("  " + iconText + "  "); // Use emoji
        icon.setForeground(Color.DARK_GRAY);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        icon.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
        panel.add(icon, BorderLayout.WEST);
        return panel;
    }

    private void attemptLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both Username and Password", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = authController.login(username, password);

        if (user != null) {
            String message = "Login Successful!\n\n" +
                    "Welcome, " + user.getName() + "\n" +
                    "Login ID: " + user.getName() + "\n" +
                    "Official Email: " + user.getEmail() + "\n" +
                    "Role: [" + user.getRole() + "]";

            JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
            new DashboardGUI(user).setVisible(true); // Open Dashboard instead of MainMenu
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.", "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
        }
    }

    public static void main(String[] args) {
        MainMenuGUI.setModernLookAndFeel();
        SwingUtilities.invokeLater(() -> new LoginGUI().setVisible(true));
    }
}
