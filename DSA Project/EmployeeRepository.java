// =====================================================================
//  EmployeeRepository.java  —  CRUD + file persistence (employees.txt)
// =====================================================================
import java.util.*;
import java.io.*;

public class EmployeeRepository {

    private final List<Employee> employees = new ArrayList<>();
    private static final String DATA_FILE  = "employees.txt";

    public EmployeeRepository() {
        loadFromFile();
    }

    // ------------------------------------------------------------------
    //  CREATE
    // ------------------------------------------------------------------
    public void add(Employee e) {
        employees.add(e);
        saveToFile();
    }

    // ------------------------------------------------------------------
    //  READ
    // ------------------------------------------------------------------
    public List<Employee> getAll() {
        return Collections.unmodifiableList(employees);
    }

    public Employee findById(int id) {
        for (Employee e : employees) {
            if (e.id == id) return e;
        }
        return null;
    }

    public Employee findByName(String name) {
        for (Employee e : employees) {
            if (e.name.equalsIgnoreCase(name.trim())) return e;
        }
        return null;
    }

    public int count() {
        return employees.size();
    }

    // ------------------------------------------------------------------
    //  UPDATE
    // ------------------------------------------------------------------
    public boolean update(int id, String name, String role, String joined,
                          double salary, String marital) {
        for (Employee e : employees) {
            if (e.id == id) {
                e.name          = name;
                e.role          = role;
                e.joinedSince   = joined;
                e.annualSalary  = salary;
                e.maritalStatus = marital;
                saveToFile();
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------------------------------
    //  DELETE
    // ------------------------------------------------------------------
    public boolean delete(int id) {
        boolean removed = employees.removeIf(e -> e.id == id);
        if (removed) saveToFile();
        return removed;
    }

    // ------------------------------------------------------------------
    //  FILE PERSISTENCE
    // ------------------------------------------------------------------
    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Employee e : employees) {
                pw.println(e.toFileString());
            }
        } catch (IOException ex) {
            System.out.println("  [ERROR] Could not save data: " + ex.getMessage());
        }
    }

    private void loadFromFile() {
        File f = new File(DATA_FILE);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    Employee e = Employee.fromFileString(line);
                    if (e != null) employees.add(e);
                }
            }
        } catch (IOException ex) {
            System.out.println("  [ERROR] Could not load data: " + ex.getMessage());
        }
    }
}
