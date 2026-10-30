abstract class Employee {
    protected String name;
    protected int id;
    Employee(String name, int id) { this.name = name; this.id = id; }
    abstract double monthlySalary();
}

class FullTime extends Employee {
    private double fixed;
    FullTime(String name, int id, double fixed) { super(name, id); this.fixed = fixed; }
    double monthlySalary() { return fixed; }
}

class PartTime extends Employee {
    private int hours; private double rate;
    PartTime(String name, int id, int hours, double rate) { super(name, id); this.hours = hours; this.rate = rate; }
    double monthlySalary() { return hours * rate; }
}

class Intern extends Employee {
    private double stipend;
    Intern(String name, int id, double stipend) { super(name, id); this.stipend = stipend; }
    double monthlySalary() { return stipend; }
}

public class Payroll {
    public static void main(String[] args) {
        Employee[] staff = {
            new FullTime("Riya", 101, 60000),
            new PartTime("Arjun", 102, 80, 250),
            new Intern("Meera", 103, 12000),
            new FullTime("Karan", 104, 45000)
        };
        double total = 0;
        for (Employee e : staff) {
            double sal = e.monthlySalary();
            total += sal;
            String note = (e instanceof Intern) ? "   <- intern (stipend only)" : "";
            System.out.printf("%d %-6s %10.2f%s%n", e.id, e.name, sal, note);
        }
        System.out.printf("Total payroll: %.2f%n", total);
    }
}
