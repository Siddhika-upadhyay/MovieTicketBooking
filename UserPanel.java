import java.util.*;

public class UserPanel {
    private static Scanner sc = new Scanner(System.in);
    public static void userMenu(User user, List<Movie> movies, List<Booking> bookings) {
       

        while (true) {
            System.out.println("\n--- User Panel ---");
            System.out.println("1. Book a Movie");
            System.out.println("2. View My Bookings");
            System.out.println("3. Cancel a Booking");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");

            int choice = getIntInput(sc);

            switch (choice) {
                case 1:
                    bookMovie(movies, bookings, user.getUserId());
                    break;
                case 2:
                    viewUserBookings(user.getUserId(), bookings, movies);
                    break;
                case 3:
                    System.out.print("Enter Booking ID to cancel: ");
                    int bookingId = getIntInput(sc);
                    cancelBooking(user.getUserId(), bookingId, bookings, movies);
                    FileManager.saveBookings(bookings);
                    FileManager.saveMovies(movies);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    public static void bookMovie(List<Movie> movies, List<Booking> bookings, int userId) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n--- Available Movies ---");
        for (Movie movie : movies) {
            System.out.println(movie); 
        }

        System.out.print("Enter Movie ID to book: ");
        int movieId = getIntInput(sc);

        Movie movie = movies.stream().filter(m -> m.getMovieId() == movieId).findFirst().orElse(null);
        if (movie == null) {
            System.out.println("Movie not found.");
            return;
        }

        System.out.println("Available seats: ");
        movie.displaySeatLayout();

        System.out.print("Enter seats to book (comma-separated like A1,A2): ");
        String[] selectedSeats = sc.nextLine().split(",");

        int remainingSeats = (int) movie.getSeatMap().values().stream().filter(val -> val).count();
        double total = 0;

        for (String seat : selectedSeats) {
            seat = seat.trim();
            if (!movie.isSeatAvailable(seat)) {
                System.out.println("Seat " + seat + " is not available.");
                return;
            }
            if (remainingSeats <= 3) {
                total += movie.getPrice() * 1.5;
            } else {
                total += movie.getPrice();
            }
            movie.bookSeat(seat);
            remainingSeats--;
        }

        int bookingId = bookings.size() + 1;

        Booking booking = new Booking(userId, movieId, String.join(",", selectedSeats), total, bookingId);
        bookings.add(booking);

        FileManager.saveBookings(bookings);
        FileManager.saveMovies(movies);

        System.out.println("Booking successful with BookingID: " + bookingId + ". Total amount to pay: ₹" + total);
    }

    public static void viewUserBookings(int userId, List<Booking> bookings, List<Movie> movies) {
        System.out.println("\nYour Bookings:");
        System.out.println("+------------+----------------------+--------+--------+");
        System.out.printf("| %-10s | %-20s | %-6s | %-6s |\n", "BookingID", "Movie", "Seats", "Amount");
        System.out.println("+------------+----------------------+--------+--------+");

        boolean hasBookings = false;
        for (Booking b : bookings) {
            if (b.getUserId() == userId) {
                Movie m = movies.stream().filter(movie -> movie.getMovieId() == b.getMovieId()).findFirst().orElse(null);
                if (m != null) {
                    System.out.printf("| %-10d | %-20s | %-6s | ₹%-6.2f |\n",
                            b.getBookingId(), m.getTitle(), b.getSeats(), b.getTotalAmount());
                    hasBookings = true;
                }
            }
        }

        if (!hasBookings) {
            System.out.println("| No bookings found.                                         |");
        }

        System.out.println("+------------+----------------------+--------+--------+");
    }

    public static void cancelBooking(int userId, int bookingId, List<Booking> bookings, List<Movie> movies) {
     
    
        for (Booking b : bookings) {
            if (b.getBookingId() == bookingId && b.getUserId() == userId) {
                Movie m = movies.stream().filter(movie -> movie.getMovieId() == b.getMovieId()).findFirst().orElse(null);
                if (m == null) {
                    System.out.println("Movie not found.");
                    return;
                }
    
                String[] bookedSeats = b.getSeats().split(",");
                System.out.println("Your booked seats: ");
                for (String seat : bookedSeats) {
                    System.out.print(seat.trim() + " ");
                }
                System.out.println("\nEnter seats to cancel (comma-separated): ");
                String[] seatsToCancel = sc.nextLine().split(",");
    
                List<String> updatedSeats = new ArrayList<>();
                for (String seat : bookedSeats) {
                    String trimmedSeat = seat.trim();
                    boolean cancelThisSeat = false;
                    for (String cancelSeat : seatsToCancel) {
                        if (trimmedSeat.equalsIgnoreCase(cancelSeat.trim())) {
                            cancelThisSeat = true;
                            break;
                        }
                    }
                    if (cancelThisSeat) {
                        m.unbookSeat(trimmedSeat); 
                    } else {
                        updatedSeats.add(trimmedSeat); 
                    }
                }
    
                if (updatedSeats.isEmpty()) {
                    bookings.remove(b);
                    System.out.println("All seats cancelled. Booking removed.");
                } else {
                    b.setSeats(String.join(",", updatedSeats));
                    System.out.println("Selected seats cancelled. Remaining seats: " + String.join(", ", updatedSeats));
                }
                return;
            }
        }
    
        System.out.println("Booking not found.");
    }
    

   
    private static int getIntInput(Scanner sc) {
        while (true) {
            try {
                String input = sc.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}
