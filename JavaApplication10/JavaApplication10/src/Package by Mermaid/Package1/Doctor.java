package Package1;

import Package2.*;

public class Doctor extends User {
    private String specialization;
    private boolean availability;
    private double consultationFee;

    public Doctor(String id, String name, String phone, String email, String specialization, boolean availability, double consultationFee) {
        super(id, name, phone, email);
        this.specialization = specialization;
        this.availability = availability;
        this.consultationFee = consultationFee;
    }

    @Override
    public String getRole() {
        return "Doctor";
    }

    public String getSpecialization() {
        return specialization;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public void diagnosePatient() {
        System.out.println(name + " is diagnosing a patient.");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Specialization: " + specialization);
        System.out.println("Available: " + availability);
    }
}