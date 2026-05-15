package Package1.sorting;

import java.util.List;
import java.util.stream.Collectors;

public class SortByTypeStrategy implements AppointmentSortingStrategy {

    @Override
    public List<AppointmentData> sort(List<AppointmentData> appointments) {
        System.out.println("sorting by type...");
        return appointments.stream()
                .sorted((a1, a2) -> {
                    if ("Visiting".equals(a1.type) && !"Visiting".equals(a2.type))
                        return -1;
                    if (!"Visiting".equals(a1.type) && "Visiting".equals(a2.type))
                        return 1;
                    return a1.appointmentTime.compareTo(a2.appointmentTime);
                })
                .collect(Collectors.toList());
    }
}
