import java.util.Scanner;

public class admin {
    private static Scanner scanner = new Scanner(System.in);
    private static final String ADMIN_EMAIL = "admin@admin.com";
    private static final String ADMIN_PASSWORD = "admin123";

    private static boolean adminRegistered = false;

    public static boolean login() {
      

        if (!adminRegistered) {
            System.out.print("Enter Admin Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Admin Password: ");
            String password = scanner.nextLine();
       
            if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
                adminRegistered = true;
                return true;
            } else {
                System.out.println("Invalid credentials. Please try again.");
                return false;
            }
        }
        return true;
    }
}
