package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Package3.BillDAO;
import Package3.BillDAO.BillRecord;
import util.BillResponseBuilder;

/**
 * BillServlet — RESTful HTTP API for Bill / Payment operations.
 * Base URL: /api/bills/*
 *
 * GET    /api/bills/                        → Get all bills
 * GET    /api/bills/{billId}                → Get bill by ID
 * POST   /api/bills/                        → Create a new bill
 * POST   /api/bills/patient/{patientId}     → Create a bill for a specific patient
 * PUT    /api/bills/{billId}/pay            → Mark bill as Paid
 * PUT    /api/bills/{billId}/update         → Update amount and status
 * DELETE /api/bills/{billId}               → Delete a single bill
 * DELETE /api/bills/patient/{patientId}    → Delete all bills for a patient
 */
@WebServlet("/api/bills/*")
public class BillServlet extends HttpServlet {

    private final BillDAO billDAO = BillDAO.getInstance();

    // DTO for parsing JSON request body
    private static class BillDTO {
        String billId;
        String patientId;
        double amount;
        String status;
    }

    // =========================================================================
    // GET — 2 endpoints
    // =========================================================================

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        setJson(resp);
        String pathInfo = req.getPathInfo();

        try {
            // GET /api/bills/
            if (pathInfo == null || pathInfo.equals("/")) {
                List<BillRecord> all = billDAO.getAllBills();
                resp.getWriter().write(BillResponseBuilder.toJson(all));
                return;
            }

            // GET /api/bills/{billId}
            String[] parts = pathInfo.split("/");
            if (parts.length == 2) {
                try {
                    BillRecord bill = billDAO.getBillById(parts[1]);
                    resp.getWriter().write(BillResponseBuilder.toJson(bill));
                } catch (Exception e) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write(error("Bill not found: " + parts[1]));
                }
                return;
            }

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(error("Invalid path"));

        } catch (Exception e) {
            serverError(resp, e);
        }
    }

    // =========================================================================
    // POST — 2 endpoints
    // =========================================================================

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        setJson(resp);
        String pathInfo = req.getPathInfo();

        try {
            String[] parts = pathInfo == null ? new String[]{} : pathInfo.split("/");

            // POST /api/bills/patient/{patientId}
            // Body: { "billId": "B001", "amount": 500.0 }
            if (parts.length == 3 && "patient".equals(parts[1])) {
                String patientId = parts[2];
                BillDTO dto = parseBody(req, BillDTO.class);

                if (dto.billId == null || dto.billId.isEmpty()) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write(error("billId is required"));
                    return;
                }

                billDAO.addBill(dto.billId, patientId, dto.amount, "Unpaid");
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("{\"message\":\"Bill created for patient\","
                        + "\"billId\":\"" + dto.billId + "\","
                        + "\"patientId\":\"" + patientId + "\"}");
                return;
            }

            // POST /api/bills/
            // Body: { "billId": "B001", "patientId": "P001", "amount": 500.0, "status": "Unpaid" }
            if (pathInfo == null || pathInfo.equals("/")) {
                BillDTO dto = parseBody(req, BillDTO.class);

                if (dto.billId == null || dto.patientId == null) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write(error("billId and patientId are required"));
                    return;
                }

                String status = (dto.status != null && !dto.status.isEmpty()) ? dto.status : "Unpaid";
                billDAO.addBill(dto.billId, dto.patientId, dto.amount, status);
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("{\"message\":\"Bill created successfully\","
                        + "\"billId\":\"" + dto.billId + "\"}");
                return;
            }

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(error("Invalid endpoint"));

        } catch (Exception e) {
            serverError(resp, e);
        }
    }

    // =========================================================================
    // PUT — 2 endpoints
    // =========================================================================

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        setJson(resp);
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(error("Missing bill ID in path"));
            return;
        }

        String[] parts = pathInfo.split("/"); // ["", billId, action]

        if (parts.length < 3) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(error("Expected: /api/bills/{billId}/{action}"));
            return;
        }

        String billId = parts[1];
        String action = parts[2];

        try {
            // PUT /api/bills/{billId}/pay
            // No body required
            if ("pay".equals(action)) {
                billDAO.markAsPaid(billId);
                resp.getWriter().write("{\"message\":\"Bill marked as Paid\","
                        + "\"billId\":\"" + billId + "\"}");

            // PUT /api/bills/{billId}/update
            // Body: { "amount": 750.0, "status": "Unpaid" }
            } else if ("update".equals(action)) {
                BillDTO dto = parseBody(req, BillDTO.class);

                if (dto.status == null || dto.status.isEmpty()) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write(error("status is required"));
                    return;
                }

                billDAO.updateBill(billId, dto.amount, dto.status);
                resp.getWriter().write("{\"message\":\"Bill updated successfully\","
                        + "\"billId\":\"" + billId + "\"}");

            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(error("Unknown action '" + action + "'. Use 'pay' or 'update'"));
            }

        } catch (Exception e) {
            serverError(resp, e);
        }
    }

    // =========================================================================
    // DELETE — 2 endpoints
    // =========================================================================

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        setJson(resp);
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(error("Missing ID in path"));
            return;
        }

        String[] parts = pathInfo.split("/");

        try {
            // DELETE /api/bills/patient/{patientId}
            if (parts.length == 3 && "patient".equals(parts[1])) {
                String patientId = parts[2];
                List<BillRecord> bills = billDAO.getBillsByPatient(patientId);
                for (BillRecord b : bills) {
                    billDAO.deleteBill(b.getBillId());
                }
                resp.getWriter().write("{\"message\":\"Deleted " + bills.size()
                        + " bill(s) for patient " + patientId + "\"}");
                return;
            }

            // DELETE /api/bills/{billId}
            if (parts.length == 2) {
                billDAO.deleteBill(parts[1]);
                resp.getWriter().write("{\"message\":\"Bill deleted successfully\","
                        + "\"billId\":\"" + parts[1] + "\"}");
                return;
            }

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(error("Invalid path"));

        } catch (Exception e) {
            serverError(resp, e);
        }
    }

    // =========================================================================
    // Helpers
    // =========================================================================

    private void setJson(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    private <T> T parseBody(HttpServletRequest req, Class<T> clazz) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
        }
        return BillResponseBuilder.fromJson(sb.toString(), clazz);
    }

    private String error(String msg) {
        return "{\"error\":\"" + msg + "\"}";
    }

    private void serverError(HttpServletResponse resp, Exception e) throws IOException {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.getWriter().write(error(e.getMessage()));
    }
}
