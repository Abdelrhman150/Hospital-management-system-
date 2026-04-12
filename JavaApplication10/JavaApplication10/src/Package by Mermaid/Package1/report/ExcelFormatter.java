package Package1.report;

/**
 * Concrete Implementor: Excel format.
 * This class handles the specific logic for Excel-like (CSV/Tab-separated) formatting.
 */
public class ExcelFormatter implements ReportFormatter {
    private StringBuilder report = new StringBuilder();

    @Override
    public void formatHeader(String title) {
        report.setLength(0); 
        report.append("[EXCEL SHEET]: ").append(title).append("\n");
        report.append("==================================================\n");
        report.append("COLUMN_A\t|\tCOLUMN_B\n");
        report.append("--------------------------------------------------\n");
    }

    @Override
    public void formatBody(String content) {
        // Split lines and format as "cells"
        String[] lines = content.split("\n");
        for (String line : lines) {
            if (line.contains(":")) {
                String[] parts = line.split(":", 2);
                report.append(parts[0].trim()).append("\t|\t").append(parts[1].trim()).append("\n");
            } else {
                report.append(line.trim()).append("\t|\t\n");
            }
        }
    }

    @Override
    public void formatFooter(String footer) {
        report.append("--------------------------------------------------\n");
        report.append("[EXCEL FOOTER]\t|\t").append(footer).append("\n");
        report.append("==================================================\n");
    }

    @Override
    public String getFormattedReport() {
        return report.toString();
    }

    @Override
    public void saveToFile(String fileName) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.File(fileName))) {
            // Using CSV format for real Excel compatibility
            String csvContent = report.toString().replace("\t|\t", ",");
            writer.write(csvContent);
            System.out.println("Excel file saved as: " + fileName);
            // Open the file automatically
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().open(new java.io.File(fileName));
            }
        } catch (Exception e) {
            System.err.println("Error saving Excel: " + e.getMessage());
        }
    }
}
