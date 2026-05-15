package Package4;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Patient Form GUI
 */
public class PatientFormGUI extends JFrame {

    public PatientFormGUI() {
        setTitle("Hospital Management - Patient Information");
        setSize(580, 680);
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

        // --- HEADER SECTION ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel lblHeader = new JLabel("📝 Patient Information Form", SwingConstants.CENTER);
        lblHeader.setForeground(new Color(46, 117, 180));
        lblHeader.setFont(new Font("Segoe UI Emoji", Font.BOLD, 22));
        headerPanel.add(lblHeader, BorderLayout.CENTER);

        // --- CENTER SECTION (FORM) ---
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.setBorder(new EmptyBorder(10, 40, 20, 40));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE); // Inner card
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(25, 25, 25, 25)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 12, 15, 12);

        int r = 0;
        JTextField txtName = new JTextField();
        addFormRow(formPanel, gbc, "👤 Patient Name:", txtName, r++);

        JTextField txtAge = new JTextField();
        addFormRow(formPanel, gbc, "🎂 Age:", txtAge, r++);

        // Gender
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        genderPanel.setBackground(Color.WHITE);
        JRadioButton rbMale = new JRadioButton("Male");
        JRadioButton rbFemale = new JRadioButton("Female");
        rbMale.setBackground(Color.WHITE);
        rbFemale.setBackground(Color.WHITE);
        rbMale.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rbFemale.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ButtonGroup bgGender = new ButtonGroup();
        bgGender.add(rbMale);
        bgGender.add(rbFemale);
        genderPanel.add(rbMale);
        genderPanel.add(rbFemale);

        addFormComponent(formPanel, gbc, "⚧ Gender:", genderPanel, r++);

        JTextField txtContact = new JTextField();
        addFormRow(formPanel, gbc, "📞 Contact Number:", txtContact, r++);

        JTextField txtAddress = new JTextField();
        addFormRow(formPanel, gbc, "🏠 Address:", txtAddress, r++);



        // Blood Type
        JTextField txtBloodType = new JTextField();
        addFormRow(formPanel, gbc, "🩸 Blood Type:", txtBloodType, r++);

        centerWrapper.add(formPanel, BorderLayout.CENTER);

        // --- BOTTOM SECTION (BUTTONS) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 15));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(0, 0, 25, 0));

        JButton btnSubmit = createStyledButton("Submit", new Color(46, 117, 180)); // blue
        JButton btnCancel = createStyledButton("Cancel", new Color(130, 130, 130)); // gray

        btnSubmit.addActionListener(e -> {
            String name = txtName.getText().trim();
            String ageStr = txtAge.getText().trim();
            String gender = rbMale.isSelected() ? "Male" : (rbFemale.isSelected() ? "Female" : "Other");
            String phone = txtContact.getText().trim();
            String address = txtAddress.getText().trim();
            String bloodType = txtBloodType.getText().trim();

            if (name.isEmpty() || ageStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill required fields (Name, Age).", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String patientId = Package2.IdGenerator.getInstance().nextPatientId();
                // Simple mapping of age to a dummy Date of Birth for now
                java.util.Date dob = new java.util.Date(); 
                
                Package3.PatientDAO.getInstance().addPatient(patientId, name, gender, bloodType, phone, address, 
                    new java.sql.Date(dob.getTime()));

                JOptionPane.showMessageDialog(this, "Patient Registered Successfully!\nID: " + patientId, "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                new MainMenuGUI().setVisible(true);
                this.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving patient: " + ex.getMessage(), "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> {
            new MainMenuGUI().setVisible(true);
            this.dispose();
        });

        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnCancel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 42));
        return btn;
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, String labelText, JTextField tf, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.35;
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl.setForeground(Color.DARK_GRAY);
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY),
                new EmptyBorder(6, 8, 6, 8)));
        panel.add(tf, gbc);
    }

    private void addFormComponent(JPanel panel, GridBagConstraints gbc, String labelText, JComponent comp, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.35;
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
        lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl.setForeground(Color.DARK_GRAY);
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        panel.add(comp, gbc);
    }

    public static void main(String[] args) {
        MainMenuGUI.setModernLookAndFeel();
        SwingUtilities.invokeLater(() -> new PatientFormGUI().setVisible(true));
    }
}
