package Package1;

public class ICU implements Room {

    public int roomId;
    public RoomType roomType;
    public RoomStatus occupancyStatus;
    public double dailyRate;

    // Constructor
    public ICU() {
        // waits for the roomID to be assigned by ID generator
        roomType = RoomType.ICU;
        occupancyStatus = RoomStatus.Available;
        dailyRate = 500.0; 
    }

    @Override
    public void markOccupied(int roomID) {
    
        occupancyStatus = RoomStatus.Occupied;
    }

    @Override
    public void markAvailable() {
        occupancyStatus = RoomStatus.Available; 
    }

    @Override
    public void markUnderMaintenance() {
        occupancyStatus = RoomStatus.UnderMaintenance; 
    }

    @Override
    public double calculateCost(int days) {
        double totalCost = dailyRate * days;
        return totalCost;
    }

    @Override
    public String toString() {
        return "Room Details [" +
                "ID: '" + roomId + '\'' +
                ", Type: '" + roomType + '\'' +
                ", Status: '" + occupancyStatus + '\'' +
                ", Daily Rate: " + dailyRate +
                ']';
    }

}