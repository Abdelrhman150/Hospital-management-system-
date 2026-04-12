package Package2;

import Package1.report.*;


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
