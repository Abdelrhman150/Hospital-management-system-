package Package1.room;

public class ICUFactory extends RoomFactory {
    @Override
    public Room createRoom() {
        return new ICU();
    }
}
