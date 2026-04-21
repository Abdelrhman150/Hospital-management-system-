package Package1.hospitalservice.sorting;

import java.util.List;
import java.util.stream.Collectors;

public class SortByPriorityStrategy implements AppointmentSortingStrategy {

    @Override
    public List<AppointmentData> sort(List<AppointmentData> appointments) {
        System.out.println("sorting by priority...");
        return appointments.stream()
                .sorted((a1, a2) -> Integer.compare(a1.priority, a2.priority))
                .collect(Collectors.toList());
    }
}
