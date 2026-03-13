package Package1;

import Package2.ReportFormatter;

/**
 * The Abstraction in the Bridge Pattern.
 * This class holds a reference to the Implementor (ReportFormatter).
 */
public abstract class Report {
    protected ReportFormatter formatter;

    protected Report(ReportFormatter formatter) {
        this.formatter = formatter;
    }

    /**
     * المسؤولية: ملء محتوى التقرير (Header, Body, Footer) عبر المنسق.
     */
    protected abstract void generate();

    /**
     * شرح الحل: Entity لا يجب أن تطبع مباشرة.
     * بدلاً من ذلك، تعيد النص المنسق، والـ UI هو المسؤول عن عرضه.
     */
    public String getFullContent() {
        // نضمن توليد المحتوى أولاً
        this.generate();
        
        // نعيد النص المنسق بدلاً من الطباعة
        return formatter.getFormattedReport();
    }
}
