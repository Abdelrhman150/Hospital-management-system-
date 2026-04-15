package Package1.roomsystemfactoryflyweight;

import Package1.room.*;
import Package2.IdGenerator;
import Package3.RoomDAO;

public class RoomController {
    public static RoomFactory factory;

    public RoomController(RoomFactory factory) {
        this.factory = factory;
    }

    public Room createRoomController() {
        Room room = factory.createRoom();

        String generatedRoomId = IdGenerator.getInstance().nextRoomId();
        room.setRoomID(generatedRoomId);    

        RoomDAO.RoomRecord record = new RoomDAO.RoomRecord(
                room.getRoomID(),
                room.getRoomType(),
                room.getCapacity(),
                room.getAvailabilityStatus(),
                room.getDailyRate()
        );

        try {
            RoomDAO.getInstance().addRoom(record);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return room;
    }
}