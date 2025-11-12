package dk.easv.mrs.GUI.Controller;

// Java imports
import dk.easv.mrs.BE.Movie;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// Project imports
import dk.easv.mrs.GUI.Model.MovieModel;

public class EditMovieController {

    @FXML
    private TextField txtMovieTitle;

    @FXML
    private TextField txtMovieYear;

    private MovieModel movieModel;
    private Movie movieToEdit;
    private boolean movieCreated = false;

    ChangeListener<String> forceNumberListener = (observable, oldValue, newValue) -> {
        if (!newValue.matches("\\d*"))
            ((StringProperty) observable).set(oldValue);
    };

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
        txtMovieYear.textProperty().addListener(forceNumberListener);
    }

    @FXML
    private void onBtnSaveMovieAction() {
        String title = txtMovieTitle.getText().trim();
        String yearText = txtMovieYear.getText().trim();

        if (title.isEmpty() || yearText.isEmpty()) {
            showAlert("Please enter both title and year.");
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException e) {
            showAlert("Year must be a number.");
            return;
        }

        try {
            movieToEdit.setTitle(title);
            movieToEdit.setYear(year);
            movieModel.updateMovie(movieToEdit);
            movieCreated = true;
            closeWindow();
        } catch (Exception e) {
            showAlert("Failed to update movie: " + e.getMessage());
        }
    }

    @FXML
    private void onBtnCancelEditMovieAction() {
        closeWindow();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) txtMovieTitle.getScene().getWindow();
        stage.close();
    }

    public boolean isMovieCreated() {
        return movieCreated;
    }

    public void setMovieToEdit(Movie selectedMovie) {
        movieToEdit = selectedMovie;
        txtMovieTitle.setText(movieToEdit.getTitle());
        txtMovieYear.setText(String.valueOf(movieToEdit.getYear()));
    }
}
