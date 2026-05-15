package Package1.staff;

import Package1.salary.*;
import Package2.DoctorController;

public class Doctor extends User {
    private String specialization;
    private String departmentId;
    private DoctorSalary salary;
    private DoctorController doctorController;
    private double savedSalary;
    private String savedSalaryDescription;

    public Doctor(String id, String name, String phone, String email, String specialization, String departmentId) {
        super(id, name, phone, email);
        this.specialization = specialization;
        this.departmentId = departmentId;
    }

    @Override
    public String getRole() {
        return "Doctor";
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
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
        System.out.println("Department ID: " + departmentId);
    }

    public boolean getAvailability() {
        try {
            boolean isAvailable = doctorController.getAvailability(this.id);
            System.out.println("Doctor " + name + " is " + (isAvailable ? "available" : "not available"));
            return isAvailable;
        } catch (Exception e) {
            System.out.println("Error occurred while checking doctor availability.");
            return false;
        }
    }


}
        