package Package1.roomsystemfactoryflyweight;

import Package3.*;

public class GeneralRoom implements Room {

    public String roomId;
    public double daysBooked;
    public double dailyRate = 100.0; // Example daily rate for general rooms
    public RoomStatus occupancyStatus;
    private RoomSharedData sharedData;

    public GeneralRoom() {
        this.sharedData = RoomFlyweightFactory.getRoomSharedData(RoomType.GeneralWard);
        this.occupancyStatus = RoomStatus.Available;
    }

    public GeneralRoom(String roomId, RoomStatus occupancyStatus) {
        this.roomId = roomId;
        this.occupancyStatus = occupancyStatus;
        this.sharedData = RoomFlyweightFactory.getRoomSharedData(RoomType.GeneralWard);
    }

    @Override
    public void markOccupied(String roomID) {
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
    public String getRoomID() {
        return roomId;
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