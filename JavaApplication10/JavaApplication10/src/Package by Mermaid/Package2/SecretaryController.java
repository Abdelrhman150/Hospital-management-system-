package Package2;

import Package1.payment.PaymentProcessor;
import Package1.hospitalservice.*;
import Package3.AppointmentDAO;
import Package3.BillDAO;
import Package1.room.Room;
import Package1.sorting.AppointmentData;
import Package1.sorting.AppointmentSortingContext;
import Package1.sorting.SortByDateStrategy;
import Package1.sorting.SortByDoctorStrategy;
import Package1.sorting.SortByPriorityStrategy;
import Package1.sorting.SortByTypeStrategy;

import java.util.List;

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

        BillDAO billDAO = BillDAO.getInstance();
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
    

    // ==================== NEW METHODS FOR SORTED APPOINTMENTS ====================

    public void showSortedAppointments(String sortType) {
        try {
            AppointmentDAO dao = AppointmentDAO.getInstance();
            List<AppointmentData> appointments = dao.getAllAppointments();

            if (appointments.isEmpty()) {
                System.out.println("no appointments!");
                return;
            }

            AppointmentSortingContext context = new AppointmentSortingContext();

            if ("date".equals(sortType)) {
                context.setStrategy(new SortByDateStrategy());
            } else if ("priority".equals(sortType)) {
                context.setStrategy(new SortByPriorityStrategy());
            } else if ("type".equals(sortType)) {
                context.setStrategy(new SortByTypeStrategy());
            } else if ("doctor".equals(sortType)) {
                context.setStrategy(new SortByDoctorStrategy());
            }

            List<AppointmentData> sorted = context.execute(appointments);

            System.out.println("\n" + "=".repeat(100));
            System.out.println("sorted appointments:");
            System.out.println("=".repeat(100));

            for (int i = 0; i < sorted.size(); i++) {
                System.out.println((i + 1) + ". " + sorted.get(i));
            }

            System.out.println("=".repeat(100));

        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}