package dk.easv.mrs.GUI.Model;
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public class MovieModel {

    private FilteredList<Movie> filteredList;
    private ObservableList<Movie> moviesToBeViewed;
    private MovieManager movieManager;

    public MovieModel() throws Exception {
        movieManager = new MovieManager();
        moviesToBeViewed = FXCollections.observableArrayList();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
        filteredList = new FilteredList<>(moviesToBeViewed);
    }

    public FilteredList<Movie> getObservableMovies() {
        return filteredList;
    }

    public Movie createMovie(Movie newMovie) throws Exception {
        Movie movieCreated = movieManager.createMovie(newMovie);
        moviesToBeViewed.add(movieCreated);
        return movieCreated;
    }

    public void updateMovie(Movie updatedMovie) throws Exception {
        movieManager.updateMovie(updatedMovie);
    }

    public void deleteMovie(Movie selectedMovie) throws Exception {
        movieManager.deleteMovie(selectedMovie);
        moviesToBeViewed.remove(selectedMovie);
    }

    public void reloadAllMovies() throws Exception {
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(movieManager.getAllMovies());
    }
}