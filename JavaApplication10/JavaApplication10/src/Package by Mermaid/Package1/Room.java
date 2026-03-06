package Package1;

import java.io.*;
import java.util.*;

public interface Room {
    public void markOccupied();
    public void markAvailable();
    public void markUnderMaintenance();
    public double calculateCost(int days);   
}
@Override
public String toString() {
        return "Room Details [" +
                "ID: '" + roomId + '\'' +
                ", Type: '" + roomType + '\'' +
                ", Capacity: " + capacity +
                ", Status: '" + availabilityStatus + '\'' +
                ", Daily Rate: " + dailyRate +
                ']';
}
