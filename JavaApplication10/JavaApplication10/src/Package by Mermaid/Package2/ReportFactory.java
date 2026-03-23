package Package2;

import Package1.FinancialReport;
import Package1.PatientReport;
import Package1.Report;


/**
 * ReportFactory: تقليبر فصل منطق إنشاء الكلاسات.
 */
public class ReportFactory {

    public static Report createPatientReport(String patientName, String diagnosis, ReportFormatter formatter) {
        return new PatientReport(patientName, diagnosis, formatter);
    }


    public static Report createFinancialReport(double revenue, double expenses, ReportFormatter formatter) {
        return new FinancialReport(revenue, expenses, formatter);
    }
}
