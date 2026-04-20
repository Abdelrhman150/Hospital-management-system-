package Package1;

import Package1.hospitalservice.RoomAppointment;
import java.util.List;

/**
 * =============================================
 *  STRATEGY INTERFACE: AppointmentSortingStrategy
 * =============================================
 * هذا هو جوهر Strategy Pattern.
 *
 * الـ interface بيعمل "عقد" — أي class بينفذه
 * لازم يكون عنده method اسمها sort().
 *
 * الـ AppointmentViewer (Context) مش محتاج يعرف
 * ازاي بيتعمل الـ sorting — هو بس بيستدعي sort()
 * وكل strategy بتتكفل بالتفاصيل.
 *
 * النوع المستخدم هنا: RoomAppointment — وهو الـ class
 * الأصلي في البروجيكت اللي بيمثل ميعاد المريض.
 */
public interface AppointmentSorting {

    /**
     * Sorts the given list of RoomAppointment objects in-place.
     * كل strategy بتعمل sort بطريقة مختلفة.
     */
    void sort(List<RoomAppointment> appointments);
}
