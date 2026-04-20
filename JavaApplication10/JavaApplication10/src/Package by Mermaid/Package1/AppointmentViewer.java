package Package1;

import Package1.hospitalservice.RoomAppointment;
import java.util.List;

/**
 * =============================================
 *  CONTEXT CLASS: AppointmentViewer
 * =============================================
 * في Strategy Pattern، هذا الـ class هو الـ "Context".
 *
 * الـ Context بيحتفظ بـ reference للـ strategy الحالية
 * ويفوّض إليها عملية الـ sort.
 * مش بيعرف ولا بيهتم ايه الـ strategy المستخدمة —
 * هو بس بيستدعي sort() ويثق إن الـ strategy هتتكفل.
 *
 * يشتغل مع RoomAppointment — الكلاس الأصلي في البروجيكت.
 */
public class AppointmentViewer {

    // الـ strategy الحالية — ممكن تتغير في أي وقت
    private AppointmentSorting strategy;

    /**
     * Constructor: محتاج strategy ابتدائية عند الإنشاء.
     * @param strategy الـ strategy الأولى اللي هتتستخدم
     */
    public AppointmentViewer(AppointmentSorting strategy) {
        this.strategy = strategy;
        System.out.println("AppointmentViewer initialized → Strategy: "
                + strategy.getClass().getSimpleName());
    }

    /**
     * تغيير الـ Strategy في runtime — ده سحر الـ Strategy Pattern!
     * تقدر تستدعي الميثود دي في أي وقت لتغيير طريقة الـ sorting.
     *
     * @param strategy الـ strategy الجديدة
     */
    public void setStrategy(AppointmentSorting strategy) {
        System.out.println("[ Strategy switched → " + strategy.getClass().getSimpleName() + " ]");
        this.strategy = strategy;
    }

    /**
     * بيعمل sort للقائمة باستخدام الـ strategy الحالية
     * ثم بيطبع النتائج على الـ console.
     *
     * @param appointments قائمة الـ RoomAppointment اللي هتتعمل sort
     */
    public void displaySorted(List<RoomAppointment> appointments) {
        // تفويض الـ sort للـ strategy المختارة
        strategy.sort(appointments);

        System.out.println("\n══════════════════════════════════════════════");
        System.out.println(" Sorted by: " + strategy.getClass().getSimpleName());
        System.out.println("══════════════════════════════════════════════");

        for (RoomAppointment appt : appointments) {
            // تحديد الـ label الخاص بالأولوية
            String priority = (appt.getPriorityLevel() == 1) ? "🚨 Emergency" : "   Normal   ";
            String dept     = (appt.getDepartmentName() != null) ? appt.getDepartmentName() : "N/A";
            String date     = (appt.appointmentDate    != null) ? appt.appointmentDate     : "N/A";

            System.out.printf("  [%s] Patient: %-12s | Date: %s | Priority: %s | Dept: %s%n",
                    appt.getAppointmentId(),
                    appt.getPatientId(),
                    date,
                    priority,
                    dept);
        }
        System.out.println("══════════════════════════════════════════════\n");
    }
}
