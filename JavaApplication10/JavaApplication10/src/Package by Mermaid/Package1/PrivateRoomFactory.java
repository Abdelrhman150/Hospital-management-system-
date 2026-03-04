package Package1;

import java.io.*;
import java.util.*;

public class PrivateRoomFactory extends RoomFactory {
    @Override
    public Room createRoom() {
        return new PrivateRoom();
    }
}
