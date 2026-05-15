package Package1.room;

public class PrivateRoomFactory implements RoomFactory {
    @Override
    public Room createRoom() {
        return new PrivateRoom();
    }
}
