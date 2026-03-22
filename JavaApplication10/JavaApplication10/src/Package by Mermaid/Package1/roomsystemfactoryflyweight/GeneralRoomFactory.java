package Package1.roomsystemfactoryflyweight;

public class GeneralRoomFactory extends RoomFactory {
    @Override
    public Room createRoom() {
        return new GeneralRoom();
    }
}
