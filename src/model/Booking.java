package model;

import java.io.Serializable;

public class Booking implements Serializable {
    private String username;
    private String movieTitle;

    public Booking(String username, String movieTitle) {
        this.username = username;
        this.movieTitle = movieTitle;
    }

    public String getUsername() {
        return username;
    }

    public String getMovieTitle() {
        return movieTitle;
    }
}
