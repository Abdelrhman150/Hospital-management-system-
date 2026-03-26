package Package1;


/*public class Doctor {

    public static void main(String[] args) {

        DoctorSalary salary = new BaseDoctorSalary(10000);

        salary = new NightShiftBonusDecorator(salary, 5, 200);
        salary = new OnCallAllowanceDecorator(salary, 2, 300);
        salary = new HazardAllowanceDecorator(salary, "ICU", 1000);

        System.out.println("Salary Details: " + salary.getDescription());
        System.out.println("Final Salary: " + salary.calculateSalary());
    }
}*/

interface DoctorSalary {
    double calculateSalary();
    String getDescription();
}

class BaseDoctorSalary implements DoctorSalary {
    private double baseSalary;

    public BaseDoctorSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    @Override
    public double calculateSalary() {
        return baseSalary;
    }

    @Override
    public String getDescription() {
        return "Base Doctor Salary = " + baseSalary;
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
    private int numberOfNightShifts;
    private double bonusPerShift;

    public NightShiftBonusDecorator(DoctorSalary wrappedSalary, int numberOfNightShifts, double bonusPerShift) {
        super(wrappedSalary);
        this.numberOfNightShifts = numberOfNightShifts;
        this.bonusPerShift = bonusPerShift;
    }

    @Override
    public double calculateSalary() {
        return super.calculateSalary() + (numberOfNightShifts * bonusPerShift);
    }

    @Override
    public String getDescription() {
        return super.getDescription() +
                " + Night Shift Bonus (" + numberOfNightShifts + " shifts × " + bonusPerShift + ")";
    }
}

class OnCallAllowanceDecorator extends DoctorSalaryDecorator {
    private int onCallDays;
    private double allowancePerDay;

    public OnCallAllowanceDecorator(DoctorSalary wrappedSalary, int onCallDays, double allowancePerDay) {
        super(wrappedSalary);
        this.onCallDays = onCallDays;
        this.allowancePerDay = allowancePerDay;
    }

    @Override
    public double calculateSalary() {
        return super.calculateSalary() + (onCallDays * allowancePerDay);
    }

    @Override
    public String getDescription() {
        return super.getDescription() +
                " + On-Call Allowance (" + onCallDays + " days × " + allowancePerDay + ")";
    }
}

class HazardAllowanceDecorator extends DoctorSalaryDecorator {
    private String department;
    private double hazardAllowance;

    public HazardAllowanceDecorator(DoctorSalary wrappedSalary, String department, double hazardAllowance) {
        super(wrappedSalary);
        this.department = department;
        this.hazardAllowance = hazardAllowance;
    }

    @Override
    public double calculateSalary() {
        return super.calculateSalary() + hazardAllowance;
    }

    @Override
    public String getDescription() {
        return super.getDescription() +
                " + Hazard Allowance for " + department + " = " + hazardAllowance;
    }
}