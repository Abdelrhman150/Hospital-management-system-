package Package1.roomsystemfactoryflyweight;

public class PrivateRoomFactory extends RoomFactory {
    @Override
    public Room createRoom() {
        return new PrivateRoom();
    }
}
