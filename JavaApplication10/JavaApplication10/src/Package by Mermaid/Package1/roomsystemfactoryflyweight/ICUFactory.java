package Package1.roomsystemfactoryflyweight;

public class ICUFactory extends RoomFactory {
    @Override
    public Room createRoom() {
        return new ICU();
    }
}
