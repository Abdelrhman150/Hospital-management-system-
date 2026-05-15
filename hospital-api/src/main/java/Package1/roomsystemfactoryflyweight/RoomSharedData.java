package Package1.roomsystemfactoryflyweight;

public class RoomSharedData {
    private RoomType roomType;
    private int capacity;
    private double dailyRate;

    public RoomSharedData(RoomType roomType, int capacity, double dailyRate) {
        this.roomType = roomType;
        this.capacity = capacity;
        this.dailyRate = dailyRate;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getDailyRate() {
        return dailyRate;
    }
}