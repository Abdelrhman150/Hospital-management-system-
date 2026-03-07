package Package1;

public class Secretary extends User {
    private String shift;

    public Secretary(int id, String name, String phone, String email, String shift) {
        super(id, name, phone, email);
        this.shift = shift;
    }

    @Override
    public String getRole() {
        return "Secretary";
    }

    public String getShift() {
        return shift;
    }

    public void bookAppointment() {
        System.out.println(name + " booked an appointment.");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Shift: " + shift);
    }
}