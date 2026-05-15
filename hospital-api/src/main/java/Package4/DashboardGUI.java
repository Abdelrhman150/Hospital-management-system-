package Package4;

import Package1.staff.User;
import Package1.MedicalRecordDisplay.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;
import java.util.List;

/**
 * Dashboard GUI: Displays functions based on the user's role after login.
 * Inspired by the logic in LoginUI.java.
 */
public class DashboardGUI extends JFrame {

    private User currentUser;

    public DashboardGUI(User user) {
        this.currentUser = user;
        setTitle("Hospital Management - " + user.getRole() + " Dashboard");
        setSize(600, 500);
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
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- HEADER ---
        JPanel headerPanel = new JPanel(new GridLayout(3, 1));
        headerPanel.setOpaque(false);

        JLabel lblWelcome = new JLabel("Welcome, " + user.getName());
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblWelcome.setForeground(new Color(46, 117, 180));
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblRole = new JLabel("Role: [" + user.getRole() + "]");
        lblRole.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        lblRole.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblID = new JLabel("ID: " + user.getUsername());
        lblID.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblID.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(lblWelcome);
        headerPanel.add(lblRole);
        headerPanel.add(lblID);

        // --- BUTTONS PANEL (GRID) ---
        JPanel buttonsPanel = new JPanel(new GridLayout(0, 1, 15, 15));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setBorder(new EmptyBorder(30, 100, 30, 100));

        // Add role-based buttons
        addRoleButtons(buttonsPanel);

        // Logout Button at the bottom
        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> {
            new MainMenuGUI().setVisible(true);
            this.dispose();
        });

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        mainPanel.add(btnLogout, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void addRoleButtons(JPanel panel) {
        String role = currentUser.getRole().toLowerCase();

        switch (role) {
            case "admin":
                panel.add(createActionButton("Manage All Departments", "🏢"));
                panel.add(createActionButton("Manage Staff Accounts", "👥"));
                panel.add(createActionButton("System Settings", "⚙️"));
                panel.add(createActionButton("Generate Reports", "📊"));
                panel.add(createActionButton("Send Notifications", "🔔"));
                break;
            case "doctor":
                panel.add(createActionButton("View Patient Medical Records", "📋"));
                panel.add(createActionButton("Diagnose Patient (Doctor View)", "🩺"));
                panel.add(createActionButton("Add New Medical Record", "➕"));
                panel.add(createActionButton("Manage Appointments", "📅"));
                panel.add(createActionButton("Generate Reports", "📊"));
                break;
            case "nurse":
                panel.add(createActionButton("Monitor Patient Vitals", "📉"));
                panel.add(createActionButton("Update Patient Status", "📝"));
                break;
            case "secretary":
                panel.add(createActionButton("Book Appointments", "📖"));
                panel.add(createActionButton("Manage Billing", "💰"));
                panel.add(createActionButton("Generate Reports", "📊")); // Added for Secretary
                break;
            case "patient":
                panel.add(createActionButton("View My Records", "📂"));
                panel.add(createActionButton("My Appointments", "⏰"));
                break;
        }
    }

    private JButton createActionButton(String text, String emoji) {
        JButton btn = new JButton(emoji + " " + text);
        btn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(46, 117, 180));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(46, 117, 180), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Navigation logic based on button text
        btn.addActionListener(e -> {
            if (text.equals("View Patient Medical Records")) {
                new MedicalRecordGUI(currentUser).setVisible(true);
                this.dispose();
            } else if (text.equals("Diagnose Patient (Doctor View)")) {
                // Uses Bridge Pattern: DoctorView + DesktopDisplay
                String idStr = JOptionPane.showInputDialog(this,
                        "Enter Patient ID to view full medical record:",
                        "Doctor View - Diagnose Patient",
                        JOptionPane.QUESTION_MESSAGE);
                if (idStr != null && !idStr.trim().isEmpty()) {
                    try {
                        // Build a sample record to display via Bridge Pattern
                        Package3.MedicalRecordDAO dao = Package3.MedicalRecordDAO.getInstance();
                        List<MedicalRecord> history = dao.getPatientHistory(idStr.trim());
                        if (!history.isEmpty()) {
                            MedicalRecord rec = history.get(0); // Get the latest record
                            // Bridge Pattern display
                            StringBuilder sb = new StringBuilder();
                            DoctorView view = new DoctorView(content -> sb.append(content));
                            view.display(rec);
                            JOptionPane.showMessageDialog(this, sb.toString(),
                                    "Doctor View - Patient " + idStr,
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this,
                                    "No records found for Patient ID: " + idStr,
                                    "Not Found", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this,
                                "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (text.equals("Add New Medical Record")) {
                new MedicalRecordGUI(currentUser).setVisible(true);
                this.dispose();
            } else if (text.equals("Generate Reports")) {
                new ReportGUI(currentUser).setVisible(true);
                this.dispose();
            } else if (text.equals("Send Notifications")) {
                new NotificationGUI(currentUser).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Feature coming soon: " + text);
            }
        });

        // Add a simple hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(240, 248, 255));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
            }
        });

        return btn;
    }
}
