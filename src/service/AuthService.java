package service;

import model.User;
import model.Admin;
import util.Serializer;

import java.io.*;
import java.util.List;

public class AuthService {

    private static final String USER_DATA_FILE = "data/users.dat";
    private static final String ADMIN_DATA_FILE = "data/admins.dat";

    public static boolean loginUser(String username, String password) {
        List<User> users = Serializer.deserialize(USER_DATA_FILE);
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static boolean loginAdmin(String username, String password) {
        List<Admin> admins = Serializer.deserialize(ADMIN_DATA_FILE);
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static void registerUser(User user) {
        List<User> users = Serializer.deserialize(USER_DATA_FILE);
        users.add(user);
        Serializer.serialize(users, USER_DATA_FILE);
    }

    public static void registerAdmin(Admin admin) {
        List<Admin> admins = Serializer.deserialize(ADMIN_DATA_FILE);
        admins.add(admin);
        Serializer.serialize(admins, ADMIN_DATA_FILE);
    }
}
