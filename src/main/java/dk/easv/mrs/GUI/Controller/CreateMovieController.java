package dk.easv.mrs.GUI.Controller;

// Java imports
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.TextFormatter;

// Project imports
import dk.easv.mrs.GUI.Model.MovieModel;

public class CreateMovieController {

    @FXML
    private TextField txtMovieTitle;

    @FXML
    private TextField txtMovieYear;

    private MovieModel movieModel;
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
    private void onBtnAddMovieAction() {
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
            movieModel.createMovie(title, year);
            movieCreated = true; // <-- mark success
            closeWindow();
        } catch (Exception e) {
            showAlert("Failed to create movie: " + e.getMessage());
        }
    }

    @FXML
    private void onBtnCancelAction() {
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
}
