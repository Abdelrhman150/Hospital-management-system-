package Package2;

import Package1.FinancialReport;
import Package1.PatientReport;
import Package1.Report;
import Package3.ReportDAO;

/**
 * Controller for Report-related operations.
 * Coordinates between Entities, DAO, and View.
 * No hardcoded values.
 */
public class ReportController {

    private ReportDAO reportDAO;

    public ReportController() {
        this.reportDAO = ReportDAO.getInstance();
    }

  
    public Report createPatientReport(int patientId, ReportFormatter formatter) {
        try {
            
            String[] info = reportDAO.getLatestPatientInfo(patientId);
            
            
            if (info == null || info.length < 2 || info[0] == null || info[0].equals("Unknown")) {
                throw new Exception("Patient data not found for ID: " + patientId);
            }
            
            String patientName = info[0];
            String diagnosis = info[1];
            
            
            return new PatientReport(patientName, diagnosis, formatter);
        } catch (Exception e) {
            System.err.println("✗ Error creating report: " + e.getMessage());
            return null;
        }
    }

    public Report createFinancialReport(ReportFormatter formatter) {
        try {
            double[] metrics = reportDAO.getFinancialSummaryDataMetrics();
            
            
            return ReportFactory.createFinancialReport(metrics[0], metrics[1], formatter);
        } catch (Exception e) {
            System.err.println("✗ Error creating report: " + e.getMessage());
            return null;
        }
    }
}
