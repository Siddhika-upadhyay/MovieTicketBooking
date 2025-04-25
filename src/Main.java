package src;

import java.util.Scanner;
import service.AuthService;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Main menu
        System.out.println("Welcome to Movie Ticket Booking System");
        System.out.println("1. Admin Login");
        System.out.println("2. User Login");
        System.out.print("Choose your role: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  

        if (choice == 1) {
     
            System.out.print("Enter Admin Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Admin Password: ");
            String password = scanner.nextLine();

            if (AuthService.loginAdmin(username, password)) {
                System.out.println("Admin logged in successfully!");
              
                ui.AdminPanel.displayMenu();
            } else {
                System.out.println("Invalid Admin credentials.");
            }

        } else if (choice == 2) {
         
            System.out.print("Enter User Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter User Password: ");
            String password = scanner.nextLine();

            if (AuthService.loginUser(username, password)) {
                System.out.println("User logged in successfully!");
                ui.UserPanel.displayMenu();
            } else {
                System.out.println("Invalid User credentials.");
            }

        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }
}
