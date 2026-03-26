package Package1;

import Package3.DoctorDAO;

public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(String id, String name, String phone, String email) {
        super(id, name, phone, email);
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    public void assignSalaryToDoctor(Doctor doctor,
                                     int nightShifts,
                                     int onCallDays,
                                     boolean hasHazard) throws Exception {

        DoctorSalary salary = new BaseDoctorSalary();

        if (nightShifts > 0) {
            salary = new NightShiftBonusDecorator(salary, nightShifts);
        }

        if (onCallDays > 0) {
            salary = new OnCallAllowanceDecorator(salary, onCallDays);
        }

        if (hasHazard) {
            salary = new HazardAllowanceDecorator(salary);
        }

        doctor.setSalary(salary);

        double finalSalary = salary.calculateSalary();
        String description = salary.getDescription();

        DoctorDAO.getInstance().saveDoctorSalary(doctor.getId(), finalSalary, description);

        doctor.setSavedSalary(finalSalary);
        doctor.setSavedSalaryDescription(description);

        System.out.println("\nSalary assigned and saved successfully to Dr. " + doctor.getName());
    }
}