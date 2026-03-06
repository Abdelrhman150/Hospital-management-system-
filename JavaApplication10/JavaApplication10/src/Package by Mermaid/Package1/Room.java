package Package1;

public interface Room {
    public void markOccupied();
    public void markAvailable();
    public void markUnderMaintenance();
    public double calculateCost(int days);
}
