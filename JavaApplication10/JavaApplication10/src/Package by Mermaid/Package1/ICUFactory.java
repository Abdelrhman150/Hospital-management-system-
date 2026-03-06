package Package1;

public class ICUFactory extends RoomFactory {
    @Override
    public Room createRoom() {
        return new ICU();
    }
}
