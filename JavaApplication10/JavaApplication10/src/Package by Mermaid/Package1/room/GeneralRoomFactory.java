package Package1.room;

public class GeneralRoomFactory extends RoomFactory {
    @Override
    public Room createRoom() {
        return new GeneralRoom();
    }
}
