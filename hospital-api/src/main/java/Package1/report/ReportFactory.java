package Package1.report;

/**
 * Factory for creating different types of reports.
 */
public class ReportFactory {

    public static Report createPatientReport(String patientName, String diagnosis, ReportFormatter formatter) {
        return new PatientReport(patientName, diagnosis, formatter);
    }

    public static Report createFinancialReport(double revenue, double expenses, ReportFormatter formatter) {
        return new FinancialReport(revenue, expenses, formatter);
    }
}
