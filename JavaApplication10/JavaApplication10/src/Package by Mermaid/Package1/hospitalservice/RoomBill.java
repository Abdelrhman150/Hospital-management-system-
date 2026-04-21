package Package1.hospitalservice;

import Package1.room.Room;

public class RoomBill extends Bill {
    public double amount;
    public int DaysOfStay;
    private Room room;


    // Constructor
    public RoomBill(Room room) {
        this.room = room;
    }

    @Override
    public double calculateamount() {
        this.amount = room.getDailyRate() * DaysOfStay;
        return this.amount;
    }

}
