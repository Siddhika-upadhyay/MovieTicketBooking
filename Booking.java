import java.io.Serializable;
import java.util.Date;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    private int bookingId;
    private int userId;
    private int movieId;
    private String seats;
    private double totalAmount;
    private Date bookingTime; // Add this field

    public Booking(int userId, int movieId, String seats, double totalAmount, int bookingId) {
        this.userId = userId;
        this.movieId = movieId;
        this.seats = seats;
        this.totalAmount = totalAmount;
        this.bookingTime = new Date();
        this.bookingId = bookingId;
    }
    

    public int getBookingId() {
        return bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getSeats() {
        return seats;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Date getBookingTime() {
        return bookingTime;
    }

    public String toRow(Movie movie) {
        return String.format("%-10d %-20s %-10s ₹%-10.2f", bookingId, movie.getTitle(), seats, totalAmount);
    }


  
}
