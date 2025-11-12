package dk.easv.mrs.DAL;

// Java imports
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Project imports
import dk.easv.mrs.BE.Movie;

public class MovieDAO_File implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";
    private List<Movie> allMovies;

    //The @Override annotation is not required, but is recommended for readability
    // and to force the compiler to check and generate error msg. if needed etc.
    @Override
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
        List<Movie> movies = getAllMovies();

        int newId = movies.isEmpty() ? 1 : movies.get(movies.size() - 1).getId() + 1;

        Movie newMovie = new Movie(newId, year, title);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MOVIES_FILE, true))) {
            bw.write(newId + "," + year + "," + title);
            bw.newLine();
        }

        movies.add(newMovie);
        allMovies = movies;
        return newMovie;
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {
        List<Movie> movies = getAllMovies();
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getId() == movie.getId()) {
                movies.set(i, movie);
                break;
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MOVIES_FILE))) {
            for (Movie m : movies) {
                bw.write(m.getId() + "," + m.getYear() + "," + m.getTitle());
                bw.newLine();
            }
        }

        allMovies = movies;
    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {
        List<Movie> movies = getAllMovies();
        movies.removeIf(m -> m.getId() == movie.getId());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MOVIES_FILE))) {
            for (Movie m : movies) {
                bw.write(m.getId() + "," + m.getYear() + "," + m.getTitle());
                bw.newLine();
            }
        }

        allMovies = movies;
    }
}