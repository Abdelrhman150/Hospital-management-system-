package Package1.room;

import Package1.roomsystemfactoryflyweight.*;
import Package3.RoomDAO;

public class PrivateRoom implements Room {

    public String roomId;
    public double daysBooked;
    public RoomStatus occupancyStatus;
    private RoomSharedData sharedData;

    public PrivateRoom() {
        this.sharedData = RoomFlyweightFactory.getRoomSharedData(RoomType.PrivateRoom);
        this.occupancyStatus = RoomStatus.Available;
    }

    public PrivateRoom(String roomId, RoomStatus occupancyStatus) {
        this.roomId = roomId;
        this.occupancyStatus = occupancyStatus;
        this.sharedData = RoomFlyweightFactory.getRoomSharedData(RoomType.PrivateRoom);
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
    public double setDays(int days) {
        this.daysBooked = days;
        return daysBooked;
    }

    @Override
    public double getDailyRate() {
        return sharedData.getDailyRate();
    }

    @Override
    public String getRoomID() {
        return roomId;
    }

    @Override
    public void setRoomID(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public double getDays() {
        return daysBooked;
    }

    @Override
    public String getRoomType() {
        return sharedData.getRoomType().toString();
    }

    @Override
    public int getCapacity() {
        return sharedData.getCapacity();
    }

    public String toString() {
        return "Room Details [" +
                "ID: '" + roomId + '\'' +
                ", Type: '" + sharedData.getRoomType() + '\'' +
                ", Capacity: '" + sharedData.getCapacity() + '\'' +
                ", Status: '" + occupancyStatus + '\'' +
                ", Daily Rate: " + sharedData.getDailyRate() +
                ']';
    }

    @Override
    public String getAvailabilityStatus() {
        return occupancyStatus.toString();
    }
}
