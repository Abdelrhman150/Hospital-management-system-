package Package1.sorting;

import java.util.List;

public class AppointmentSortingContext {
    private AppointmentSortingStrategy strategy;

    public AppointmentSortingContext() {
        this.strategy = new SortByDateStrategy();
    }

    public void setStrategy(AppointmentSortingStrategy strategy) {
        this.strategy = strategy;
    }

    public List<AppointmentData> execute(List<AppointmentData> appointments) {
        return strategy.sort(appointments);
    }
}
