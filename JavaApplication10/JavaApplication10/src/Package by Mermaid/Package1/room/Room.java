package Package1.room;

public interface Room {
    public void markOccupied(String roomID);

    public void markAvailable();

    public void markUnderMaintenance();

    public double setDays(int days);

    public double getDays();
    
    public double getDailyRate();

    public String getRoomID();

    public String getAvailabilityStatus();

    String getRoomType();

    int getCapacity();

    void setRoomID(String roomId);
}
