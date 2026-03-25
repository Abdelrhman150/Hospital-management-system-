package Package1;

import java.sql.SQLException;

import Package1.Payement_System.Insurance;
import Package1.Payement_System.InsuranceAdaptor;
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
            Doctors.getAvailableDoctors();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void bookVisitingAppointment(String patientId, String doctorName, String appointmentDate) {
        hospitalServiceController = new HospitalServiceController(new OutPatientServiceFactory());
        appointment = hospitalServiceController.CreateAppointment(patientId, doctorName, appointmentDate,
                null, null);
        System.out.println("Appointment booked successfully!");
        
        
    }

    public void bookStayAppointment(String patientId, String doctorName, String appointmentDate, String roomID, int daysOfStay)
        throws Exception {
        hospitalServiceController = new HospitalServiceController(new StayPatientServiceFactory(roomID));
        appointment = hospitalServiceController.CreateAppointment(patientId, doctorName, appointmentDate,
                roomID, daysOfStay);
        System.out.println("Appointment booked successfully!");
        
    }

    public void GenerateBill(){
        bill = hospitalServiceController.CreateBill(appointment.getPatientId(), appointment.getDaysOfStay());
        System.out.println("Bill generated successfully!");
    }

    public void Payment(String billId, double amount, PaymentProcessor paymentProcessor) {
        bill.setPaymentProcessor(paymentProcessor);
        System.out.println("Payment processed successfully!");
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