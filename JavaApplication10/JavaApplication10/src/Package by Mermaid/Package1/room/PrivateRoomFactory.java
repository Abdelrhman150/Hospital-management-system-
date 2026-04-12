package Package1.room;

public class PrivateRoomFactory extends RoomFactory {
    @Override
    public Room createRoom() {
        return new PrivateRoom();
    }
}
