package Package1.staff;
import Package4.*;

public class StaffController {

    public StaffCreationResult createMedicalStaff(String name, String phone, String email,
                                                  String specialization, boolean availability,
                                                  double consultationFee,
                                                  String departmentId, String departmentName,
                                                  int floorNumber, String headDoctorId) {

        Department medicalDepartment = new MedicalDepartment(
                departmentId,
                departmentName,
                floorNumber,
                headDoctorId
        );

        StaffFactory factory = new MedicalStaff(
                name,
                phone,
                email,
                specialization,
                availability,
                consultationFee,
                medicalDepartment
        );

        User user = factory.createUser();
        Department department = factory.createDepartment();

        return new StaffCreationResult(user, department);
    }

    public StaffCreationResult createAdministrativeStaff(String name, String phone, String email,
                                                         String shift,
                                                         String departmentId, String departmentName,
                                                         int floorNumber, String headStaffId) {

        Department administrativeDepartment = new AdministrativeDepartment(
                departmentId,
                departmentName,
                floorNumber,
                headStaffId
        );

        StaffFactory factory = new AdministrativeStaff(
                name,
                phone,
                email,
                shift,
                administrativeDepartment
        );

        User user = factory.createUser();
        Department department = factory.createDepartment();

        return new StaffCreationResult(user, department);
    }
}