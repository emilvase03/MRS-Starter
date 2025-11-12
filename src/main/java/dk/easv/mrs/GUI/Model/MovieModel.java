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

    public Movie createMovie(Movie movie) throws Exception {
        Movie movieCreated = movieManager.createMovie(movie);
        moviesToBeViewed.add(movieCreated);
        return movieCreated;
    }

    public void updateMovie(Movie movie) throws Exception {
        movieManager.updateMovie(movie);
    }

    public void deleteMovie(Movie movie) throws Exception {
        movieManager.deleteMovie(movie);
        moviesToBeViewed.remove(movie);
    }
}
