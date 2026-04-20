package Package1.hospitalservice.sorting;

import java.util.List;
import java.util.stream.Collectors;

public class SortByDateStrategy implements AppointmentSortingStrategy {

    @Override
    public List<AppointmentData> sort(List<AppointmentData> appointments) {
        System.out.println("sorting by date :");
        return appointments.stream()
                .sorted((a1, a2) -> a1.appointmentTime.compareTo(a2.appointmentTime))
                .collect(Collectors.toList());
    }
}
