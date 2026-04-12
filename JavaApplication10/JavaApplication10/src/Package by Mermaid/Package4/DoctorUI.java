package Package4;
import Package1.Doctor;
import Package3.DoctorDAO;

import java.io.*;
import java.util.*;




public class DoctorUI {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        DoctorDAO doctorDAO = DoctorDAO.getInstance();

        try {
            System.out.println("===== Doctor Salary Viewer =====");
            System.out.print("Enter Doctor ID: ");
            String doctorId = input.nextLine();

            Doctor doctor = doctorDAO.getDoctorById(doctorId);

            if (doctor == null) {
                System.out.println("Doctor not found.");
                return;
            }

            System.out.println("\nDoctor found:");
            doctor.displayInfo();

            doctor.viewSavedSalaryFromDatabase();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            input.close();
        }
    }
}