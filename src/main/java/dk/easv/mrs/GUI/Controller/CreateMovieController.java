package dk.easv.mrs.GUI.Controller;

import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class CreateMovieController {
    private MovieModel movieModel;

    @FXML
    private TextField txtMovieTitle;
    @FXML
    private TextField txtMovieYear;

    public CreateMovieController()  {
        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    @FXML
    private void onBtnAddMovieAction(ActionEvent actionEvent) {
        String title = txtMovieTitle.getText();
        String yearText = txtMovieYear.getText();

        if (title.isEmpty() || yearText.isEmpty()) {
            displayError(new Exception("Title and Year must not be empty."));
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException e) {
            displayError(new Exception("Year must be a valid integer."));
            return;
        }

        try {
            movieModel.createMovie(title, year);
            movieModel.getObservableMovies().clear();
            movieModel.getObservableMovies().addAll(movieModel.movieManager.getAllMovies());
            txtMovieTitle.clear();
            txtMovieYear.clear();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }
}
