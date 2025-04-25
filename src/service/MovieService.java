package service;

import model.Movie;
import util.Serializer;

import java.util.List;

public class MovieService {

    private static final String MOVIE_DATA_FILE = "data/movies.dat";

    public static void addMovie(Movie movie) {
        List<Movie> movies = Serializer.deserialize(MOVIE_DATA_FILE);
        movies.add(movie);
        Serializer.serialize(movies, MOVIE_DATA_FILE);
    }

    public static void removeMovie(String movieTitle) {
        List<Movie> movies = Serializer.deserialize(MOVIE_DATA_FILE);
        movies.removeIf(movie -> movie.getTitle().equals(movieTitle));
        Serializer.serialize(movies, MOVIE_DATA_FILE);
    }

    public static List<Movie> getMovies() {
        return Serializer.deserialize(MOVIE_DATA_FILE);
    }

    public static Movie getMovieByTitle(String title) {
        List<Movie> movies = Serializer.deserialize(MOVIE_DATA_FILE);
        for (Movie movie : movies) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }
        return null;
    }
}
