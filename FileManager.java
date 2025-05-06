import java.io.*;
import java.util.List;

public class FileManager {
    public static <T> void saveToFile(String filename, List<T> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(list);
        } catch (IOException e) {
            System.out.println("Error saving to " + filename);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<T>) ois.readObject();
        } catch (Exception e) {
            return new java.util.ArrayList<>();
        }
    }

    public static void saveUsers(List<User> users) {
        saveToFile("users.dat", users);
    }

    public static List<User> loadUsers() {
        return loadFromFile("users.dat");
    }

    public static void saveMovies(List<Movie> movies) {
        saveToFile("movies.dat", movies);
    }

    public static List<Movie> loadMovies() {
        return loadFromFile("movies.dat");
    }

    public static void saveBookings(List<Booking> bookings) {
        saveToFile("bookings.dat", bookings);
    }

    public static List<Booking> loadBookings() {
        return loadFromFile("bookings.dat");
    }
}
