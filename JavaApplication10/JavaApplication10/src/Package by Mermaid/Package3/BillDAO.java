package Package3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object: BillDAO
 * Handles all database operations for the Bills table.
 * Implemented using the Singleton Pattern.
 */
public class BillDAO {

    // ==================== Singleton ====================

    private static BillDAO instance;

    private BillDAO() {
    }

    public static synchronized BillDAO getInstance() {
        if (instance == null)
            instance = new BillDAO();
        return instance;
    }

    // ==================== Bill Record (Inner Class) ====================

    public static class BillRecord {
        private String billId;
        private String patientId;
        private double amount;
        private Timestamp billDate;
        private String status;

        public BillRecord(String billId, String patientId, double amount,
                Timestamp billDate, String status) {
            this.billId    = billId;
            this.patientId = patientId;
            this.amount    = amount;
            this.billDate  = billDate;
            this.status    = status;
        }

        public String    getBillId()    { return billId; }
        public String    getPatientId() { return patientId; }
        public double    getAmount()    { return amount; }
        public Timestamp getBillDate()  { return billDate; }
        public String    getStatus()    { return status; }

        @Override
        public String toString() {
            return "Bill #" + billId
                 + " | Patient: " + patientId
                 + " | Amount: $" + amount
                 + " | Status: " + status
                 + " | Date: " + billDate;
        }
    }

    // ==================== CRUD Operations ====================

    /**
     * إضافة فاتورة جديدة.
     * billId يُولَّد تلقائيًا بواسطة قاعدة البيانات (IDENTITY column).
     */
    public void addBill(String patientId, double amount, String status) throws Exception {
        String sql = "INSERT INTO Bills(patientId, amount, status) VALUES(?,?,?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patientId);
            ps.setDouble(2, amount);
            ps.setString(3, status);
            ps.executeUpdate();
        }
    }

    /**
     * إضافة فاتورة بعد تطبيق خصم التأمين.
     * amount = originalAmount - (originalAmount * discountPct / 100)
     */
    public void addBillWithInsurance(String patientId, double originalAmount,
            int insuranceDiscountPercentage) throws Exception {
        double discounted = originalAmount
                - (originalAmount * insuranceDiscountPercentage / 100.0);
        addBill(patientId, discounted, "Unpaid");
    }

    /**
     * تعديل مبلغ وحالة فاتورة موجودة.
     */
    public void updateBill(String billId, double amount, String status) throws Exception {
        String sql = "UPDATE Bills SET amount=?, status=? WHERE billId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setString(2, status);
            ps.setString(3, billId);
            if (ps.executeUpdate() == 0)
                throw new SQLException("No bill found with ID: " + billId);
        }
    }

    /**
     * تحديد الفاتورة كـ "Paid".
     */
    public void markAsPaid(String billId) throws Exception {
        String sql = "UPDATE Bills SET status='Paid' WHERE billId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, billId);
            if (ps.executeUpdate() == 0)
                throw new SQLException("No bill found with ID: " + billId);
        }
    }

    /**
     * حذف فاتورة بالـ ID.
     */
    public void deleteBill(String billId) throws Exception {
        String sql = "DELETE FROM Bills WHERE billId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, billId);
            if (ps.executeUpdate() == 0)
                throw new SQLException("No bill found with ID: " + billId);
        }
    }

    // ==================== Query Operations ====================

    /**
     * جلب كل الفواتير مرتبة من الأحدث.
     */
    public List<BillRecord> getAllBills() throws Exception {
        List<BillRecord> bills = new ArrayList<>();
        String sql = "SELECT * FROM Bills ORDER BY billDate DESC";
        Connection conn = DatabaseConnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            while (rs.next()) {
                bills.add(new BillRecord(
                        rs.getString("billId"),
                        rs.getString("patientId"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("billDate"),
                        rs.getString("status")));
            }
        }
        return bills;
    }

    /**
     * جلب كل فواتير مريض معين.
     */
    public List<BillRecord> getBillsByPatient(String patientId) throws Exception {
        List<BillRecord> bills = new ArrayList<>();
        String sql = "SELECT * FROM Bills WHERE patientId=? ORDER BY billDate DESC";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bills.add(new BillRecord(
                            rs.getString("billId"),
                            rs.getString("patientId"),
                            rs.getDouble("amount"),
                            rs.getTimestamp("billDate"),
                            rs.getString("status")));
                }
            }
        }
        return bills;
    }

    /**
     * جلب فاتورة واحدة بالـ ID.
     */
    public BillRecord getBillById(String billId) throws Exception {
        String sql = "SELECT * FROM Bills WHERE billId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, billId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new BillRecord(
                            rs.getString("billId"),
                            rs.getString("patientId"),
                            rs.getDouble("amount"),
                            rs.getTimestamp("billDate"),
                            rs.getString("status"));
                } else {
                    throw new SQLException("No bill found with ID: " + billId);
                }
            }
        }
    }

    /**
     * حساب إجمالي المبالغ الغير مدفوعة لمريض معين.
     */
    public double getTotalUnpaid(String patientId) throws Exception {
        String sql = "SELECT ISNULL(SUM(amount), 0) FROM Bills WHERE patientId=? AND status='Unpaid'";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble(1);
            }
        }
        return 0.0;
    }

    public void BillDetails(String billId) throws Exception {
        String sql = "SELECT * FROM Bills WHERE billId=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, billId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("===============================");
                    System.out.println("Bill Details:");
                    System.out.println("================================");
                    System.out.println("Billing Date: " + rs.getTimestamp("billDate"));
                    System.out.println("Bill ID: " + rs.getString("billId"));
                    System.out.println("Patient ID: " + rs.getString("patientId"));
                    System.out.println("Amount: $" + rs.getDouble("amount"));
                    System.out.println("Status: " + rs.getString("status"));
                } else {
                    System.out.println("Bill not found for Bill ID: " + billId);
                }
            }
        }
    }
    
}
