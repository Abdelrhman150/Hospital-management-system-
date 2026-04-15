package Package1.staff;

import Package4.*;
import Package1.staff.User;
import Package1.staff.Department;

public class StaffCreationResult {
    private User user;
    private Department department;

    public StaffCreationResult(User user, Department department) {
        this.user = user;
        this.department = department;
    }

    public User getUser() {
        return user;
    }

    public Department getDepartment() {
        return department;
    }
}