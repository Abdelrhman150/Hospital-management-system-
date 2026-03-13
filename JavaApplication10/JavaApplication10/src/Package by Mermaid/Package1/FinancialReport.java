package Package1;

import Package2.ReportFormatter;

/**
 * Refined Abstraction: Financial-specific report.
 * This class uses real-time data from SQL Server to present financial information.
 */
public class FinancialReport extends Report {
    private double revenue;
    private double expenses;

    public FinancialReport(double revenue, double expenses, ReportFormatter formatter) {
        super(formatter);
        this.revenue = revenue;
        this.expenses = expenses;
    }

    @Override
    public void generate() {
        formatter.formatHeader("Official Hospital Financial Statement");
        formatter.formatBody("Fiscal Revenue: " + revenue + " EGP\n" +
                             "Total Expenses: " + expenses + " EGP\n" +
                             "Net Operating Profit: " + (revenue - expenses) + " EGP");
        formatter.formatFooter("Audit Verified | Official Financial Record.");
    }
}
