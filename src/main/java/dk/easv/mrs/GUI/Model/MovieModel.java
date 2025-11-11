package dk.easv.mrs.GUI.Model;

// Java imports
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

// Project imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BLL.MovieManager;

public class MovieModel {

    private final ObservableList<Movie> moviesToBeViewed;

    public final MovieManager movieManager;

    public MovieModel() throws Exception {
        movieManager = new MovieManager();
        moviesToBeViewed = FXCollections.observableArrayList();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }

    public ObservableList<Movie> getObservableMovies() {
        return moviesToBeViewed;
    }

    public void searchMovie(String query) throws Exception {
        List<Movie> searchResults = movieManager.searchMovies(query);
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(searchResults);
    }

    public Movie createMovie(String title, int year) throws Exception {
        try {
            Movie newMovie = movieManager.createMovie(title, year);
            moviesToBeViewed.add(newMovie);
            return newMovie;
        } catch (Exception e) {
            throw new Exception("Failed to create movie: " + e.getMessage());
        }
    }

    public void deleteMovie(Movie movie) throws Exception {
        try {
            movieManager.deleteMovie(movie);
            moviesToBeViewed.remove(movie);
        } catch (Exception e) {
            throw new Exception("Failed to delete movie: " + e.getMessage());
        }
    }
}
