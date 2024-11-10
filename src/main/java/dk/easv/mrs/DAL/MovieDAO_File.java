package dk.easv.mrs.DAL;
import dk.easv.mrs.BE.Movie;
import java.io.*;
import java.util.List;

public class MovieDAO_File implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";

    public List<Movie> getAllMovies() throws IOException {
        return null;
    }

    @Override
    public Movie createMovie(String title, int year) throws Exception {
        return null;
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {
    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {
    }
}