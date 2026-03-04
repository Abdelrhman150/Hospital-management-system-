package Package1;

import java.io.*;
import java.util.*;


public class ICUFactory extends RoomFactory {
    @Override
    public Room createRoom() {
        return new ICU();
    }
}

