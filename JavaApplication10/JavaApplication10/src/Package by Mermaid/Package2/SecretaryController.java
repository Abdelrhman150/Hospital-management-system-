package Package2;

import Package1.payment.PaymentProcessor;
import Package1.hospitalservice.*;
import Package3.AppointmentDAO;
import Package3.BillDAO;
import Package1.room.Room;

/**
 * Controller for secretary operations.
 */
public class SecretaryController {
    AppointmentMediator appointmentMediator;
    Appointment appointment;
    HospitalServiceController hospitalServiceController;
    Bill bill;


    public SecretaryController() {
        this.appointmentMediator = new HospitalAppointmentMediator();
    }

    public String bookVisitingAppointment(String patientId, String doctorName, String appointmentDate, String roomId) throws Exception {

        return appointmentMediator.bookAppointment(
            patientId,
            doctorName,
            appointmentDate,
            roomId,
            0,
            "Visiting"
        );
    }

    public String bookStayAppointment(String patientId,
                                      String doctorName,
                                      String appointmentDate,
                                      String roomId,
                                      int daysOfStay) throws Exception {

        return appointmentMediator.bookAppointment(
            patientId,
            doctorName,
            appointmentDate,
            roomId,
            daysOfStay,
            "Stay"
        );
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
        AppointmentDAO appointmentDAO = AppointmentDAO.getInstance();
        try {
            appointmentDAO.GetAppointmentDetails(billId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DisplayBillDetails(String billId) {
        BillDAO billDAO = BillDAO.getInstance();
        try {
            billDAO.BillDetails(billId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}