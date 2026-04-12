package Package1.salary;

public class HazardAllowanceDecorator extends DoctorSalaryDecorator {
    private static final double HAZARD_AMOUNT = 200.0;

    public HazardAllowanceDecorator(DoctorSalary wrappedSalary) {
        super(wrappedSalary);
    }

    @Override
    public double calculateSalary() {
        return super.calculateSalary() + HAZARD_AMOUNT;
    }

    @Override
    public String getDescription() {
        return super.getDescription() +
                String.format("\nHazard Allowance: %.2f", HAZARD_AMOUNT);
    }
}
