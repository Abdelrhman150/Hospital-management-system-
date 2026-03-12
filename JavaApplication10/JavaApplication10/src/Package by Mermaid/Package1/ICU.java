package Package1;
import Package3.*;

public class ICU implements Room {

    public int roomId;
    public int Capacity;
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

    public ICU(int roomId, int capacity, RoomType roomType, RoomStatus occupancyStatus, double dailyRate) {
        this.roomId = roomId;
        this.Capacity = capacity;
        this.roomType = roomType;
        this.occupancyStatus = occupancyStatus;
        this.dailyRate = dailyRate;
    }

    @Override
    public void markOccupied(int roomID) {
        occupancyStatus = RoomStatus.Occupied;
            RoomDAO roomdao = RoomDAO.getInstance() ;
        try {
            roomdao.markRoomOccupied(roomID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void markAvailable() {
        occupancyStatus = RoomStatus.Available;
            RoomDAO roomdao = RoomDAO.getInstance() ;
        try {           
             roomdao.markRoomAvailable(roomId);
        } catch (Exception e) {
            e.printStackTrace();
        }
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