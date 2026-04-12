package Package1.salary;

public class NightShiftBonusDecorator extends DoctorSalaryDecorator {
    private static final double BONUS_PER_SHIFT = 300.0;
    private int numberOfNightShifts;

    public NightShiftBonusDecorator(DoctorSalary wrappedSalary, int numberOfNightShifts) {
        super(wrappedSalary);
        this.numberOfNightShifts = numberOfNightShifts;
    }

    @Override
    public double calculateSalary() {
        return super.calculateSalary() + (numberOfNightShifts * BONUS_PER_SHIFT);
    }

    @Override
    public String getDescription() {
        return super.getDescription() +
                String.format("\nNight Shift Bonus: %d x %.2f = %.2f",
                        numberOfNightShifts,
                        BONUS_PER_SHIFT,
                        numberOfNightShifts * BONUS_PER_SHIFT);
    }
}
