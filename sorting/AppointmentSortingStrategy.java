package Package1.hospitalservice.sorting;

import java.util.List;

public interface AppointmentSortingStrategy {
    List<AppointmentData> sort(List<AppointmentData> appointments);
}
