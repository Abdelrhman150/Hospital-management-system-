package Package2;

import Package1.MedicalRecord;
import Package3.MedicalRecordDAO;
import java.sql.ResultSet;

/**
 * Controller for Medical Record viewing logic using Bridge Pattern.
 * Bridges the gap between "WHAT to view" (RecordView) and "WHERE to display" (DisplayPlatform).
 */
public class MedicalRecordBridgeController {

    private MedicalRecordDAO dao = MedicalRecordDAO.getInstance();

    /**
     * View a medical record using specific platform and view type.
     * 
     * @param recordId ID of the record in DB
     * @param platformType "web" or "desktop"
     * @param viewType "doctor" or "patient"
     */
    public void viewRecord(int recordId, String platformType, String viewType) {
        try {
            // 1. Fetch data from DB
            ResultSet rs = dao.getFullRecord(recordId);
            if (rs.next()) {
                MedicalRecord model = dao.mapToModel(rs);

                // 2. Choose Display Platform (Implementor)
                DisplayPlatform platform;
                if (platformType.equalsIgnoreCase("web")) {
                    platform = new WebDisplay();
                } else {
                    platform = new DesktopDisplay();
                }

                // 3. Choose Record View (Abstraction)
                RecordView view;
                if (viewType.equalsIgnoreCase("doctor")) {
                    view = new DoctorView(platform);
                } else {
                    view = new PatientView(platform);
                }

                // 4. Display
                view.display(model);

            } else {
                System.out.println("Error: Medical Record ID " + recordId + " not found.");
            }
        } catch (Exception e) {
            System.err.println("Bridge Controller Error: " + e.getMessage());
        }
    }
}
