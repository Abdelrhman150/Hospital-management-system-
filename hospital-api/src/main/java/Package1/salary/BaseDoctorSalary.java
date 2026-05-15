package Package1.salary;

public class BaseDoctorSalary implements DoctorSalary {
    private static final double BASE_SALARY = 100000.0;

    public BaseDoctorSalary() {
    }

    @Override
    public double calculateSalary() {
        return BASE_SALARY;
    }

    @Override
    public String getDescription() {
        return String.format("Base Salary: %.2f", BASE_SALARY);
    }
}
