package Package4;

import Package1.Admin;
import Package1.Doctor;
import Package2.IdGenerator;
import Package3.AdminDAO;
import Package3.DoctorDAO;

import java.util.Scanner;

public class AdminUI {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        AdminDAO adminDAO = AdminDAO.getInstance();
        DoctorDAO doctorDAO = DoctorDAO.getInstance();

        Admin loggedInAdmin = null;

        try {
            while (loggedInAdmin == null) {
                System.out.println("\n=================================");
                System.out.println("         ADMIN PORTAL");
                System.out.println("=================================");
                System.out.println("1. Sign In");
                System.out.println("2. Sign Up");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");

                int choice = input.nextInt();
                input.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("\n----------- Sign In -----------");
                        System.out.print("Enter Admin ID: ");
                        String loginId = input.nextLine();

                        System.out.print("Enter Admin Name: ");
                        String loginName = input.nextLine();

                        loggedInAdmin = adminDAO.login(loginId, loginName);

                        if (loggedInAdmin == null) {
                            System.out.println("\nInvalid admin credentials. Please try again.");
                        } else {
                            System.out.println("\nLogin successful. Welcome, " + loggedInAdmin.getName() + "!");
                        }
                        break;

                    case 2:
                        System.out.println("\n----------- Sign Up -----------");

                        System.out.print("Enter Name: ");
                        String newName = input.nextLine();

                        System.out.print("Enter Phone: ");
                        String newPhone = input.nextLine();

                        System.out.print("Enter Email: ");
                        String newEmail = input.nextLine();

                        String generatedAdminId = IdGenerator.getInstance().generateUserIdByRole("admin");

                        adminDAO.addAdmin(generatedAdminId, newName, newPhone, newEmail);

                        System.out.println("\nAdmin account created successfully.");
                        System.out.println("Generated Admin ID: " + generatedAdminId);
                        break;
                }
            }

            boolean running = true;

            while (running) {
                System.out.println("\n=================================");
                System.out.println("         ADMIN FUNCTIONS");
                System.out.println("=================================");
                System.out.println("1. Assign Salary To Doctor");
                System.out.println("2. Logout");
                System.out.print("Choose an option: ");

                int option = input.nextInt();
                input.nextLine();

                switch (option) {
                    case 1:
                        System.out.println("\n------ Assign Salary To Doctor ------");
                        System.out.print("Enter Doctor ID: ");
                        String doctorId = input.nextLine();

                        Doctor doctor = doctorDAO.findDoctorObjectById(doctorId);

                        if (doctor == null) {
                            System.out.println("\nDoctor not found in the database.");
                            break;
                        }

                        System.out.println("\nDoctor found successfully:");
                        System.out.println("---------------------------------");
                        doctor.displayInfo();
                        System.out.println("---------------------------------");

                        int nightShifts = 0;
                        int onCallDays = 0;
                        boolean hasHazard = false;

                        System.out.print("\nDid this doctor work night shifts this month? (yes/no): ");
                        String nightShiftAnswer = input.nextLine().trim().toLowerCase();

                        if (nightShiftAnswer.equals("yes")) {
                            System.out.print("Enter number of night shifts: ");
                            nightShifts = input.nextInt();
                            input.nextLine();
                        }

                        System.out.print("\nWas this doctor on-call this month? (yes/no): ");
                        String onCallAnswer = input.nextLine().trim().toLowerCase();

                        if (onCallAnswer.equals("yes")) {
                            System.out.print("Enter number of on-call days: ");
                            onCallDays = input.nextInt();
                            input.nextLine();
                        }

                        System.out.print("\nDoes this doctor receive hazard allowance this month? (yes/no): ");
                        String hazardAnswer = input.nextLine().trim().toLowerCase();

                        if (hazardAnswer.equals("yes")) {
                            hasHazard = true;
                        }

                        loggedInAdmin.assignSalaryToDoctor(
                                doctor,
                                nightShifts,
                                onCallDays,
                                hasHazard
                        );

                        System.out.println("\n========= SALARY BREAKDOWN =========");
                        doctor.viewSalary();
                        System.out.println("====================================");
                        break;

                    case 2:
                        System.out.println("\nLogged out successfully.");
                        running = false;
                        break;

                    default:
                        System.out.println("\nInvalid choice. Please try again.");
                }
            }

        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
        } finally {
            input.close();
        }
    }
}