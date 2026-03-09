// =====================================================================
//  AdminPage.java  —  Admin login + full employee management console
// =====================================================================
import java.util.*;

public class AdminPage {

    private final Scanner            sc;
    private final AuthService        auth;
    private final EmployeeRepository repo;
    private final NavigationStack    navStack;
    private final OperationQueue     opQueue;

    public AdminPage(Scanner sc, AuthService auth, EmployeeRepository repo,
                     NavigationStack navStack, OperationQueue opQueue) {
        this.sc       = sc;
        this.auth     = auth;
        this.repo     = repo;
        this.navStack = navStack;
        this.opQueue  = opQueue;
    }

    // ------------------------------------------------------------------
    //  LOGIN GATE
    // ------------------------------------------------------------------
    public void show() {
        Util.printBanner("Admin Login");
        System.out.println("  Credentials are stored in: admin_credentials.txt");
        Util.printLine();

        String username = Util.readLine(sc, "  Username : ");
        String password = Util.readLine(sc, "  Password : ");

        if (auth.authenticate(username, password)) {
            System.out.println("\n  Login successful!  Welcome, " + username + ".");
            navStack.push("ADMIN_DASHBOARD");
            dashboard();
        } else {
            System.out.println("\n  [!] Wrong username or password. Access denied.");
        }
    }

    // ------------------------------------------------------------------
    //  ADMIN DASHBOARD
    // ------------------------------------------------------------------
    private void dashboard() {
        boolean running = true;
        while (running) {
            Util.printBanner("Admin Dashboard");
            System.out.println("  Total employees in company : " + repo.count());
            Util.printLine();
            System.out.println("  [1] View All Employees");
            System.out.println("  [2] Add Employee");
            System.out.println("  [3] Edit Employee");
            System.out.println("  [4] Delete Employee");
            System.out.println("  [5] Search Employee");
            System.out.println("  [6] View Operation Log");
            System.out.println("  [7] Logout");
            Util.printLine();

            int choice = Util.readInt(sc, "  Choice: ");
            switch (choice) {
                case 1 -> viewAll();
                case 2 -> addEmployee();
                case 3 -> editEmployee();
                case 4 -> deleteEmployee();
                case 5 -> searchEmployee();
                case 6 -> viewOperationLog();
                case 7 -> {
                    System.out.println("\n  Logged out from Admin panel.\n");
                    navStack.pop();
                    running = false;
                }
                default -> System.out.println("  [!] Invalid choice, try again.");
            }
        }
    }

    // ------------------------------------------------------------------
    //  1. VIEW ALL
    // ------------------------------------------------------------------
    private void viewAll() {
        Util.printBanner("All Employees  (" + repo.count() + " total)");
        List<Employee> list = repo.getAll();
        if (list.isEmpty()) {
            System.out.println("  No employees found.");
        } else {
            for (Employee e : list) {
                Util.printLine();
                System.out.println(e);
            }
            Util.printLine();
        }
        Util.pause(sc);
    }

    // ------------------------------------------------------------------
    //  2. ADD EMPLOYEE
    // ------------------------------------------------------------------
    private void addEmployee() {
        Util.printBanner("Add New Employee");

        String name    = Util.readLine(sc, "  Name           : ");
        String role    = Util.readLine(sc, "  Role           : ");
        String joined  = Util.readLine(sc, "  Joined Since   : ");
        double salary  = Util.readDouble(sc, "  Annual Salary ($): ");
        String marital = Util.readMaritalStatus(sc);

        Employee e = new Employee(name, role, joined, salary, marital);
        repo.add(e);
        opQueue.enqueue("ADD    | ID:" + e.id + " | " + name + " | " + role);

        System.out.println("\n  Employee added! Assigned ID: " + e.id);
        Util.pause(sc);
    }

    // ------------------------------------------------------------------
    //  3. EDIT EMPLOYEE
    // ------------------------------------------------------------------
    private void editEmployee() {
        Util.printBanner("Edit Employee");
        if (repo.count() == 0) {
            System.out.println("  No employees to edit.");
            Util.pause(sc);
            return;
        }

        viewAll();
        int id = Util.readInt(sc, "  Enter Employee ID to edit: ");
        Employee e = repo.findById(id);
        if (e == null) {
            System.out.println("  [!] ID " + id + " not found.");
            Util.pause(sc);
            return;
        }

        System.out.println("\n  Current details:");
        System.out.println(e);
        System.out.println("\n  Press ENTER to keep the current value.");

        String name   = Util.promptKeep(sc, "  Name           [" + e.name + "]: ",    e.name);
        String role   = Util.promptKeep(sc, "  Role           [" + e.role + "]: ",    e.role);
        String joined = Util.promptKeep(sc, "  Joined Since   [" + e.joinedSince + "]: ", e.joinedSince);

        System.out.print("  Annual Salary  [" + e.annualSalary + "] (ENTER to keep): ");
        String salStr = sc.nextLine().trim();
        double salary = salStr.isEmpty() ? e.annualSalary : Double.parseDouble(salStr);

        System.out.println("  Marital Status [" + e.maritalStatus + "]");
        String marital = Util.readMaritalStatusKeep(sc, e.maritalStatus);

        repo.update(id, name, role, joined, salary, marital);
        opQueue.enqueue("EDIT   | ID:" + id + " | " + name + " | " + role);

        System.out.println("\n  Employee ID " + id + " updated successfully.");
        Util.pause(sc);
    }

    // ------------------------------------------------------------------
    //  4. DELETE EMPLOYEE
    // ------------------------------------------------------------------
    private void deleteEmployee() {
        Util.printBanner("Delete Employee");
        if (repo.count() == 0) {
            System.out.println("  No employees to delete.");
            Util.pause(sc);
            return;
        }

        viewAll();
        int id = Util.readInt(sc, "  Enter Employee ID to delete: ");
        Employee e = repo.findById(id);
        if (e == null) {
            System.out.println("  [!] ID " + id + " not found.");
            Util.pause(sc);
            return;
        }

        System.out.println("\n  You are about to delete:");
        System.out.println(e);
        String confirm = Util.readLine(sc, "\n  Type YES to confirm: ");

        if (confirm.equalsIgnoreCase("YES")) {
            String deletedName = e.name;
            repo.delete(id);
            opQueue.enqueue("DELETE | ID:" + id + " | " + deletedName);
            System.out.println("\n  Employee deleted successfully.");
        } else {
            System.out.println("  Deletion cancelled.");
        }
        Util.pause(sc);
    }

    // ------------------------------------------------------------------
    //  5. SEARCH EMPLOYEE
    // ------------------------------------------------------------------
    private void searchEmployee() {
        Util.printBanner("Search Employee");
        System.out.println("  [1] Search by ID");
        System.out.println("  [2] Search by Name");
        Util.printLine();
        int choice = Util.readInt(sc, "  Choice: ");

        Employee found = null;
        if (choice == 1) {
            int id = Util.readInt(sc, "  Enter Employee ID: ");
            found = repo.findById(id);
        } else if (choice == 2) {
            String name = Util.readLine(sc, "  Enter Employee Name: ");
            found = repo.findByName(name);
        } else {
            System.out.println("  [!] Invalid option.");
            Util.pause(sc);
            return;
        }

        if (found == null) {
            System.out.println("  [!] No employee found.");
        } else {
            Util.printLine();
            System.out.println(found);
            Util.printLine();
        }
        Util.pause(sc);
    }

    // ------------------------------------------------------------------
    //  6. OPERATION LOG
    // ------------------------------------------------------------------
    private void viewOperationLog() {
        Util.printBanner("Operation Log  (Queue — " + opQueue.size() + " entries)");
        opQueue.printAll();
        Util.printLine();
        String opt = Util.readLine(sc, "  Type CLEAR to clear the log, or ENTER to go back: ");
        if (opt.equalsIgnoreCase("CLEAR")) {
            opQueue.clear();
            System.out.println("  Operation log cleared.");
        }
        Util.pause(sc);
    }
}
