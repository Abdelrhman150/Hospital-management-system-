package Package1;

import Package2.*;

public class Doctor extends User {
    private String specialization;
    private boolean availability;
    private double consultationFee;
    private DoctorSalary salary;

    private double savedSalary;
    private String savedSalaryDescription;

    public Doctor(String id, String name, String phone, String email,
            String specialization, boolean availability, double consultationFee) {
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

    public void setSalary(DoctorSalary salary) {
        this.salary = salary;
    }

    public DoctorSalary getSalary() {
        return salary;
    }

    public double getSavedSalary() {
        return savedSalary;
    }

    public void setSavedSalary(double savedSalary) {
        this.savedSalary = savedSalary;
    }

    public String getSavedSalaryDescription() {
        return savedSalaryDescription;
    }

    public void setSavedSalaryDescription(String savedSalaryDescription) {
        this.savedSalaryDescription = savedSalaryDescription;
    }

    public void diagnosePatient() {
        System.out.println(name + " is diagnosing a patient.");
    }

    public void viewSalary() {
        if (salary == null) {
            System.out.println("Salary has not been assigned yet.");
            return;
        }

        System.out.println("\n========== SALARY BREAKDOWN ==========");
        System.out.println(salary.getDescription());
        System.out.println("--------------------------------------");
        System.out.println("Total Salary: " + salary.calculateSalary());
        System.out.println("======================================");
    }

    public void viewSavedSalaryFromDatabase() {
        System.out.println("\n========== SAVED SALARY ==========");
        if (savedSalaryDescription != null && !savedSalaryDescription.isEmpty()) {
            System.out.println(savedSalaryDescription);
            System.out.println("--------------------------------------");
        }
        System.out.println("Saved Salary: " + savedSalary);
        System.out.println("==================================");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Specialization: " + specialization);
        System.out.println("Available: " + availability);
    }
}