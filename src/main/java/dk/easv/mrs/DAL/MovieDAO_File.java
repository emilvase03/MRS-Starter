package dk.easv.mrs.DAL;
import dk.easv.mrs.BE.Movie;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO_File implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";
    private List<Movie> allMovies;

    //The @Override annotation is not required, but is recommended for readability
    // and to force the compiler to check and generate error msg. if needed etc.
    //@Override
    public List<Movie> getAllMovies() throws IOException
    {
        allMovies = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(MOVIES_FILE));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1].trim());
            String title = parts[2].trim();
            Movie movie = new Movie(id, year, title);
            allMovies.add(movie);
        }
        br.close();
        return allMovies;
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