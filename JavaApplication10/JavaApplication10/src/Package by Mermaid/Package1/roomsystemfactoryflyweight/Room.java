package Package1.roomsystemfactoryflyweight;

public interface Room {
    public void markOccupied(int roomID);

    public void markAvailable();

    public void markUnderMaintenance();

    public double setDays(int days);

    public double getDays();
    
    public double getDailyRate();

    public int getRoomID();
}
