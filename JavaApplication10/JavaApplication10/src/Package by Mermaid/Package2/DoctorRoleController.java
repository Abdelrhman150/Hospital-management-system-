package Package2;

import Package1.*;
import Package1.DoctorRole.*;
import Package3.*;
import java.util.ArrayList;

public class DoctorRoleController {

    private DoctorDAO doctorDAO;
    private DoctorRoleDAO roleDAO;

    public DoctorRoleController() {
        this.doctorDAO = DoctorDAO.getInstance();
        this.roleDAO = DoctorRoleDAO.getInstance();
    }

    public DoctorRoleController(DoctorDAO doctorDAO, DoctorRoleDAO roleDAO) {
        this.doctorDAO = doctorDAO;
        this.roleDAO = roleDAO;
    }

    public DoctorService getDecoratedDoctor(String doctorId) throws Exception {
        Doctor docModel = doctorDAO.getDoctorById(doctorId);
        if (docModel != null) {
            String name = docModel.getName();
            String specialization = docModel.getSpecialization();

            DoctorService doctor = new BasicDoctor(name, specialization);

            ArrayList<String> roles = roleDAO.getRolesForDoctor(doctorId);

            if (roles != null) {
                for (String role : roles) {
                    doctor = addRoleToDoctor(doctor, role);
                }
            }
            return doctor;
        }
        return null; 
    }

   
    private DoctorService addRoleToDoctor(DoctorService doctor, String role) {
        if (role.equalsIgnoreCase("Surgeon")) {
            return new SurgeonDecorator(doctor);
        } else if (role.equalsIgnoreCase("Head of Department")) {
            return new HeadOfDepartmentDecorator(doctor);
        } else if (role.equalsIgnoreCase("On-call")) {
            return new OnCallDoctorDecorator(doctor);
        }
        return doctor; 
    }


    public void assignRole(String doctorId, String roleName) throws Exception {
        roleDAO.assignRoleToDoctor(doctorId, roleName);
    }

  
    public void removeRole(String doctorId, String roleName) throws Exception {
        roleDAO.removeRoleFromDoctor(doctorId, roleName);
    }
}