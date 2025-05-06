import java.io.*;
import java.util.*;


public class TicketBookingSystem {
    static final String USERS_FILE = "users.ser";
    static final String MOVIES_FILE = "movies.ser";
    static final String BOOKINGS_FILE = "bookings.ser";
    static final String ADMIN_FILE = "admin.ser";

    static List<User> users = FileManager.loadFromFile(USERS_FILE);
    static List<Movie> movies = FileManager.loadFromFile(MOVIES_FILE);
    static List<Booking> bookings = FileManager.loadFromFile(BOOKINGS_FILE);
    private static User admin = null;
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        loadData();
        loadAdmin();

        if (admin == null) {
            adminRegistration();
        }

        while (true) {
            System.out.println("\n--- Movie Ticket Booking System ---");
            viewMovies();
            System.out.println("\n1. User Login / Register");
            System.out.println("2. Admin Login");
            System.out.println("3. View Movies by Rating");
            System.out.println("4. View Movies by Booking Count");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = getIntInput();

            switch (choice) {
                case 1:
                    userLoginOrRegister();
                    break;
                case 2:
                    adminLogin();
                    break;
                case 3:
                    viewMoviesByRating();
                    break;
                case 4:
                    viewMoviesByBookingCount();
                    break;
                case 5:
                    saveData();
                    saveAdmin();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Method to view movies sorted by rating
    public static void viewMoviesByRating() {
        PriorityQueue<Movie> ratingQueue = new PriorityQueue<>(
            (m1, m2) -> Double.compare(m2.getRating(), m1.getRating()) // Sorting by rating (descending)
        );

        ratingQueue.addAll(movies);

        System.out.println("\n🎬 Top Rated Movies:");
        while (!ratingQueue.isEmpty()) {
            Movie m = ratingQueue.poll();
            System.out.println(m.getTitle() + " - ⭐ " + m.getRating());
        }
    }

   
    public static void viewMoviesByBookingCount() {
        PriorityQueue<Movie> bookingQueue = new PriorityQueue<>(
            (m1, m2) -> Integer.compare(m2.getBookingCount(), m1.getBookingCount()) // Sorting by booking count (descending)
        );

        bookingQueue.addAll(movies);

        System.out.println("\n🎟️ Most Booked Movies:");
        while (!bookingQueue.isEmpty()) {
            Movie m = bookingQueue.poll();
            System.out.println(m.getTitle() + " - 📈 Bookings: " + m.getBookingCount());
        }
    }
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
    
    public static boolean isValidPassword(String password) {
        return password.length() >= 6 && password.matches(".*[A-Za-z].*") && password.matches(".*\\d.*");
    }
    
    public static String getNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("Input cannot be empty. Please try again.");
        }
    }
    

    public static void adminRegistration() {
        System.out.println("\n--- Admin Registration ---");
        String name = getNonEmptyString("Enter admin name: ");
        
        String email;
        do {
            email = getNonEmptyString("Enter admin email: ");
            if (!isValidEmail(email)) {
                System.out.println("Invalid email format. Try again.");
            }
        } while (!isValidEmail(email));
        
        String password;
        do {
            password = getNonEmptyString("Enter admin password: ");
            if (!isValidPassword(password)) {
                System.out.println("Password must be at least 6 characters long and contain both letters and digits.");
            }
        } while (!isValidPassword(password));
    
        admin = new User(0, name, email, password);
        System.out.println("Admin registered successfully.");
        saveAdmin();
    }
    
    
    public static void adminLogin() {
        System.out.println("\n--- Admin Login ---");
    
        String email;
        do {
            email = getNonEmptyString("Enter admin email: ");
            if (!isValidEmail(email)) {
                System.out.println("Invalid email format. Please enter a valid email.");
            }
        } while (!isValidEmail(email));
    
        String password;
        do {
            password = getNonEmptyString("Enter admin password: ");
            if (!isValidPassword(password)) {
                System.out.println("Password must be at least 6 characters long and contain both letters and digits.");
            }
        } while (!isValidPassword(password));
    
        if (admin != null && admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
            System.out.println("Admin login successful!");
        System.out.println();
            System.out.println("Welcome Admin " + admin.getName() + "!");
            AdminPanel adminPanel = new AdminPanel(movies, bookings);
            adminPanel.showAdminMenu();
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }
    

    public static void userLoginOrRegister() {
        System.out.println("\n--- User Login/Register ---");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Enter your choice: ");
        int choice = getIntInput();
        if (choice == 1) {
            registerUser();
        } else if (choice == 2) {
            loginUser();
        } else {
            System.out.println("Invalid choice.");
        }
    }

    public static void registerUser() {
        String name = getNonEmptyString("Enter name: ");
    
        String email;
        do {
            email = getNonEmptyString("Enter email: ");
            if (!isValidEmail(email)) {
                System.out.println("Invalid email format. Try again.");
            }
        } while (!isValidEmail(email));
    
        String password;
        do {
            password = getNonEmptyString("Enter password: ");
            if (!isValidPassword(password)) {
                System.out.println("Password must be at least 6 characters long and contain both letters and digits.");
            }
        } while (!isValidPassword(password));
    
        int userId = users.size() + 1;
        User user = new User(userId, name, email, password);
        users.add(user);
        currentUser = user;
    
        System.out.println("User registered and logged in successfully!");
        System.out.println();
        System.out.println("Welcome User " + user.getName() + "!");
        UserPanel.userMenu(currentUser, movies, bookings);
    }
    

    public static void loginUser() {
        System.out.println("\n--- User Login ---");
    
        String email;
        do {
            email = getNonEmptyString("Enter email: ");
            if (!isValidEmail(email)) {
                System.out.println("Invalid email format. Please enter a valid email.");
            }
        } while (!isValidEmail(email));
    
        String password;
        do {
            password = getNonEmptyString("Enter password: ");
            if (!isValidPassword(password)) {
                System.out.println("Password must be at least 6 characters long and contain both letters and digits.");
            }
        } while (!isValidPassword(password));
    
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("Login successful!");
                System.out.println();
                System.out.println("Welcome User " + user.getName() + "!");
                UserPanel.userMenu(currentUser, movies, bookings);
                return;
            }
        }
        System.out.println("Invalid credentials. Please try again.");
    }
    public static void viewMovies() {
        System.out.println("\nAvailable Movies:");
        if (movies.isEmpty()) {
            System.out.println("No movies available.");
            return;
        }
    
        // Print table header with borders
        System.out.println("+-----+------------------------------+-----------------+");
        System.out.printf("| %-3s | %-28s | %-15s |\n", "ID", "Title", "Genre");
        System.out.println("+-----+------------------------------+-----------------+");
    
        // Print each movie row
        for (Movie movie : movies) {
            System.out.printf("| %-3d | %-28s | %-15s |\n", 
                movie.getMovieId(), 
                movie.getTitle(), 
                movie.getGenre()
            );
        }
    
        // Print footer
        System.out.println("+-----+------------------------------+-----------------+");
    }
    
    

    public static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    public static void saveAdmin() {
        try (ObjectOutputStream aos = new ObjectOutputStream(new FileOutputStream(ADMIN_FILE))) {
            aos.writeObject(admin);
        } catch (IOException e) {
            System.out.println("Failed to save admin data.");
        }
    }

    public static void loadAdmin() {
        File file = new File(ADMIN_FILE);
        if (file.exists()) {
            try (ObjectInputStream ais = new ObjectInputStream(new FileInputStream(ADMIN_FILE))) {
                admin = (User) ais.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Failed to load admin data.");
                admin = null;
            }
        } else {
            admin = null;
        }
    }

    public static void saveData() {
        try (ObjectOutputStream mos = new ObjectOutputStream(new FileOutputStream(MOVIES_FILE));
             ObjectOutputStream uos = new ObjectOutputStream(new FileOutputStream(USERS_FILE));
             ObjectOutputStream bos = new ObjectOutputStream(new FileOutputStream(BOOKINGS_FILE))) {
            mos.writeObject(movies);
            uos.writeObject(users);
            bos.writeObject(bookings);
        } catch (IOException e) {
            System.out.println("Failed to save data.");
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadData() {
        File moviesFile = new File(MOVIES_FILE);
        File usersFile = new File(USERS_FILE);
        File bookingsFile = new File(BOOKINGS_FILE);
    
        if (moviesFile.exists() && usersFile.exists() && bookingsFile.exists()) {
            try (ObjectInputStream mos = new ObjectInputStream(new FileInputStream(MOVIES_FILE));
                 ObjectInputStream uos = new ObjectInputStream(new FileInputStream(USERS_FILE));
                 ObjectInputStream bos = new ObjectInputStream(new FileInputStream(BOOKINGS_FILE))) {
                movies = (ArrayList<Movie>) mos.readObject();
                users = (ArrayList<User>) uos.readObject();
                bookings = (ArrayList<Booking>) bos.readObject();
            } catch (Exception e) {
                System.out.println("Failed to load data.");
            }
        } else {
            movies = new ArrayList<>();
            users = new ArrayList<>();
            bookings = new ArrayList<>();
        }
    }
}
