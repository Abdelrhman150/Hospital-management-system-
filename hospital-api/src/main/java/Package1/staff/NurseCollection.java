package Package1.staff;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class NurseCollection implements StaffCollection {
    private Set<User> nurses = new HashSet<>();

    public void addNurse(User nurse) {
        nurses.add(nurse);
    }

    @Override
    public StaffIterator createIterator() {
        return new NurseIterator();
    }

    
    private class NurseIterator implements StaffIterator {
        private Iterator<User> iterator = nurses.iterator();

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public User next() {
            if (this.hasNext()) {
                return iterator.next();
            }
            return null;
        }
    }
}
