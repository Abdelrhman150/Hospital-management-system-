package Package1.staff;

public class AdminCollection implements StaffCollection {
    private static final int MAX_ITEMS = 10;
    private int numberOfItems = 0;
    private User[] admins;

    public AdminCollection() {
        admins = new User[MAX_ITEMS];
    }

    public void addAdmin(User admin) {
        if (numberOfItems >= MAX_ITEMS) {
            System.err.println("Collection is full!");
        } else {
            admins[numberOfItems] = admin;
            numberOfItems++;
        }
    }

    @Override
    public StaffIterator createIterator() {
        return new AdminIterator();
    }

   
    private class AdminIterator implements StaffIterator {
        private int position = 0;

        @Override
        public boolean hasNext() {
            return position < numberOfItems && admins[position] != null;
        }

        @Override
        public User next() {
            if (this.hasNext()) {
                return admins[position++];
            }
            return null;
        }
    }
}
