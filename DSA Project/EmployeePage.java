// =====================================================================
//  EmployeePage.java  —  Employee login + read-only self-view
// =====================================================================
import java.util.Scanner;

public class EmployeePage {

    private final Scanner            sc;
    private final EmployeeRepository repo;

    public EmployeePage(Scanner sc, EmployeeRepository repo) {
        this.sc   = sc;
        this.repo = repo;
    }

    // ------------------------------------------------------------------
    //  LOGIN GATE  (employees log in with their registered name)
    // ------------------------------------------------------------------
    public void show() {
        Util.printBanner("Employee Portal - Login");

        if (repo.count() == 0) {
            System.out.println("  No employees are registered yet.");
            System.out.println("  Please ask the admin to add your record first.");
            Util.pause(sc);
            return;
        }

        String name = Util.readLine(sc, "  Enter your full name: ");
        Employee e  = repo.findByName(name);

        if (e == null) {
            System.out.println("\n  [!] No record found for: " + name);
            System.out.println("  Please contact the admin.");
        } else {
            viewDashboard(e);
        }
    }

    // ------------------------------------------------------------------
    //  EMPLOYEE DASHBOARD  (read-only)
    // ------------------------------------------------------------------
    private void viewDashboard(Employee e) {
        Util.printBanner("My Details");
        System.out.println("  Welcome, " + e.name + "!");
        Util.printLine();
        System.out.println(e);
        Util.printLine();
        System.out.println("  Total colleagues in company : " + repo.count());
        Util.printLine();
        System.out.println("  (Employees can only view their own details.)");
        System.out.println("  (Contact admin for any changes.)");
        Util.pause(sc);
        System.out.println("\n  Logged out from Employee Portal.\n");
    }
}
