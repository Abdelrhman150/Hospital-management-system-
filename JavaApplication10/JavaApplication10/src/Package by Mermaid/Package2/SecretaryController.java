package Package2;

import Package1.payment.PaymentProcessor;
import Package1.hospitalservice.*;
import Package3.DoctorDAO;
import Package3.RoomDAO;
import Package3.AppointmentDAO;
import Package3.BillDAO;
import Package1.room.Room;

/**
 * Controller for secretary operations.
 */
public class SecretaryController {

    Appointment appointment;
    HospitalServiceController hospitalServiceController;
    Bill bill;

    public SecretaryController() {
    }

    public String bookVisitingAppointment(String patientId, String doctorName, String appointmentDate, String roomId) throws Exception {
        RoomDAO roomDAO = RoomDAO.getInstance();
        Room room = roomDAO.getRoomById(roomId);

        if (!"Available".equals(room.getAvailabilityStatus())) {
            System.out.println("Selected room is not available. Please choose another room.");
            return null;
        }

        hospitalServiceController = new HospitalServiceController(new OutPatientServiceFactory(room));
        appointment = hospitalServiceController.CreateAppointment();
        appointment.scheduleAppointment(patientId, doctorName, appointmentDate);
        
        AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();
        DoctorDAO doctorDAO = DoctorDAO.getInstance();
        try {
            String doctorId = doctorDAO.getDoctorIdByName(doctorName);
            appointmentDAO.bookAppointment(appointment.getAppointmentId(), patientId, doctorId, java.sql.Timestamp.valueOf(appointmentDate), "Visiting", roomId , 0);
        } catch (Exception e) {
            System.out.println("Error scheduling appointment: " + e.getMessage());
            e.printStackTrace();   
        }
        
        System.out.println("Appointment booked successfully!");
        return appointment.getAppointmentId();
    }

    public String bookStayAppointment(String patientId, String doctorName, String appointmentDate, String roomID, int daysOfStay)
            throws Exception {
        RoomDAO roomDAO = RoomDAO.getInstance();
        Room room = roomDAO.getRoomById(roomID);
        
        if (!"Available".equals(room.getAvailabilityStatus())) {
            System.out.println("Selected room is not available. Please choose another room.");
            return null;
        }

        hospitalServiceController = new HospitalServiceController(new StayPatientServiceFactory(room));
        appointment = hospitalServiceController.CreateAppointment();
        
        ((RoomAppointment) appointment).setDaysOfStay(daysOfStay);
        
        appointment.scheduleAppointment(patientId, doctorName, appointmentDate);
        
        room.markOccupied(room.getRoomID());
        System.out.println("Room " + room.getRoomID() + " has been Booked.");

        AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();
        DoctorDAO doctorDAO = DoctorDAO.getInstance();
        try {
            String doctorId = doctorDAO.getDoctorIdByName(doctorName);
            appointmentDAO.bookAppointment(appointment.getAppointmentId(), patientId, doctorId, java.sql.Timestamp.valueOf(appointmentDate), "Stay", room.getRoomID(), daysOfStay);
        } catch (Exception e) {
            System.out.println("Error scheduling appointment: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Appointment booked successfully!");
        return appointment.getAppointmentId();
    }

    public void GenerateBill() {
        bill = hospitalServiceController.CreateBill(appointment.getPatientId(), appointment.getDaysOfStay());
        
        Room room = ((RoomAppointment) appointment).room;
        bill.generateBill(appointment.getPatientId(), appointment.getDaysOfStay());
        

        BillDAO billDAO = BillDAO.getInstance() ;
        try {
            double amount = (bill instanceof RoomBill) ? ((RoomBill) bill).amount : ((VisitingBill) bill).amount;
            billDAO.addBill(bill.getBillId(), appointment.getPatientId(), amount, "Unpaid");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Bill Generated Successfully. ");
    }

    public void DisplayBillDetailsAfterGeneration() {
        if (bill != null && bill.getBillId() != null) {
            bill.getBillDetails(bill.getBillId());
            BillDAO billDAO = BillDAO.getInstance();
            try {
                billDAO.BillDetails(bill.getBillId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No bill has been generated yet.");
        }
    }

    public void Payment(PaymentProcessor paymentProcessor) {
        bill.setPaymentProcessor(paymentProcessor);
        if (bill instanceof RoomBill) {
            paymentProcessor.processPayment(((RoomBill) bill).amount);
        } else if (bill instanceof VisitingBill) {
            paymentProcessor.processPayment(((VisitingBill) bill).amount);
        }
        System.out.println("Payment processed successfully for bill : " + bill.getBillId());
    }

    public void DisplayAppointmentDetails(String billId) {
        appointment.displayDetails(billId);
        AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();
        try {
            appointmentDAO.GetAppointmentDetails(billId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DisplayBillDetails(String billId) {
        bill.getBillDetails(billId);
        BillDAO billDAO = BillDAO.getInstance();
        try {
            billDAO.BillDetails(billId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}