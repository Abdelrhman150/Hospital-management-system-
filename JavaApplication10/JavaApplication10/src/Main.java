import Package1.roomsystemfactoryflyweight.GeneralRoomFactory;
import Package1.roomsystemfactoryflyweight.RoomController;

public class Main {
    public static void main(String[] args) {
        RoomController PatientRoom = new RoomController(new GeneralRoomFactory());
        System.out.println(PatientRoom.reserveRoom().calculateCost(10));

    }

}
