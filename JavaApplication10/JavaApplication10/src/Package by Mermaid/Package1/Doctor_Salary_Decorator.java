package Package1;

interface DoctorSalary {
    double calculateSalary();
    String getDescription();
}

class BaseDoctorSalary implements DoctorSalary {
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

abstract class DoctorSalaryDecorator implements DoctorSalary {
    protected DoctorSalary wrappedSalary;

    public DoctorSalaryDecorator(DoctorSalary wrappedSalary) {
        this.wrappedSalary = wrappedSalary;
    }

    @Override
    public double calculateSalary() {
        return wrappedSalary.calculateSalary();
    }

    @Override
    public String getDescription() {
        return wrappedSalary.getDescription();
    }
}

class NightShiftBonusDecorator extends DoctorSalaryDecorator {
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

class OnCallAllowanceDecorator extends DoctorSalaryDecorator {
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

class HazardAllowanceDecorator extends DoctorSalaryDecorator {
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