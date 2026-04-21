package Package2;

import Package1.staff.Doctor;
import Package3.DoctorDAO;

/**
 * Controller for doctor operations.
 */
public class DoctorController {
    DoctorDAO doctorDAO;
        public boolean getAvailability(String doctorId ) throws Exception {
            return doctorDAO.isAvailable(doctorId);
            
    }
}