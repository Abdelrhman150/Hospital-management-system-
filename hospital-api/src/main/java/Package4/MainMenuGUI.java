package Package4;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Central Entry Point for the Hospital Management System.
 */
public class MainMenuGUI extends JFrame {

    public MainMenuGUI() {
        setTitle("Hospital Management System");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // --- Custom Background Panel (Bokeh + Gradient) ---
        JPanel mainPanel = new JPanel(null) { // Use null layout for precise positioning like the image
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();

                // 1. Background Gradient (Light Blue to Soft White)
                GradientPaint gp = new GradientPaint(0, 0, new Color(200, 230, 255), 0, h, new Color(245, 250, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);

                // 2. Bokeh/Bubble Effect
                g2d.setColor(new Color(255, 255, 255, 100));
                java.util.Random rnd = new java.util.Random(42);
                for (int i = 0; i < 25; i++) {
                    int size = 60 + rnd.nextInt(160);
                    int x = rnd.nextInt(w);
                    int y = rnd.nextInt(h);
                    g2d.fillOval(x - size / 2, y - size / 2, size, size);
                }
            }
        };
        add(mainPanel);

        // --- 1. TOP LOGO & TITLE ---
        JLabel lblLogo = new JLabel("📋");
        lblLogo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 45));
        lblLogo.setForeground(new Color(46, 117, 180));
        lblLogo.setBounds(475, 20, 60, 60);
        mainPanel.add(lblLogo);

        JLabel lblTitle = new JLabel("Hospital Management System", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 34));
        lblTitle.setForeground(new Color(46, 117, 180));
        lblTitle.setBounds(150, 80, 700, 50);
        mainPanel.add(lblTitle);

        // --- 2. LEFT BUTTONS ---
        RoundedButton btnLogin = new RoundedButton("Login", "👤", new Color(46, 117, 180));
        btnLogin.setBounds(50, 200, 230, 80);
        mainPanel.add(btnLogin);

        RoundedButton btnPatient = new RoundedButton("Patient Form", "📋", new Color(156, 39, 176));
        btnPatient.setBounds(50, 310, 230, 80);
        mainPanel.add(btnPatient);

        RoundedButton btnMedical = new RoundedButton("Medical Records", "🩺", new Color(0, 150, 136));
        btnMedical.setBounds(50, 420, 230, 80);
        mainPanel.add(btnMedical);

        // --- 3. CENTER DASHBOARD CARD ---
        JPanel dashCard = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255, 255, 255, 200));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.setColor(new Color(200, 220, 240));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            }
        };
        dashCard.setOpaque(false);
        dashCard.setBounds(330, 150, 340, 320);
        dashCard.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.add(dashCard);

        JLabel dashIcon = new JLabel("🏠");
        dashIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 100));
        dashIcon.setHorizontalAlignment(SwingConstants.CENTER);
        dashCard.add(dashIcon, BorderLayout.CENTER);

        RoundedButton btnDash = new RoundedButton("Dashboard", "🏠", new Color(70, 130, 180));
        btnDash.setPreferredSize(new Dimension(280, 70));
        dashCard.add(btnDash, BorderLayout.SOUTH);

        // --- 4. RIGHT BUTTONS ---
        RoundedButton btnRegister = new RoundedButton("Register", "➕", new Color(40, 167, 69));
        btnRegister.setBounds(720, 200, 230, 80);
        mainPanel.add(btnRegister);

        RoundedButton btnExit = new RoundedButton("Exit", "⏻", new Color(220, 53, 69));
        btnExit.setBounds(720, 310, 230, 80);
        mainPanel.add(btnExit);

        RoundedButton btnReports = new RoundedButton("Reports", "📊", new Color(255, 152, 0));
        btnReports.setBounds(720, 420, 230, 80);
        mainPanel.add(btnReports);

        // ACTIONS
        btnLogin.addActionListener(e -> navigateTo(new LoginGUI()));
        btnRegister.addActionListener(e -> navigateTo(new RegisterGUI()));
        btnPatient.addActionListener(e -> navigateTo(new PatientFormGUI()));
        btnDash.addActionListener(e -> JOptionPane.showMessageDialog(this, "Please Login First!"));
        btnExit.addActionListener(e -> System.exit(0));
        btnMedical.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Please Login first to access Medical Records.",
                "Login Required", JOptionPane.INFORMATION_MESSAGE);
            navigateTo(new LoginGUI());
        });
        btnReports.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Please Login first to access Reports.",
                "Login Required", JOptionPane.INFORMATION_MESSAGE);
            navigateTo(new LoginGUI());
        });
    }

    private void navigateTo(JFrame frame) {
        frame.setVisible(true);
        this.dispose();
    }

    /**
     * Custom Rounded Button with soft glow effect.
     */
    static class RoundedButton extends JButton {
        private Color backgroundColor;

        public RoundedButton(String text, String icon, Color bg) {
            super("<html><font face='Segoe UI Symbol'>" + icon + "</font>  " + text + "</html>");
            this.backgroundColor = bg;
            setFocusPainted(false);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 20));
            setContentAreaFilled(false);
            setBorderPainted(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Soft Glow/Shadow
            g2.setColor(new Color(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue(), 60));
            g2.fillRoundRect(3, 3, getWidth() - 3, getHeight() - 3, 25, 25);

            // Main Body
            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth() - 5, getHeight() - 5, 25, 25);

            super.paintComponent(g2);
            g2.dispose();
        
        }
    }

    /**
     * Helper to set Nimbus Look And Feel globally so colors render correctly on
     * Windows.
     */
    public static void setModernLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        setModernLookAndFeel();
        SwingUtilities.invokeLater(() -> new MainMenuGUI().setVisible(true));
    }
}
