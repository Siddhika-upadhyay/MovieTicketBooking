package service;

import model.Booking;
import util.LinkedList;
import util.Serializer;

public class BookingService {

    private static final String BOOKING_DATA_FILE = "data/bookings.dat";
    private static LinkedList<Booking> bookings;

    static {
        bookings = new LinkedList<>();
    }

    public static void bookTicket(String username, String movieTitle) {
        Booking booking = new Booking(username, movieTitle);
        bookings.add(booking);
        Serializer.serialize(bookings, BOOKING_DATA_FILE);
    }

    public static LinkedList<Booking> getBookings() {
        return bookings;
    }

    public static void cancelBooking(String username, String movieTitle) {
        bookings.removeIf(booking -> booking.getUsername().equals(username) && booking.getMovieTitle().equals(movieTitle));
        Serializer.serialize(bookings, BOOKING_DATA_FILE);
    }
}
