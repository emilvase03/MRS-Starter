package dk.easv.mrs.GUI.Controller;

// Java imports
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

// Project imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.GUI.Model.MovieModel;

public class MovieViewController implements Initializable {


    public TextField txtMovieSearch;
    public ListView<Movie> lstMovies;
    private MovieModel movieModel;

    @FXML
    private Label lblResults;
    @FXML
    private MenuButton btnMenuOptions;

    public MovieViewController()  {
        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        lstMovies.setItems(movieModel.getObservableMovies());

        lblResults.textProperty().bind(
                Bindings.concat("Total Movies: ", Bindings.size(movieModel.getObservableMovies()))
        );

        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });

        lstMovies.getSelectionModel().selectedItemProperty().addListener((obs, oldMovie, newMovie) -> {
            if (newMovie != null) {
                btnMenuOptions.setOpacity(1);
            } else {
                btnMenuOptions.setOpacity(0);
            }
        });
    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    @FXML
    private void onBtnCreateMovieAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CreateMovieView.fxml"));
            Parent root = loader.load();

            CreateMovieController controller = loader.getController();
            controller.setMovieModel(movieModel);

            Stage stage = new Stage();
            stage.setTitle("Create Movie");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            stage.showAndWait();

            lstMovies.setItems(movieModel.getObservableMovies());

            // auto-select movie on add.
            if (controller.isMovieCreated()) {
                int newIndex = movieModel.getObservableMovies().size() - 1;
                if (newIndex >= 0) {
                    lstMovies.getSelectionModel().select(newIndex);
                    lstMovies.scrollTo(newIndex);
                }
            }

        } catch (IOException e) {
            displayError(e);
            e.printStackTrace();
        }
    }


    @FXML
    private void onBtnDeleteMovieAction(ActionEvent actionEvent) {
        Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();
        if (selectedMovie == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Movie");
        confirm.setHeaderText("Are you sure you want to delete \"" + selectedMovie.getTitle() + "\"?");
        confirm.setContentText("This action cannot be undone.");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    movieModel.deleteMovie(selectedMovie);
                    movieModel.getObservableMovies().remove(selectedMovie);
                    btnMenuOptions.setOpacity(0); // hide again
                    lstMovies.refresh();
                } catch (Exception e) {
                    displayError(e);
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    private void onBtnEditMovieAction(ActionEvent actionEvent) {
        Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();
        if (selectedMovie == null)
            return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditMovieView.fxml"));
            Parent root = loader.load();

            EditMovieController controller = loader.getController();
            controller.setMovieModel(movieModel);
            controller.setMovieToEdit(selectedMovie);

            Stage stage = new Stage();
            stage.setTitle("Edit Movie");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            stage.showAndWait();

            lstMovies.refresh();

        } catch (IOException e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    @FXML
    private void onBtnRefreshAction(ActionEvent actionEvent) {
        try {
            txtMovieSearch.clear();
            movieModel.reloadAllMovies();
            lstMovies.refresh();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }
}
