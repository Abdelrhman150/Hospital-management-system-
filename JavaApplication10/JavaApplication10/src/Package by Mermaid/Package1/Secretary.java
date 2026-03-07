package Package1;

import Package2.HospitalServiceController;

public class Secretary extends User {
    private String shift;

    public Secretary(int id, String name, String phone, String email, String shift) {
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

    public void bookAppointment(int patientId, String doctorName, String appointmentDate, int roomID) {
        HospitalServiceController hospitalServiceController = new HospitalServiceController(new OutPatientServiceFactory());
        Appointment appointment = hospitalServiceController.CreateAppointment(patientId, doctorName, appointmentDate, roomID);
        System.out.println("Appointment booked successfully!");

    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Shift: " + shift);
    }


}