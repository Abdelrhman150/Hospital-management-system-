package Package2;

import java.util.List;

import Package3.BillDAO;
import Package3.BillDAO.BillRecord;

/**
 * Controller for Bill / Payment operations.
 * Bridges the UI layer (Package4) with the DAO layer (Package3).
 * Mirrors the Controller pattern used across the rest of the application.
 */
public class PaymentController {

    private final BillDAO billDAO = BillDAO.getInstance();

    // ── Create ────────────────────────────────────────────────────────────────

    /**
     * Create a new bill with status "Unpaid".
     */
    public void createBill(String billId, String patientId, double amount) throws Exception {
        billDAO.addBill(billId, patientId, amount, "Unpaid");
    }

    // ── Read ──────────────────────────────────────────────────────────────────

    /** Retrieve all bills in the system (sorted by date descending). */
    public List<BillRecord> getAllBills() throws Exception {
        return billDAO.getAllBills();
    }

    /** Retrieve a single bill by its ID. */
    public BillRecord getBillById(String billId) throws Exception {
        return billDAO.getBillById(billId);
    }

    /** Retrieve all bills belonging to a specific patient. */
    public List<BillRecord> getBillsByPatient(String patientId) throws Exception {
        return billDAO.getBillsByPatient(patientId);
    }

    /** Calculate total unpaid amount for a patient. */
    public double getTotalUnpaid(String patientId) throws Exception {
        return billDAO.getTotalUnpaid(patientId);
    }

    // ── Update ────────────────────────────────────────────────────────────────

    /**
     * Mark a bill as Paid.
     */
    public void payBill(String billId) throws Exception {
        billDAO.markAsPaid(billId);
    }

    /**
     * Update the amount and status of an existing bill.
     */
    public void updateBill(String billId, double newAmount, String newStatus) throws Exception {
        billDAO.updateBill(billId, newAmount, newStatus);
    }

    // ── Delete ────────────────────────────────────────────────────────────────

    /**
     * Delete a bill record permanently.
     */
    public void deleteBill(String billId) throws Exception {
        billDAO.deleteBill(billId);
    }
}
