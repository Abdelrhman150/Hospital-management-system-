package Package1.room;

public class ICUFactory implements RoomFactory {
    @Override
    public Room createRoom() {
        return new ICU();
    }
}
