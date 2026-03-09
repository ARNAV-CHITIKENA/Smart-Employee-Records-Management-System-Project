// =====================================================================
//  Employee.java  —  Model class
// =====================================================================
public class Employee {

    private static int idCounter = 1;

    public int    id;
    public String name;
    public String role;
    public String joinedSince;
    public double annualSalary;
    public String maritalStatus;  // "Single" or "Married"

    // Constructor for NEW employees (auto-generates ID)
    public Employee(String name, String role, String joinedSince,
                    double annualSalary, String maritalStatus) {
        this.id            = idCounter++;
        this.name          = name;
        this.role          = role;
        this.joinedSince   = joinedSince;
        this.annualSalary  = annualSalary;
        this.maritalStatus = maritalStatus;
    }

    // Constructor for LOADING from file (preserves existing ID)
    public Employee(int id, String name, String role, String joinedSince,
                    double annualSalary, String maritalStatus) {
        if (id >= idCounter) idCounter = id + 1;
        this.id            = id;
        this.name          = name;
        this.role          = role;
        this.joinedSince   = joinedSince;
        this.annualSalary  = annualSalary;
        this.maritalStatus = maritalStatus;
    }

    // Serialize to pipe-separated line for text file storage
    public String toFileString() {
        return id + "|" + name + "|" + role + "|" + joinedSince
               + "|" + annualSalary + "|" + maritalStatus;
    }

    // Parse one line from the text file back into an Employee object
    public static Employee fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 6) return null;
        try {
            int    id     = Integer.parseInt(parts[0].trim());
            String name   = parts[1].trim();
            String role   = parts[2].trim();
            String joined = parts[3].trim();
            double salary = Double.parseDouble(parts[4].trim());
            String marital= parts[5].trim();
            return new Employee(id, name, role, joined, salary, marital);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format(
            "  ID            : %d%n" +
            "  Name          : %s%n" +
            "  Role          : %s%n" +
            "  Joined Since  : %s%n" +
            "  Annual Salary : $%.2f%n" +
            "  Marital Status: %s",
            id, name, role, joinedSince, annualSalary, maritalStatus);
    }
}
