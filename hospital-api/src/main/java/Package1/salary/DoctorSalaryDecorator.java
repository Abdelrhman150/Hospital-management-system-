package Package1.salary;

public abstract class DoctorSalaryDecorator implements DoctorSalary {
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
