package Package1;

import java.sql.SQLException;
import java.sql.ResultSet;

import Package1.Payement_System.PaymentProcessor;
import Package2.*;
import Package3.DoctorDAO;

public class Secretary extends User {
    private String shift;
    Appointment appointment;
    HospitalServiceController hospitalServiceController; 
    Bill bill ;

    public Secretary(String id, String name, String phone, String email, String shift) {
        super(id, name, phone, email);
        this.shift = shift;
    }

    @Override
    public String getRole() {
        return "Secretary";
    }

    public String getShift() {
        return shift;
    }

    public void showAvailableDoctors(){
        DoctorDAO Doctors = DoctorDAO.getInstance();
        try {
            ResultSet rs = Doctors.getAvailableDoctors();
            System.out.println("Available Doctors:");
            while (rs.next()) {
                String doctorId = rs.getString("doctorId");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
                System.out.println("ID: " + doctorId + ", Name: " + name + ", Specialization: " + specialization);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String bookVisitingAppointment(String patientId, String doctorName, String appointmentDate) {
        hospitalServiceController = new HospitalServiceController(new OutPatientServiceFactory());
        appointment = hospitalServiceController.CreateAppointment(patientId, doctorName, appointmentDate);
        appointment.scheduleAppointment(patientId, doctorName, appointmentDate);
        System.out.println("Appointment booked successfully!");
        return appointment.getAppointmentId() ;
        
        
    }

    public String bookStayAppointment(String patientId, String doctorName, String appointmentDate, String roomID)
        throws Exception {
        hospitalServiceController = new HospitalServiceController(new StayPatientServiceFactory(roomID));
        appointment = hospitalServiceController.CreateAppointment(patientId, doctorName, appointmentDate);
        appointment.scheduleAppointment(patientId, doctorName, appointmentDate);
        System.out.println("Appointment booked successfully!");
        return appointment.getAppointmentId() ;

    }

    public void GenerateBill(){
        bill = hospitalServiceController.CreateBill(appointment.getPatientId(), appointment.getDaysOfStay());
        System.out.println("Bill Generated Successfully. ");
    }

    public void DisplayBillDetailsAfterGeneration() {
        if (bill != null && bill.getBillId() != null) {
            bill.getBillDetails(bill.getBillId());
        } else {
            System.out.println("No bill has been generated yet.");
        }
    }

    public void Payment(PaymentProcessor paymentProcessor) {
        bill.setPaymentProcessor(paymentProcessor);
        System.out.println("Payment processed successfully for bill : " + bill.getBillId());
    }


    public void DisplayAppointmentDetails(String billId) {
        appointment.displayDetails(billId);
    }

    public void DisplayBillDetails(String billId) {
        bill.getBillDetails(billId);
    } 


    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Shift: " + shift);
    }

}