package Package1.HospitalDischarge;

import Package3.AppointmentDAO;
import Package3.BillDAO;
import Package3.RoomDAO;
import Package3.BillDAO.BillRecord;

public class HospitalDischargeMediator implements DischargeMediator {

    private AppointmentDAO appointmentDAO;
    private BillDAO billDAO;
    private RoomDAO roomDAO;

    public HospitalDischargeMediator() {
        this.appointmentDAO = AppointmentDAO.getInstance();
        this.billDAO = BillDAO.getInstance();
        this.roomDAO = RoomDAO.getInstance();
    }

    @Override
    public void dischargePatient(String appointmentId, String billId) throws Exception {

        String appointmentType = appointmentDAO.getAppointmentTypeById(appointmentId);
        if (appointmentType == null) {
            throw new Exception("Appointment not found.");
        }

        if (!"Stay".equalsIgnoreCase(appointmentType)) {
            throw new Exception("Only stay appointments can be discharged.");
        }

        BillRecord billRecord = billDAO.getBillById(billId);
        if (billRecord == null) {
            throw new Exception("Bill not found.");
        }

        if (!"Paid".equalsIgnoreCase(billRecord.getStatus())) {
            throw new Exception("Patient cannot be discharged before bill is paid.");
        }

        String roomId = appointmentDAO.getRoomIdByAppointmentId(appointmentId);
        if (roomId == null || roomId.trim().isEmpty()) {
            throw new Exception("No room is linked to this appointment.");
        }

        roomDAO.markRoomAvailable(roomId);

        appointmentDAO.completeAppointment(appointmentId);

        System.out.println("Patient discharged successfully.");
        System.out.println("Room " + roomId + " is now available.");
        System.out.println("Appointment " + appointmentId + " marked as completed.");
    }
}