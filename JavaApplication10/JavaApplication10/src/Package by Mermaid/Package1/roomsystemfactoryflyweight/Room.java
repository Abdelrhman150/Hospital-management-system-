package Package1.roomsystemfactoryflyweight;

public interface Room {
    public void markOccupied(String roomID);

    public void markAvailable();

    public void markUnderMaintenance();

    public double setDays(int days);

    public double getDays();
    
    public double getDailyRate();

    public String getRoomID();

    public String getAvailabilityStatus();
}
