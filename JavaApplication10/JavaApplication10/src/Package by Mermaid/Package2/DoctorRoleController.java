package Package2;

import Package1.*;
import Package3.*;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Controller to manage doctor roles dynamically using Decorator Pattern.
 */
public class DoctorRoleController {

    private DoctorDAO doctorDAO = DoctorDAO.getInstance();
    private DoctorRoleDAO roleDAO = DoctorRoleDAO.getInstance();

    /**
     * Retrieves a doctor and wraps them with their assigned roles from the database.
     */
    public DoctorService getDecoratedDoctor(int doctorId) throws Exception {
        ResultSet rs = doctorDAO.getDoctorById(doctorId);
        if (rs.next()) {
            String name = rs.getString("name");
            String specialization = rs.getString("specialization");

            // 1. Create the BasicDoctor
            DoctorService doctor = new BasicDoctor(name, specialization);

            // 2. Fetch roles from database
            ArrayList<String> roles = roleDAO.getRolesForDoctor(doctorId);

            // 3. Wrap with decorators dynamically
            for (int i = 0; i < roles.size(); i++) {
                String role = roles.get(i);
                
                if (role.equalsIgnoreCase("Surgeon")) {
                    doctor = new SurgeonDecorator(doctor);
                } else if (role.equalsIgnoreCase("Head of Department")) {
                    doctor = new HeadOfDepartmentDecorator(doctor);
                } else if (role.equalsIgnoreCase("On-call")) {
                    doctor = new OnCallDoctorDecorator(doctor);
                }
            }
            return doctor;
        }
        return null;
    }

    /**
     * Saves a new role to the database for a doctor.
     */
    public void assignRole(int doctorId, String roleName) throws Exception {
        roleDAO.assignRoleToDoctor(doctorId, roleName);
    }

    /**
     * Removes a role from the database for a doctor.
     */
    public void removeRole(int doctorId, String roleName) throws Exception {
        roleDAO.removeRoleFromDoctor(doctorId, roleName);
    }
}
