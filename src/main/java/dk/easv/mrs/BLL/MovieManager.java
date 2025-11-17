package dk.easv.mrs.BLL;

import java.io.IOException;
import java.util.List;
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BLL.util.MovieSearcher;
import dk.easv.mrs.DAL.IMovieDataAccess;
import dk.easv.mrs.DAL.db.MovieDAO_DB;

public class MovieManager {

    private final MovieSearcher movieSearcher = new MovieSearcher();
    private final IMovieDataAccess movieDAO;

    public MovieManager() throws IOException {
        movieDAO = new MovieDAO_DB();
    }

    public List<Movie> getAllMovies() throws Exception {
        return movieDAO.getAllMovies();
    }

    public List<Movie> searchMovies(String query) throws Exception {
        List<Movie> allMovies = getAllMovies();
        return movieSearcher.search(allMovies, query);
    }

    public Movie createMovie(Movie newMovie) throws Exception {
        return movieDAO.createMovie(newMovie);
    }

    public void updateMovie(Movie movie) throws Exception {
        movieDAO.updateMovie(movie);
    }

    public void deleteMovie(Movie movie) throws Exception {
        movieDAO.deleteMovie(movie);
    }
}
