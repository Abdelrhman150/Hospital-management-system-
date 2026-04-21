package Package1.staff;

import java.util.ArrayList;
import java.util.List;


public class DoctorCollection implements StaffCollection {
    private List<User> doctors = new ArrayList<>();

    public void addDoctor(User doctor) {
        doctors.add(doctor);
    }

    @Override
    public StaffIterator createIterator() {
        return new DoctorIterator();
    }


    private class DoctorIterator implements StaffIterator {
        private int position = 0;

        @Override
        public boolean hasNext() {
            return position < doctors.size();
        }

        @Override
        public User next() {
            if (this.hasNext()) {
                return doctors.get(position++);
            }
            return null;
        }
    }
}
