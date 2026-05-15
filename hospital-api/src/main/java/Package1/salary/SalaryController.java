package Package1.salary;

import Package1.staff.Doctor;
import Package3.DoctorDAO;

public class SalaryController {

    private DoctorDAO doctorDAO;

    public SalaryController() {
        this.doctorDAO = DoctorDAO.getInstance();
    }

    public Doctor getDoctorById(String doctorId) throws Exception {
        return doctorDAO.getDoctorById(doctorId);
    }

    public DoctorSalary buildSalary(int nightShifts, int onCallDays, boolean hasHazard) {
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

        return salary;
    }

    public Doctor assignSalaryToDoctor(String doctorId, int nightShifts, int onCallDays, boolean hasHazard) throws Exception {
        Doctor doctor = doctorDAO.getDoctorById(doctorId);

        if (doctor == null) {
            return null;
        }

        DoctorSalary salary = buildSalary(nightShifts, onCallDays, hasHazard);

        doctor.setSalary(salary);
        doctor.setSavedSalary(salary.calculateSalary());
        doctor.setSavedSalaryDescription(salary.getDescription());

        doctorDAO.saveDoctorSalary(
                doctorId,
                salary.calculateSalary(),
                salary.getDescription()
        );

        return doctor;
    }
}