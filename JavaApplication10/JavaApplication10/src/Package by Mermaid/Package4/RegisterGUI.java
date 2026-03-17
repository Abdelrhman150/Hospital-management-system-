package Package4;

import Package2.AuthController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Register GUI with gradient background.
 * Behaves exactly like the Registration section in LoginUI.java.
 */
public class RegisterGUI extends JFrame {

    private AuthController authController;
    private JTextField txtFullName;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JComboBox<String> comboRole;

    public RegisterGUI() {
        authController = new AuthController();
        setTitle("Hospital Management - Register");
        setSize(500, 650);
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
        centerPanel.setBackground(Color.WHITE); // Inner card
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(25, 40, 25, 40) // padding inside card
        ));

        // Wrapper
        JPanel cardWrapper = new JPanel(new GridBagLayout());
        cardWrapper.setOpaque(false);
        cardWrapper.add(centerPanel);

        // Home Button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setMaximumSize(new Dimension(350, 30));
        JButton btnHome = new JButton("<< Home");
        btnHome.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnHome.setForeground(Color.GRAY);
        btnHome.setContentAreaFilled(false);
        btnHome.setBorderPainted(false);
        btnHome.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHome.addActionListener(e -> {
            new MainMenuGUI().setVisible(true);
            this.dispose();
        });
        topPanel.add(btnHome, BorderLayout.WEST);
        centerPanel.add(topPanel);

        JLabel lblRegisterTitle = new JLabel("Register");
        lblRegisterTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblRegisterTitle.setForeground(new Color(40, 167, 69)); // matching green
        lblRegisterTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(lblRegisterTitle);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Input Fields (exactly matching LoginUI flow)
        txtFullName = addStyledInputField(centerPanel, "👤", "Full Name");
        
        // Role Selection directly in GUI
        String[] roles = { "Admin", "Doctor", "Nurse", "Secretary", "Patient" };
        comboRole = addStyledComboBox(centerPanel, "👔", roles);

        txtPassword = addStyledPasswordField(centerPanel, "🔒", "Password");
        txtConfirmPassword = addStyledPasswordField(centerPanel, "🔒", "Confirm Password");

        // Register Button
        JButton btnRegister = new JButton("Register");
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setBackground(new Color(40, 167, 69)); // bold green
        btnRegister.setContentAreaFilled(false);
        btnRegister.setOpaque(true);
        btnRegister.setFocusPainted(false);
        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegister.setMaximumSize(new Dimension(350, 45));
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegister.addActionListener(e -> attemptRegister());

        centerPanel.add(btnRegister);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Login Link
        JLabel lblLogin = new JLabel("<html><u>Already have an account? Login</u></html>");
        lblLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblLogin.setForeground(new Color(46, 117, 180));
        lblLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new LoginGUI().setVisible(true);
                dispose();
            }
        });
        centerPanel.add(lblLogin);

        mainPanel.add(cardWrapper, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JTextField addStyledInputField(JPanel parent, String iconText, String tooltip) {
        JPanel panel = createInputOutline(iconText);
        JTextField tf = new JTextField();
        tf.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        tf.setToolTipText(tooltip);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(tf, BorderLayout.CENTER);
        parent.add(panel);
        parent.add(Box.createRigidArea(new Dimension(0, 15)));
        return tf;
    }

    private JComboBox<String> addStyledComboBox(JPanel parent, String iconText, String[] items) {
        JPanel panel = createInputOutline(iconText);
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(Color.WHITE);
        combo.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        panel.add(combo, BorderLayout.CENTER);
        parent.add(panel);
        parent.add(Box.createRigidArea(new Dimension(0, 15)));
        return combo;
    }

    private JPasswordField addStyledPasswordField(JPanel parent, String iconText, String tooltip) {
        JPanel panel = createInputOutline(iconText);
        JPasswordField pf = new JPasswordField();
        pf.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        pf.setToolTipText(tooltip);
        pf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(pf, BorderLayout.CENTER);
        parent.add(panel);
        parent.add(Box.createRigidArea(new Dimension(0, 15)));
        return pf;
    }

    private JPanel createInputOutline(String iconText) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panel.setMaximumSize(new Dimension(350, 42));

        JLabel icon = new JLabel("  " + iconText + "  ");
        icon.setForeground(Color.DARK_GRAY);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 15));
        icon.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
        icon.setPreferredSize(new Dimension(45, 42));
        panel.add(icon, BorderLayout.WEST);
        return panel;
    }

    private void attemptRegister() {
        String fullName = txtFullName.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirm = new String(txtConfirmPassword.getPassword());
        String selectedRole = (String) comboRole.getSelectedItem();

        if (fullName.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Uses exact method from AuthController that generates ID and Email
        String result = authController.register(fullName, selectedRole, password);

        // Handling the result as requested
        if (result.contains("Registration Successful")) {
            JOptionPane.showMessageDialog(this, result, "Success", JOptionPane.INFORMATION_MESSAGE);
            new LoginGUI().setVisible(true);
            this.dispose();
        } else if (result.contains("already exists")) {
            JOptionPane.showMessageDialog(this, "Error: Username or Email already exists!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        MainMenuGUI.setModernLookAndFeel();
        SwingUtilities.invokeLater(() -> new RegisterGUI().setVisible(true));
    }
}
