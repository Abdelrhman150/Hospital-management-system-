package Package1.staff;

import Package4.*;
import Package3.DoctorDAO;
import Package3.SecretaryDAO;
import Package3.UserDatabase;
import Package3.DepartmentLookup;

public class StaffController {

    private UserDatabase userDatabase;
    private DoctorDAO doctorDAO;
    private SecretaryDAO secretaryDAO;
    private DepartmentLookup departmentLookup;

    public StaffController() {
        userDatabase = UserDatabase.getInstance();
        doctorDAO = DoctorDAO.getInstance();
        secretaryDAO = SecretaryDAO.getInstance();
        departmentLookup = new DepartmentLookup();
    }

    public boolean createDoctorAndSave(String name, String phone, String email,
                                       String username, String password,
                                       String specialization,
                                       String departmentName) throws Exception {

        Department department = departmentLookup.getMedicalDepartmentByName(departmentName);
        if (department == null) {
            throw new Exception("Medical department not found.");
        }

        StaffFactory factory = new MedicalStaff(
                name,
                phone,
                email,
                specialization,
                department
        );

        Doctor doctor = (Doctor) factory.createUser();

        doctor.setUsername(username);
        doctor.setPassword(password);
        doctor.setPersonId(doctor.getId());
        doctor.setRole("Doctor");

        boolean userSaved = userDatabase.createUser(doctor);
        if (!userSaved) {
            throw new Exception("Failed to save doctor in Users table.");
        }

        doctorDAO.addDoctor(doctor);
        return true;
    }

    public boolean createSecretaryAndSave(String name, String phone, String email,
                                          String username, String password,
                                          String shift, String departmentName) throws Exception {

        Department department = departmentLookup.getAdministrativeDepartmentByName(departmentName);
        if (department == null) {
            throw new Exception("Administrative department not found.");
        }

        StaffFactory factory = new AdministrativeStaff(
                name,
                phone,
                email,
                shift,
                department
        );

        Secretary secretary = (Secretary) factory.createUser();

        secretary.setUsername(username);
        secretary.setPassword(password);
        secretary.setPersonId(secretary.getId());
        secretary.setRole("Secretary");

        boolean userSaved = userDatabase.createUser(secretary);
        if (!userSaved) {
            throw new Exception("Failed to save secretary in Users table.");
        }

            secretaryDAO.addSecretary(
                    secretary.getId(),
                    secretary.getName(),
                    secretary.getPhone(),
                    secretary.getEmail(),
                    secretary.getShift(),
                    department.getDepartmentId()
            );
    
            return true;
        }
    }