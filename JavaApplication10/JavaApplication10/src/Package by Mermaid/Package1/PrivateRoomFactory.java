package Package1;

public class PrivateRoomFactory extends RoomFactory {
    @Override
    public Room createRoom() {
        return new PrivateRoom();
    }
}
