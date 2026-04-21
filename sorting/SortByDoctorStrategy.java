package Package1.hospitalservice.sorting;

import java.util.List;
import java.util.stream.Collectors;

public class SortByDoctorStrategy implements AppointmentSortingStrategy {

    @Override
    public List<AppointmentData> sort(List<AppointmentData> appointments) {
        System.out.println("sorting by doctor...");
        return appointments.stream()
                .sorted((a1, a2) -> {
                    int doc = a1.doctorName.compareTo(a2.doctorName);
                    if (doc != 0)
                        return doc;
                    return a1.appointmentTime.compareTo(a2.appointmentTime);
                })
                .collect(Collectors.toList());
    }
}
