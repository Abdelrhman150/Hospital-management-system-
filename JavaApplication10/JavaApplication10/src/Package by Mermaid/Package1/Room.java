package Package1;

public interface Room {
    public void markOccupied(int roomID);
    public void markAvailable();
    public void markUnderMaintenance();
    public double calculateCost(int days);
    //creation of room 
    // 
}
