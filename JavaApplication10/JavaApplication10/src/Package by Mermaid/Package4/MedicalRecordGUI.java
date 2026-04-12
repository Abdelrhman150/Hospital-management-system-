package Package4;

<<<<<<< HEAD
import Package1.staff.User;
=======
import Package1.User;
import Package1.MedicalRecordDisplay.MedicalRecord;
>>>>>>> 2d8052ceec96ad29dc964448c293e6853cad9ce4
import Package3.MedicalRecordDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Vector;

/**
 * MedicalRecordGUI: Handles viewing and adding medical records for doctors.
 */
public class MedicalRecordGUI extends JFrame {

    private User currentUser;
    private MedicalRecordDAO medicalRecordDAO;
    private JTextField txtPatientId;
    private JTextArea txtDiagnosis, txtComplaint;
    private JTable historyTable;
    private DefaultTableModel tableModel;

    public MedicalRecordGUI(User user) {
        this.currentUser = user;
        this.medicalRecordDAO = MedicalRecordDAO.getInstance();

        setTitle("Medical Records Management - Dr. " + user.getName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel with Gradient
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(230, 240, 255);
                Color color2 = Color.WHITE;
                g2d.setPaint(new GradientPaint(0, 0, color1, 0, h, color2));
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- TOP BAR (Search & Actions) ---
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        topBar.setOpaque(false);

        JButton btnHome = new JButton("<< Home");
        btnHome.addActionListener(e -> {
            new DashboardGUI(currentUser).setVisible(true);
            this.dispose();
        });

        JLabel lblSearch = new JLabel("Patient ID:");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));

        txtPatientId = new JTextField(10);
        JButton btnViewHistory = new JButton("View History 📋");
        btnViewHistory.setBackground(new Color(46, 117, 180));
        btnViewHistory.setForeground(Color.WHITE);
        btnViewHistory.addActionListener(e -> loadPatientHistory());

        topBar.add(btnHome);
        topBar.add(lblSearch);
        topBar.add(txtPatientId);
        topBar.add(btnViewHistory);

        // --- CENTER (Tabs: View vs Add) ---
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Tab 1: View History
        JPanel viewPanel = new JPanel(new BorderLayout());
        viewPanel.setBackground(Color.WHITE);
        tableModel = new DefaultTableModel(new String[] { "Record ID", "Date", "Diagnosis", "Complaint" }, 0);
        historyTable = new JTable(tableModel);
        historyTable.setRowHeight(25);
        viewPanel.add(new JScrollPane(historyTable), BorderLayout.CENTER);
        tabbedPane.addTab("Patient History", viewPanel);

        // Tab 2: Add New Record
        JPanel addPanel = new JPanel(new GridBagLayout());
        addPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        addPanel.add(new JLabel("Diagnosis:"), gbc);
        txtDiagnosis = new JTextArea(3, 30);
        txtDiagnosis.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        gbc.gridx = 1;
        addPanel.add(new JScrollPane(txtDiagnosis), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        addPanel.add(new JLabel("Complaint:"), gbc);
        txtComplaint = new JTextArea(3, 30);
        txtComplaint.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        gbc.gridx = 1;
        addPanel.add(new JScrollPane(txtComplaint), gbc);

        JButton btnSave = new JButton("Save New Record ✅");
        btnSave.setBackground(new Color(40, 167, 69));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.addActionListener(e -> saveNewRecord());
        gbc.gridx = 1;
        gbc.gridy = 2;
        addPanel.add(btnSave, gbc);

        tabbedPane.addTab("Add New Record", addPanel);

        mainPanel.add(topBar, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void loadPatientHistory() {
        String idStr = txtPatientId.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Patient ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String patientId = idStr;
            tableModel.setRowCount(0); // Clear old data

            List<MedicalRecord> history = medicalRecordDAO.getPatientHistory(patientId);
            if (history.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No records found for this patient.", "Info",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (MedicalRecord rec : history) {
                    Vector<Object> row = new Vector<>();
                    row.add(rec.recordId);
                    row.add(rec.dateCreated);
                    row.add(rec.clinicalDiagnosis);
                    row.add(rec.chiefComplaint);
                    tableModel.addRow(row);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading history: " + ex.getMessage(), "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveNewRecord() {
        String idStr = txtPatientId.getText().trim();
        String diagnosis = txtDiagnosis.getText().trim();
        String complaint = txtComplaint.getText().trim();

        if (idStr.isEmpty() || diagnosis.isEmpty() || complaint.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in Patient ID, Diagnosis, and Complaint.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String patientId = idStr;
            String doctorId = (currentUser != null) ? currentUser.getPersonId() : "DOC001";

            medicalRecordDAO.createMedicalRecord(patientId, doctorId, diagnosis, complaint,
                    new java.sql.Date(System.currentTimeMillis()));

            JOptionPane.showMessageDialog(this, "Medical Record saved successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            // Clear fields and refresh history
            txtDiagnosis.setText("");
            txtComplaint.setText("");
            loadPatientHistory();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving record: " + ex.getMessage(), "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Test entry
        MainMenuGUI.setModernLookAndFeel();
        SwingUtilities.invokeLater(() -> new MedicalRecordGUI(null).setVisible(true));
    }
}