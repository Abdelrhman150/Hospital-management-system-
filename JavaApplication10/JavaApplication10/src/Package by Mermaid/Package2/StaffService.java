package Package2;

import Package1.*;
import Package3.*;

import java.sql.ResultSet;

public class StaffService {

    public void createAndSaveDoctor(String name, String phone, String email,
            String specialization, boolean availability, double consultationFee, String departmentId) {
        try {
            // prepare el database
            DoctorDAO doctorDAO = DoctorDAO.getInstance();
            DepartmentDAO departmentDAO = DepartmentDAO.getInstance();
            // make sure en el department mawgood
            ResultSet rs = departmentDAO.getDepartmentById(departmentId);
            // lw msh mawgood yegib error
            if (!rs.next()) {
                System.out.println("Department not found.");
                return;
            }
            // get el data bta3el el department 3shan a creat Medical Department
            String departmentName = rs.getString("name");
            int floorNumber = rs.getInt("floorNumber");
            String headDoctorId = rs.getString("headDoctorId");
            Department department = new MedicalDepartment(departmentId, departmentName, floorNumber, headDoctorId);
            // create factory
            StaffFactory factory = new MedicalStaff(
                    name,
                    phone,
                    email,
                    specialization,
                    availability,
                    consultationFee,
                    department);
            // create user
            User user = factory.createUser();
            // check if user is doctor

            Doctor doctor = (Doctor) user;

            doctorDAO.addDoctor(
                    doctor.getId(),
                    doctor.getName(),
                    doctor.getSpecialization(),
                    doctor.getEmail(),
                    doctor.getConsultationFee());

            System.out.println("Doctor added successfully.");
            doctor.displayInfo();
            department.displayDepartment();

        } catch (Exception e) {
            System.out.println("Error while saving doctor: " + e.getMessage());
        }
    }

    public void createAndSaveSecretary(String name, String phone, String email,
            String shift, String departmentId) {
        try {
            SecretaryDAO secretaryDAO = SecretaryDAO.getInstance();
            DepartmentDAO departmentDAO = DepartmentDAO.getInstance();

            String newSecretaryId = IdGenerator.getInstance().nextSecretaryId();

            ResultSet rs = departmentDAO.getDepartmentById(departmentId);

            if (!rs.next()) {
                System.out.println("Department not found.");
                return;
            }

            String departmentName = rs.getString("name");

            int floorNumber = rs.getInt("floorNumber");
            String headDoctorId = rs.getString("headDoctorId");
            Department department = new AdministrativeDepartment(departmentId, departmentName, floorNumber,
                    headDoctorId);

            StaffFactory factory = new AdministrativeStaff(
                    newSecretaryId,
                    name,
                    phone,
                    email,
                    shift,
                    department);

            User user = factory.createUser();

            Secretary secretary = (Secretary) user;

            secretaryDAO.addSecretary(
                    secretary.getId(),
                    secretary.getName(),
                    secretary.getPhone(),
                    secretary.getEmail(),
                    secretary.getShift(),
                    department.getDepartmentId());

            System.out.println("Secretary added successfully.");
            secretary.displayInfo();
            department.displayDepartment();

        } catch (Exception e) {
            System.out.println("Error while saving secretary: " + e.getMessage());
        }
    }
}