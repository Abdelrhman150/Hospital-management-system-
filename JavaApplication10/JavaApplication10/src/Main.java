import Package1.GeneralRoomFactory;
import Package2.RoomController;


public class Main {
    public static void main(String[] args){
        RoomController controller = new RoomController(new GeneralRoomFactory());
        System.out.println(controller.reserveRoom().calculateCost(10));

        
    }
    
}
