package Package4;

import Package1.roomsystemfactoryflyweight.Room;
import Package3.RoomDAO;

import java.util.List;
import java.util.Scanner;

/**
 * UI for Room management operations
 */
public class RoomUI {

    private Scanner scanner;

    public RoomUI() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("========================================");
        System.out.println("            Room Management");
        System.out.println("========================================");

        while (true) {
            System.out.println("\nRoom Options:");
            System.out.println("1. View all rooms");
            System.out.println("2. View room details by ID");
            System.out.println("3. Reserve room (mark occupied)");
            System.out.println("4. Release room (mark available)");
            System.out.println("5. Set room under maintenance");
            System.out.println("6. Exit");
            System.out.print("Choose an option (1-6): ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        listRooms();
                        break;
                    case "2":
                        showRoomDetails();
                        break;
                    case "3":
                        changeRoomStatus("occupied");
                        break;
                    case "4":
                        changeRoomStatus("available");
                        break;
                    case "5":
                        changeRoomStatus("maintenance");
                        break;
                    case "6":
                        System.out.println("Exiting Room Management.");
                        return;
                    default:
                        System.out.println("Invalid option. Enter a value between 1 and 6.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void listRooms() throws Exception {
        List<RoomDAO.RoomRecord> rooms = RoomDAO.getInstance().getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms are registered yet.");
            return;
        }

        System.out.println("\n--- Registered Rooms ---");
        for (RoomDAO.RoomRecord record : rooms) {
            System.out.printf("ID: %s | Type: %s | Status: %s | Capacity: %d | Daily Rate: $%.2f\n",
                    record.getRoomId(), record.getRoomType(), record.getAvailabilityStatus(),
                    record.getCapacity(), record.getDailyRate());
        }
        System.out.println("-------------------------");
    }

    private void showRoomDetails() throws Exception {
        System.out.print("Enter room ID: ");
        int roomId = Integer.parseInt(scanner.nextLine());
        Room room = RoomDAO.getInstance().getRoomById(roomId);
        System.out.println("\n--- Room Details ---");
        System.out.println(room);
    }

    private void changeRoomStatus(String action) throws Exception {
        System.out.print("Enter room ID: ");
        int roomId = Integer.parseInt(scanner.nextLine());
        RoomDAO roomDAO = RoomDAO.getInstance();

        switch (action) {
            case "occupied":
                roomDAO.markRoomOccupied(roomId);
                System.out.println("Room " + roomId + " marked as Occupied.");
                break;
            case "available":
                roomDAO.markRoomAvailable(roomId);
                System.out.println("Room " + roomId + " marked as Available.");
                break;
            case "maintenance":
                Room room = roomDAO.getRoomById(roomId);
                room.markUnderMaintenance();
                System.out.println("Room " + roomId + " set to Under Maintenance (local state only).\n" +
                        "Database status remains unchanged due to repository limitations.");
                break;
            default:
                System.out.println("Unknown action");
        }
    }

    public static void main(String[] args) {
        new RoomUI().start();
    }
}