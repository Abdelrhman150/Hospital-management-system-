package Package4;

import Package1.Report;
import Package1.staff.User;
import Package2.ExcelFormatter;
import Package2.PDFFormatter;
import Package2.ReportController;
import Package2.ReportFormatter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Report GUI with home navigation and stylish UI.
 */
public class ReportGUI extends JFrame {
    private ReportController reportController;
    private User currentUser;

    private JRadioButton rbtnPatientReport, rbtnFinancialReport;
    private JTextField txtPatientId;
    private JComboBox<String> cmbFormatter;
    private JTextArea txtOutput;

    public ReportGUI(User user) {
        this.currentUser = user;
        reportController = new ReportController();
        setTitle("Hospital Management - Reports");
        setSize(700, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(230, 238, 245)); // Gray blue bg

        // --- HEADER SECTION ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(46, 117, 180));
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JButton btnHome = createHomeButton();
        headerPanel.add(btnHome, BorderLayout.WEST);

        JLabel lblTitle = new JLabel("Report Generation Module", SwingConstants.CENTER); // removed emoji
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(lblTitle, BorderLayout.CENTER);

        JLabel blank = new JLabel("   ");
        blank.setPreferredSize(btnHome.getPreferredSize());
        headerPanel.add(blank, BorderLayout.EAST);

        // --- TOP CONTROLS ---
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(new Color(230, 238, 245));
        centerWrapper.setBorder(new EmptyBorder(20, 30, 20, 30)); // Frame padding

        JPanel controlsPanel = new JPanel(new GridBagLayout());
        controlsPanel.setBackground(Color.WHITE); // White card
        controlsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(20, 20, 20, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Type
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblType = new JLabel("Report Type:");
        lblType.setFont(new Font("Segoe UI", Font.BOLD, 14));
        controlsPanel.add(lblType, gbc);

        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        typePanel.setBackground(Color.WHITE);
        rbtnPatientReport = new JRadioButton("Patient      ", true);
        rbtnFinancialReport = new JRadioButton("Financial");
        rbtnPatientReport.setBackground(Color.WHITE);
        rbtnFinancialReport.setBackground(Color.WHITE);
        rbtnPatientReport.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        rbtnFinancialReport.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ButtonGroup bgType = new ButtonGroup();
        bgType.add(rbtnPatientReport);
        bgType.add(rbtnFinancialReport);
        typePanel.add(rbtnPatientReport);
        typePanel.add(rbtnFinancialReport);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        controlsPanel.add(typePanel, gbc);

        // Patient ID
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel lblId = new JLabel("Patient ID:");
        lblId.setFont(new Font("Segoe UI", Font.BOLD, 14));
        controlsPanel.add(lblId, gbc);

        txtPatientId = new JTextField();
        txtPatientId.setPreferredSize(new Dimension(150, 32));
        txtPatientId.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY),
                new EmptyBorder(4, 8, 4, 8)));
        txtPatientId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        controlsPanel.add(txtPatientId, gbc);

        // Formatter
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblFormat = new JLabel("Output Format:");
        lblFormat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        controlsPanel.add(lblFormat, gbc);

        cmbFormatter = new JComboBox<>(new String[] { "PDF", "Excel" });
        cmbFormatter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbFormatter.setPreferredSize(new Dimension(150, 32));
        gbc.gridx = 1;
        controlsPanel.add(cmbFormatter, gbc);

        // Buttons
        JButton btnGenerate = createStyledButton("Generate Report", new Color(46, 117, 180));
        btnGenerate.addActionListener(e -> generateReport());
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        controlsPanel.add(btnGenerate, gbc);

        // Toggles
        rbtnPatientReport.addActionListener(e -> txtPatientId.setEnabled(true));
        rbtnFinancialReport.addActionListener(e -> {
            txtPatientId.setEnabled(false);
            txtPatientId.setText("");
        });

        // --- OUTPUT AREA ---
        txtOutput = new JTextArea();
        txtOutput.setEditable(false);
        txtOutput.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtOutput.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(txtOutput);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(10, 0, 0, 0),
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Generated Output", 0, 0,
                        new Font("Segoe UI", Font.BOLD, 12))));

        centerWrapper.add(controlsPanel, BorderLayout.NORTH);
        centerWrapper.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerWrapper, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JButton createHomeButton() {
        JButton btn = new JButton("<< Home"); // Removed emoji
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
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return btn;
    }

    private void generateReport() {
        boolean isPatientReport = rbtnPatientReport.isSelected();
        String patientId = "";

        if (isPatientReport) {
            String idStr = txtPatientId.getText().trim();
            if (idStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Patient ID.", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                //patientId = Integer.parseInt(idStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Patient ID must be numeric.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String formatSelection = (String) cmbFormatter.getSelectedItem();
        ReportFormatter formatter = "PDF".equals(formatSelection) ? new PDFFormatter() : new ExcelFormatter();

        Report report = isPatientReport ? reportController.createPatientReport(patientId, formatter)
                : reportController.createFinancialReport(formatter);

        if (report != null) {
            txtOutput.setText(report.getFullContent());
            
            // Real File Generation
            String fileName = isPatientReport ? "PatientReport_" + patientId : "FinancialReport";
            String extension = "PDF".equals(formatSelection) ? ".pdf" : ".csv";
            report.exportToFile(fileName + extension);

            JOptionPane.showMessageDialog(this, "Report Generated and Opened!\nSaved as: " + fileName + extension, 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            txtOutput.setText("");
            JOptionPane.showMessageDialog(this, "Failed to generate report.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        MainMenuGUI.setModernLookAndFeel();
        SwingUtilities.invokeLater(() -> new ReportGUI(null).setVisible(true));
    }
}
