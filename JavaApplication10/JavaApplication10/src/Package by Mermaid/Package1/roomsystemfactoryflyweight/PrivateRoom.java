package Package1.roomsystemfactoryflyweight;

import Package3.RoomDAO;

public class PrivateRoom implements Room {

    public int roomId;
    public RoomStatus occupancyStatus;
    private RoomSharedData sharedData;

    public PrivateRoom() {
        this.sharedData = RoomFlyweightFactory.getRoomSharedData(RoomType.PrivateRoom);
        this.occupancyStatus = RoomStatus.Available;
    }

    public PrivateRoom(int roomId, RoomStatus occupancyStatus) {
        this.roomId = roomId;
        this.occupancyStatus = occupancyStatus;
        this.sharedData = RoomFlyweightFactory.getRoomSharedData(RoomType.PrivateRoom);
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
    public double calculateCost(int days) {
        return sharedData.getDailyRate() * days;
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