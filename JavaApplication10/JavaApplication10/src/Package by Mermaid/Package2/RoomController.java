package Package2;

import Package1.RoomFactory;
import Package1.Room;

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
