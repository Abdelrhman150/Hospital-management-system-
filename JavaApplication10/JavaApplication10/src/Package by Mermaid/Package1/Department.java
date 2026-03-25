package Package1;

import java.util.Date;

/**
 * Represents a department in the hospital.
 */

public abstract class Department {
    protected String departmentId;
    protected String departmentName;
    protected int floorNumber;
    protected String departmentHeadId;

    public Department(String departmentId, String departmentName, int floorNumber, String departmentHeadId) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.floorNumber = floorNumber;
        this.departmentHeadId = departmentHeadId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public String getDepartmentHeadId() {
        return departmentHeadId;
    }

    public abstract String getDepartmentType();

    public void displayDepartment() {
        System.out.println("Department ID: " + departmentId);
        System.out.println("Department Name: " + departmentName);
        System.out.println("Department Type: " + getDepartmentType());
        System.out.println("Floor Number: " + floorNumber);
        System.out.println("Department Head ID: " + departmentHeadId);
    }

}
