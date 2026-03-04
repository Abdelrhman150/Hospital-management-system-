package Package1;

import java.io.*;
import java.util.*;

public class GeneralRoomFactory extends RoomFactory {
    @Override
    public Room createRoom() {
        return new GeneralRoom();
    }
}
