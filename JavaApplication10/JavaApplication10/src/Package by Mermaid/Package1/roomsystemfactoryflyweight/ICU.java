package Package1.roomsystemfactoryflyweight;

import Package3.*;

public class ICU implements Room {

    public int roomId;
    public double daysBooked;
    public double dailyRate = 500.0; // Example daily rate for ICU rooms
    public RoomStatus occupancyStatus;
    private RoomSharedData sharedData;

    public ICU() {
        this.sharedData = RoomFlyweightFactory.getRoomSharedData(RoomType.ICU);
        this.occupancyStatus = RoomStatus.Available;
    }

    public ICU(int roomId, RoomStatus occupancyStatus) {
        this.roomId = roomId;
        this.occupancyStatus = occupancyStatus;
        this.sharedData = RoomFlyweightFactory.getRoomSharedData(RoomType.ICU);
    }

    @Override
    public void markOccupied(int roomID) {
        occupancyStatus = RoomStatus.Occupied;
        RoomDAO roomdao = RoomDAO.getInstance();
        try {
            roomdao.markRoomOccupied(roomID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void markAvailable() {
        occupancyStatus = RoomStatus.Available;
        RoomDAO roomdao = RoomDAO.getInstance();
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
    public double setDays(int days) {
        this.daysBooked = days;
        return daysBooked;
    }

    @Override
    public double getDays() {
        return daysBooked;
    }

    public double getDailyRate() {
        return dailyRate;
    }
    
    @Override
    public int getRoomID() {
        return roomId;
    }

    @Override
    public String toString() {
        return "Room Details [" +
                "ID: '" + roomId + '\'' +
                ", Type: '" + sharedData.getRoomType() + '\'' +
                ", Capacity: '" + sharedData.getCapacity() + '\'' +
                ", Status: '" + occupancyStatus + '\'' +
                ", Daily Rate: " + sharedData.getDailyRate() +
                ']';
    }
}