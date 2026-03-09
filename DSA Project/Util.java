// =====================================================================
//  Util.java  —  Shared console utility methods
// =====================================================================
import java.util.Scanner;

public class Util {

    private static final String LINE =
        "--------------------------------------------------";
    private static final String TOP  =
        "╔══════════════════════════════════════════════════╗";
    private static final String BOT  =
        "╚══════════════════════════════════════════════════╝";

    // Banner header
    public static void printBanner(String title) {
        System.out.println();
        System.out.println(TOP);
        System.out.printf("║  %-48s║%n", title);
        System.out.println(BOT);
    }

    public static void printLine() {
        System.out.println(LINE);
    }

    // Read a non-empty trimmed string
    public static String readLine(Scanner sc, String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    // Read integer with retry on bad input
    public static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Please enter a whole number.");
            }
        }
    }

    // Read double with retry on bad input
    public static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("  [!] Please enter a valid number (e.g. 50000).");
            }
        }
    }

    // Read marital status choice (1=Single / 2=Married)
    public static String readMaritalStatus(Scanner sc) {
        while (true) {
            System.out.print("  Marital Status (1=Single / 2=Married): ");
            String s = sc.nextLine().trim();
            if (s.equals("1")) return "Single";
            if (s.equals("2")) return "Married";
            System.out.println("  [!] Enter 1 for Single or 2 for Married.");
        }
    }

    // Same as above but pressing ENTER keeps current value
    public static String readMaritalStatusKeep(Scanner sc, String current) {
        System.out.print("  Change status? (1=Single / 2=Married / ENTER=keep): ");
        String s = sc.nextLine().trim();
        if (s.equals("1")) return "Single";
        if (s.equals("2")) return "Married";
        return current;
    }

    // Prompt that keeps the old value if user just presses ENTER
    public static String promptKeep(Scanner sc, String prompt, String current) {
        System.out.print(prompt);
        String val = sc.nextLine().trim();
        return val.isEmpty() ? current : val;
    }

    // Wait for the user to press ENTER before continuing
    public static void pause(Scanner sc) {
        System.out.print("\n  Press ENTER to continue...");
        sc.nextLine();
    }
}
