package Package1;

import java.io.*;
import java.util.*;

public interface Room {
    public void markOccupied();
    public void markAvailable();
    public void markUnderMaintenance();
    public double calculateCost(int days);   
}
