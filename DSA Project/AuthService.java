// =====================================================================
//  AuthService.java  —  Admin credentials stored in admin_credentials.txt
// =====================================================================
import java.io.*;

public class AuthService {

    private static final String CRED_FILE = "admin_credentials.txt";
    private String storedUsername;
    private String storedPassword;

    public AuthService() {
        loadOrCreateCredentials();
    }

    // Try to load credentials; if file missing, write the defaults
    private void loadOrCreateCredentials() {
        File f = new File(CRED_FILE);
        if (f.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                storedUsername = br.readLine();
                storedPassword = br.readLine();
            } catch (IOException ex) {
                System.out.println("  [ERROR] Reading credentials: " + ex.getMessage());
            }
        }

        // Fall back to defaults if file was missing or malformed
        if (storedUsername == null || storedUsername.isBlank()
                || storedPassword == null || storedPassword.isBlank()) {
            storedUsername = "Arnav ch";
            storedPassword = "123456789";
            saveCredentials();
            System.out.println("  [INFO] Default admin credentials written to "
                               + CRED_FILE);
        }
    }

    private void saveCredentials() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CRED_FILE))) {
            pw.println(storedUsername);
            pw.println(storedPassword);
        } catch (IOException ex) {
            System.out.println("  [ERROR] Saving credentials: " + ex.getMessage());
        }
    }

    // Returns true only if both username AND password match exactly
    public boolean authenticate(String username, String password) {
        return storedUsername.equals(username)
            && storedPassword.equals(password);
    }

    public String getUsername() {
        return storedUsername;
    }
}
