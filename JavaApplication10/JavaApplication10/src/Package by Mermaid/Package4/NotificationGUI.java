package Package4;

import Package1.Notification;
import Package1.staff.User;
import Package2.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Notification UI with improved styling and home navigation.
 */
public class NotificationGUI extends JFrame {
    private NotificationController notificationController;
    private User currentUser;

    private JComboBox<String> cmbType;
    private JTextField txtRecipient;
    private JTextArea txtMessage;
    private JLabel lblStatus;

    public NotificationGUI(User user) {
        this.currentUser = user;
        notificationController = new NotificationController();
        setTitle("Hospital Management - Send Notifications");
        setSize(550, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(230, 238, 245));

        // --- HEADER SECTION ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(46, 117, 180));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JButton btnHome = createHomeButton();
        headerPanel.add(btnHome, BorderLayout.WEST);

        JLabel lblTitle = new JLabel("Send Notifications", SwingConstants.CENTER); // removed emoji
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(lblTitle, BorderLayout.CENTER);

        JLabel blank = new JLabel("   ");
        blank.setPreferredSize(btnHome.getPreferredSize());
        headerPanel.add(blank, BorderLayout.EAST);

        // --- FORM SECTION ---
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(new Color(230, 238, 245));
        wrapperPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 15, 15);

        // Type
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel lblType = new JLabel("Notification Type:");
        lblType.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formPanel.add(lblType, gbc);

        cmbType = new JComboBox<>(new String[] { "Email", "SMS", "Mobile App" });
        cmbType.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(cmbType, gbc);

        // Recipient
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblRecip = new JLabel("Recipient:");
        lblRecip.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formPanel.add(lblRecip, gbc);

        txtRecipient = new JTextField();
        txtRecipient.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtRecipient.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY),
                new EmptyBorder(6, 8, 6, 8)));
        gbc.gridx = 1;
        formPanel.add(txtRecipient, gbc);

        // Message
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        JLabel lblMsg = new JLabel("Message:");
        lblMsg.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formPanel.add(lblMsg, gbc);

        txtMessage = new JTextArea(4, 20);
        txtMessage.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMessage.setLineWrap(true);
        txtMessage.setWrapStyleWord(true);
        txtMessage.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY),
                new EmptyBorder(4, 4, 4, 4)));
        JScrollPane scrollMessage = new JScrollPane(txtMessage);
        gbc.gridx = 1;
        formPanel.add(scrollMessage, gbc);

        wrapperPanel.add(formPanel, BorderLayout.CENTER);

        // --- BOTTOM SECTION ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(230, 238, 245));

        lblStatus = new JLabel("Status: Waiting for action...", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblStatus.setForeground(new Color(108, 117, 125)); // Gray
        lblStatus.setBorder(new EmptyBorder(0, 0, 15, 0));
        bottomPanel.add(lblStatus, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setBackground(new Color(230, 238, 245));
        btnPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JButton btnSend = createStyledButton("Send Notification", new Color(46, 117, 180));
        JButton btnClear = createStyledButton("Clear", new Color(108, 117, 125));

        btnSend.addActionListener(e -> sendNotification());
        btnClear.addActionListener(e -> {
            txtRecipient.setText("");
            txtMessage.setText("");
            lblStatus.setText("Status: Waiting for action...");
            lblStatus.setForeground(new Color(108, 117, 125));
        });

        btnPanel.add(btnSend);
        btnPanel.add(btnClear);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(wrapperPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JButton createHomeButton() {
        JButton btn = new JButton("<< Home");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(36, 90, 140));
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> {
            if (currentUser != null) {
                new DashboardGUI(currentUser).setVisible(true);
            } else {
                new MainMenuGUI().setVisible(true);
            }
            this.dispose();
        });
        return btn;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 40));
        return btn;
    }

    private void sendNotification() {
        String type = (String) cmbType.getSelectedItem();
        String recipient = txtRecipient.getText().trim();
        String message = txtMessage.getText().trim();

        if (recipient.isEmpty() || message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Recipient and Message cannot be empty.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        NotificationService service = null;
        switch (type) {
            case "Email":
                service = new EmailNotificationAdapter(new EmailService());
                break;
            case "SMS":
                service = new SMSNotificationAdapter(new SMSService());
                break;
            case "Mobile App":
                service = new MobileAppNotificationAdapter(new MobileAppService());
                break;
        }

        if (service != null) {
            Notification notification = new Notification(message, recipient);
            notificationController.notifyPatient(service, notification);

            if ("SENT".equals(notification.getStatus())) {
                lblStatus.setText("Status: SUCCESS - Sent via " + type);
                lblStatus.setForeground(new Color(40, 167, 69)); // Green
                JOptionPane.showMessageDialog(this, "Notification successfully sent!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                lblStatus.setText("Status: FAILED");
                lblStatus.setForeground(Color.RED);
            }
        }
    }

    public static void main(String[] args) {
        MainMenuGUI.setModernLookAndFeel();
        SwingUtilities.invokeLater(() -> new NotificationGUI(null).setVisible(true));
    }
}
