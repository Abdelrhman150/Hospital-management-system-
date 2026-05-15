package Package1.salary;

public class OnCallAllowanceDecorator extends DoctorSalaryDecorator {
    private static final double ALLOWANCE_PER_DAY = 100.0;
    private int onCallDays;

    public OnCallAllowanceDecorator(DoctorSalary wrappedSalary, int onCallDays) {
        super(wrappedSalary);
        this.onCallDays = onCallDays;
    }

    @Override
    public double calculateSalary() {
        return super.calculateSalary() + (onCallDays * ALLOWANCE_PER_DAY);
    }

    @Override
    public String getDescription() {
        return super.getDescription() +
                String.format("\nOn-Call Allowance: %d x %.2f = %.2f",
                        onCallDays,
                        ALLOWANCE_PER_DAY,
                        onCallDays * ALLOWANCE_PER_DAY);
    }
}
