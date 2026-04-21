package Package1.report;

/**
 * Refined Abstraction: Financial-specific report.
 * - Implements Template Method steps (drawHeader, insertMainData, etc.)
 * - Uses the Bridge (formatter) to format each section.
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
    protected void drawHeader() {
        formatter.formatHeader("Official Hospital Financial Statement");
    }

    @Override
    protected void insertMainData() {
        formatter.formatBody("Fiscal Revenue: " + revenue + " EGP\n" +
                             "Total Expenses: " + expenses + " EGP");
    }

    @Override
    protected void addDetails() {
        formatter.formatBody("Net Operating Profit: " + (revenue - expenses) + " EGP");
    }

    @Override
    protected void finalSignature() {
        formatter.formatFooter("Audit Verified | Official Financial Record.");
    }
}
