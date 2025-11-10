package dk.easv.mrs.GUI.Controller;

// Java imports
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
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
    private TextField txtMovieTitle;
    @FXML
    private TextField txtMovieYear;

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
            // Load the FXML for the popup window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CreateMovieView.fxml"));
            Parent root = loader.load();

            // Create a new Stage (popup window)
            Stage stage = new Stage();
            stage.setTitle("Create Movie");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // blocks parent window until closed
            stage.setResizable(false);
            stage.showAndWait(); // Waits for the popup to close before continuing
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
