import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Movie implements Serializable {
    private int movieId;
    private String title;
    private String genre;
    private String duration;
    private double price;
    private double rating;  // New property for rating
    private Map<String, Boolean> seatMap; // Seat ID → availability (false = booked)

    public Movie(int movieId, String title, String genre, String duration, double price, double rating) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.price = price;
        this.rating = rating;  // Initialize rating
        this.seatMap = initializeSeats(); // A1 to E5 = 25 seats
    }

    // Getters and Setters
    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getDuration() {
        return duration;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;  // Getter for rating
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRating(double rating) {
        this.rating = rating;  // Setter for rating
    }

    private Map<String, Boolean> initializeSeats() {
        Map<String, Boolean> seats = new LinkedHashMap<>();
        for (char row = 'A'; row <= 'E'; row++) {
            for (int col = 1; col <= 5; col++) {
                seats.put(row + String.valueOf(col), true); // true = available
            }
        }
        return seats;
    }

    public Map<String, Boolean> getSeatMap() {
        return seatMap;
    }

    public boolean bookSeat(String seat) {
        if (seatMap.containsKey(seat) && seatMap.get(seat)) {
            seatMap.put(seat, false); // mark seat as booked
            return true;
        }
        return false;
    }

    public void unbookSeat(String seat) {
        if (seatMap.containsKey(seat)) {
            seatMap.put(seat, true); // unbook for cancel
        }
    }

    // Booking count is the number of false values (booked seats) in the seatMap
    public int getBookingCount() {
        int bookedCount = 0;
        for (Boolean isAvailable : seatMap.values()) {
            if (!isAvailable) {
                bookedCount++;
            }
        }
        return bookedCount;
    }

    public void displaySeatLayout() {
        System.out.println("\nSeat Layout (✔ = Available, ✘ = Booked):");
        // Column headers
        System.out.print("    ");
        for (int col = 1; col <= 10; col++) {
            System.out.printf("%2d ", col);
        }
        System.out.println();

        for (char row = 'A'; row <= 'E'; row++) {
            System.out.print(row + " | ");
            for (int col = 1; col <= 10; col++) {
                String seat = row + String.valueOf(col);
                boolean available = seatMap.getOrDefault(seat, true);
                System.out.print((available ? "✔" : "✘") + "  ");
            }
            System.out.println();
        }
    }

    public boolean isSeatAvailable(String seat) {
        return seatMap.containsKey(seat) && seatMap.get(seat);
    }

    @Override
    public String toString() {
        return String.format("| %-9d | %-19s | %-9s | %-9s | %-7.2f | %-7.2f |", movieId, title, genre, duration, price, rating);
    }
}
