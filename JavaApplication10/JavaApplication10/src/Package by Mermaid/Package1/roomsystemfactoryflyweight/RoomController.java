package Package1.roomsystemfactoryflyweight;

/**
 * 
 */
public class RoomController {
    public static RoomFactory factory;

    /**
     * Default constructor
     */
    public RoomController(RoomFactory factory) {
        this.factory = factory;
    }

    public Room reserveRoom() {
        return factory.createRoom();
    }
}