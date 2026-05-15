package Package1.room;

public class GeneralRoomFactory implements RoomFactory {
    @Override
    public Room createRoom() {
        return new GeneralRoom();
    }
}
