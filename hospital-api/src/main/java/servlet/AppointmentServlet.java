package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Package1.sorting.AppointmentData;
import Package3.AppointmentDAO;
import util.AppointmentResponseBuilder;

@WebServlet("/api/appointments/*")
public class AppointmentServlet extends HttpServlet {

    private AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();

    private static class AppointmentDTO {
        String appointmentId;
        String patientId;
        String doctorId;
        String appointmentTime; // expects "yyyy-MM-dd HH:mm:ss"
        String type;
        String roomId;
        int daysOfStay;
        String status; // for status updates
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/appointments
                List<AppointmentData> appointments = appointmentDAO.getAllAppointments();
                resp.getWriter().write(AppointmentResponseBuilder.toJson(appointments));
            } else {
                // GET /api/appointments/{id}
                String id = pathInfo.substring(1);
                AppointmentData data = appointmentDAO.getAppointmentById(id);
                if (data != null) {
                    resp.getWriter().write(AppointmentResponseBuilder.toJson(data));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("{\"error\":\"Appointment not found\"}");
                }
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();
        try {
            AppointmentDTO dto = parseBody(req, AppointmentDTO.class);

            if ("/visiting".equals(pathInfo)) {
                appointmentDAO.bookAppointment(
                        dto.appointmentId, dto.patientId, dto.doctorId,
                        Timestamp.valueOf(dto.appointmentTime), "Visiting", null, 0);
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("{\"message\":\"Visiting appointment created successfully\"}");
            } else if ("/stay".equals(pathInfo)) {
                appointmentDAO.bookAppointment(
                        dto.appointmentId, dto.patientId, dto.doctorId,
                        Timestamp.valueOf(dto.appointmentTime), "Stay", dto.roomId, dto.daysOfStay);
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("{\"message\":\"Stay appointment created successfully\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Invalid endpoint\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Missing appointment ID in path\"}");
            return;
        }

        String[] parts = pathInfo.split("/");
        if (parts.length < 3) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid path format\"}");
            return;
        }

        String id = parts[1];
        String action = parts[2];

        try {
            if ("reschedule".equals(action)) {
                AppointmentDTO dto = parseBody(req, AppointmentDTO.class);
                appointmentDAO.rescheduleAppointment(id, Timestamp.valueOf(dto.appointmentTime));
                resp.getWriter().write("{\"message\":\"Appointment rescheduled successfully\"}");
            } else if ("status".equals(action)) {
                AppointmentDTO dto = parseBody(req, AppointmentDTO.class);
                if ("InProgress".equalsIgnoreCase(dto.status)) {
                    appointmentDAO.startAppointment(id);
                    resp.getWriter().write("{\"message\":\"Appointment started successfully\"}");
                } else if ("Completed".equalsIgnoreCase(dto.status)) {
                    appointmentDAO.completeAppointment(id);
                    resp.getWriter().write("{\"message\":\"Appointment completed successfully\"}");
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("{\"error\":\"Invalid status value\"}");
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Invalid action\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Missing ID in path\"}");
            return;
        }

        String[] parts = pathInfo.split("/");

        try {
            if (parts.length == 3 && "patient".equals(parts[1])) {
                String patientId = parts[2];
                ResultSet rs = appointmentDAO.getPatientAppointments(patientId);
                int count = 0;
                while (rs.next()) {
                    String aptId = rs.getString("appointmentId");
                    appointmentDAO.cancelAppointment(aptId);
                    count++;
                }
                resp.getWriter()
                        .write("{\"message\":\"Cancelled " + count + " appointments for patient " + patientId + "\"}");
            } else if (parts.length == 2) {
                String id = parts[1];
                appointmentDAO.cancelAppointment(id);
                resp.getWriter().write("{\"message\":\"Appointment cancelled successfully\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Invalid path\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private <T> T parseBody(HttpServletRequest req, Class<T> clazz) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return AppointmentResponseBuilder.fromJson(sb.toString(), clazz);
    }
}
