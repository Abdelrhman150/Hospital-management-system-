package Package1;

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
                                     double baseSalary,
                                     int nightShifts, double bonusPerShift,
                                     int onCallDays, double onCallAllowancePerDay,
                                     boolean hasHazardAllowance, String department, double hazardAmount) {

        DoctorSalary salary = new BaseDoctorSalary(baseSalary);

        if (nightShifts > 0) {
            salary = new NightShiftBonusDecorator(salary, nightShifts, bonusPerShift);
        }

        if (onCallDays > 0) {
            salary = new OnCallAllowanceDecorator(salary, onCallDays, onCallAllowancePerDay);
        }

        if (hasHazardAllowance) {
            salary = new HazardAllowanceDecorator(salary, department, hazardAmount);
        }

        doctor.setSalary(salary);
        System.out.println("Salary assigned successfully to Dr. " + doctor.getName());
    }
}