import java.util.*;

public class AdminPanel {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Movie> movies;
    private static List<Booking> bookings;

    public AdminPanel(List<Movie> movies, List<Booking> bookings) {
        AdminPanel.movies = movies;
        AdminPanel.bookings = bookings;
    }

    public void showAdminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Movie");
            System.out.println("2. Edit Movie");
            System.out.println("3. Remove Movie");
            System.out.println("4. View Movies");
            System.out.println("5. View All Bookings");
            System.out.println("6. Change Movie Price");
            System.out.println("7. Logout");
            System.out.print("Enter your choice: ");
            int choice = getValidIntInput(1, 7, "Please enter a valid option (1-7): ");

            switch (choice) {
                case 1:
                    addMovie();
                    break;
                case 2:
                    editMovie();
                    break;
                case 3:
                    removeMovie();
                    break;
                case 4:
                    viewMovies();
                    break;
                case 5:
                    viewAllBookings(bookings, movies);
                    break;
                case 6:
                    updateMoviePrice(movies);
                    break;
                case 7:
                    System.out.println("Admin logged out.");
                    return;
            }
        }
    }

    public static void addMovie() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Movie ID: ");
        int movieId = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Movie Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Movie Genre: ");
        String genre = scanner.nextLine();

        System.out.print("Enter Movie Duration (e.g., 2 hours): ");
        String duration = scanner.nextLine();

        System.out.print("Enter Movie Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter Movie Rating (e.g., 8.5): ");
        double rating = Double.parseDouble(scanner.nextLine());

        Movie newMovie = new Movie(movieId, title, genre, duration, price, rating);

        movies.add(newMovie);

        System.out.println("Movie added successfully.");
    }
    public void editMovie() {
        int id = getValidIntInput(1, Integer.MAX_VALUE, "Enter valid movie ID: ");
        for (Movie movie : movies) {
            if (movie.getMovieId() == id) {
                System.out.print("New title: ");
                movie.setTitle(scanner.nextLine().trim());
                System.out.print("New genre: ");
                movie.setGenre(scanner.nextLine().trim());
                System.out.print("New duration: ");
                movie.setDuration(scanner.nextLine().trim());
                double newPrice = getValidDoubleInput("New price: ", "Enter a valid price >= 0", 0);
                movie.setPrice(newPrice);
                System.out.println("Movie updated.");
                return;
            }
        }
        System.out.println("Movie not found.");
    }

    public void removeMovie() {
        int id = getValidIntInput(1, Integer.MAX_VALUE, "Enter valid movie ID to remove: ");
        boolean removed = movies.removeIf(movie -> movie.getMovieId() == id);
        if (removed) {
            System.out.println("Movie removed.");
        } else {
            System.out.println("Movie not found.");
        }
    }

    public void viewMovies() {
        if (movies.isEmpty()) {
            System.out.println("No movies available.");
            return;
        }

        System.out.println("\nAvailable Movies:");
        for (Movie m : movies) {
            System.out.println(m);
        }
    }

    public void viewAllBookings(List<Booking> bookings, List<Movie> movies) {
        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        System.out.println(String.format("%-10s %-20s %-10s %-10s", "BookingID", "Movie", "Seats", "Amount"));
        System.out.println("---------------------------------------------------------------");
        for (Booking b : bookings) {
            Movie movie = movies.stream().filter(m -> m.getMovieId() == b.getMovieId()).findFirst().orElse(null);
            if (movie != null) {
                System.out.println(b.toRow(movie));
            }
        }
    }

    public void updateMoviePrice(List<Movie> movies) {
        int id = getValidIntInput(1, Integer.MAX_VALUE, "Enter valid Movie ID: ");
        double newPrice = getValidDoubleInput("Enter new price: ", "Enter a valid price >= 0", 0);

        for (Movie m : movies) {
            if (m.getMovieId() == id) {
                m.setPrice(newPrice);
                System.out.println("Price updated for " + m.getTitle());
                return;
            }
        }
        System.out.println("Movie not found.");
    }

    private int getValidIntInput(int min, int max, String errorMsg) {
        while (true) {
            try {
                String input = scanner.nextLine();
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) return value;
            } catch (NumberFormatException ignored) {}
            System.out.print(errorMsg);
        }
    }

    private double getValidDoubleInput(String prompt, String errorMsg, double min) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                double value = Double.parseDouble(input);
                if (value >= min) return value;
            } catch (NumberFormatException ignored) {}
            System.out.println(errorMsg);
        }
    }
}
