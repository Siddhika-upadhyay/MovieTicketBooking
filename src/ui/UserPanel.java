package ui;

import service.MovieService;
import service.BookingService;
import model.Movie;

import java.util.List;
import java.util.Scanner;

public class UserPanel {

    public static void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("User Panel");
            System.out.println("1. View Movies");
            System.out.println("2. Book Ticket");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    List<Movie> movies = MovieService.getMovies();
                    System.out.println("Movies available:");
                    for (Movie movie : movies) {
                        System.out.println("Title: " + movie.getTitle() + ", Genre: " + movie.getGenre() + ", Price: " + movie.getPrice());
                    }
                    break;
                case 2:
                    System.out.print("Enter movie title to book: ");
                    String movieTitle = scanner.nextLine();
                    BookingService.bookTicket("user", movieTitle);
                    System.out.println("Ticket booked successfully.");
                    break;
                case 3:
                    System.out.print("Enter movie title to cancel booking: ");
                    String cancelTitle = scanner.nextLine();
                    BookingService.cancelBooking("user", cancelTitle);
                    System.out.println("Booking cancelled.");
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
