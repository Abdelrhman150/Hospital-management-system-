package Package1;

public class GeneralRoomFactory extends RoomFactory {
    @Override
    public Room createRoom() {
        return new GeneralRoom();
    }
}
