package ui;

import service.MovieService;
import service.BookingService;
import model.Movie;

import java.util.List;
import java.util.Scanner;

public class AdminPanel {

    public static void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Admin Panel");
            System.out.println("1. Add Movie");
            System.out.println("2. Remove Movie");
            System.out.println("3. View Bookings");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter movie title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter movie genre: ");
                    String genre = scanner.nextLine();
                    System.out.print("Enter movie price: ");
                    double price = scanner.nextDouble();
                    Movie movie = new Movie(title, genre, price);
                    MovieService.addMovie(movie);
                    System.out.println("Movie added successfully.");
                    break;
                case 2:
                    System.out.print("Enter movie title to remove: ");
                    String movieToRemove = scanner.nextLine();
                    MovieService.removeMovie(movieToRemove);
                    System.out.println("Movie removed successfully.");
                    break;
                case 3:
                    List<Booking> bookings = BookingService.getBookings();
                    System.out.println("Bookings: ");
                    for (Booking booking : bookings) {
                        System.out.println("User: " + booking.getUsername() + ", Movie: " + booking.getMovieTitle());
                    }
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return; // Log out and exit
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }
}

