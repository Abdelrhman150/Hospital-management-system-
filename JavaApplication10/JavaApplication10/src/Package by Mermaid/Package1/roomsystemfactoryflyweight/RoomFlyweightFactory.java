package Package1.roomsystemfactoryflyweight;

import java.util.HashMap;
import java.util.Map;

public class RoomFlyweightFactory {

    private static final Map<String, RoomSharedData> sharedRooms = new HashMap<>();

    public static RoomSharedData getRoomSharedData(RoomType roomType) {
        String key = roomType.name();

        if (!sharedRooms.containsKey(key)) {
            switch (roomType) {
                case ICU:
                    sharedRooms.put(key, new RoomSharedData(RoomType.ICU, 1, 500.0));
                    break;
                case PrivateRoom:
                    sharedRooms.put(key, new RoomSharedData(RoomType.PrivateRoom, 1, 300.0));
                    break;
                case GeneralWard:
                    sharedRooms.put(key, new RoomSharedData(RoomType.GeneralWard, 4, 100.0));
                    break;
            }
        }

        return sharedRooms.get(key);
    }

}