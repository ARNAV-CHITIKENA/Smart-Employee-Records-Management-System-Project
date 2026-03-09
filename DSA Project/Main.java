// =====================================================================
//  Main.java  —  Entry point for Smart Employee Records Management System
//
//  DSA used:
//    Stack  (NavigationStack)  — tracks admin page navigation history
//    Queue  (OperationQueue)   — logs every add / edit / delete action
//
//  Files created at runtime:
//    admin_credentials.txt   — admin username & password
//    employees.txt           — all employee records (pipe-separated)
// =====================================================================
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner            sc       = new Scanner(System.in);
        AuthService        auth     = new AuthService();
        EmployeeRepository repo     = new EmployeeRepository();
        NavigationStack    navStack = new NavigationStack();
        OperationQueue     opQueue  = new OperationQueue();

        AdminPage    adminPage    = new AdminPage(sc, auth, repo, navStack, opQueue);
        EmployeePage employeePage = new EmployeePage(sc, repo);

        Util.printBanner("Smart Employee Records Management System");
        System.out.println("  Admin credentials file  : admin_credentials.txt");
        System.out.println("  Employee data file      : employees.txt");

        boolean running = true;
        while (running) {
            Util.printLine();
            System.out.println("  [1] Admin Login");
            System.out.println("  [2] Employee Login");
            System.out.println("  [3] Exit");
            Util.printLine();

            int choice = Util.readInt(sc, "  Choice: ");
            switch (choice) {
                case 1 -> adminPage.show();
                case 2 -> employeePage.show();
                case 3 -> {
                    System.out.println("\n  Goodbye! System closed.\n");
                    running = false;
                }
                default -> System.out.println("  [!] Invalid choice.");
            }
        }

        sc.close();
    }
}
