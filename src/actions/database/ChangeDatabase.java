package actions.database;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Action;
import input.Movie;
import printer.OutputPrinter;
import users.User;

import java.util.ArrayList;

public class ChangeDatabase {

    private final Action currentAction;
    private final ArrayList<Movie> movies;
    private final ArrayList<Movie> currentMovieList;
    private final ArrayList<User> users;
    private final ArrayNode output;

    /**
     * Constructor for ChangeDatabase
     */
    public ChangeDatabase(final Action currentAction, final ArrayNode output,
                          final ArrayList<Movie> movies, final ArrayList<User> users,
                          final ArrayList<Movie> currentMovieList) {
        this.currentAction = currentAction;
        this.movies = movies;
        this.users = users;
        this.currentMovieList = currentMovieList;
        this.output = output;
    }

    /**
     * Method that adds a movie to the database
     */
    public void add() {
        Movie toAdd = currentAction.getAddedMovie();
        int error = 0;
        for (Movie movie : movies) {
            if (movie.getName().equals(toAdd.getName())) {
                error = 1;
                break;
            }
        }
        if (error == 0) {
            movies.add(toAdd);
            for (User user : users) {
                user.addMovieNotification(toAdd);
            }
        } else {
            output.add(OutputPrinter.getInstance().printError());
        }
    }

    /**
     * Method that deletes a movie from the database
     */
    public void delete() {
        String toRemove = currentAction.getDeletedMovie();
        int error = 0;
        for (Movie movie : movies) {
            if (movie.getName().equals(toRemove)) {
                movies.remove(movie);
                currentMovieList.remove(movie);
                for (User user : users) {
                    user.removeMovie(toRemove);
                }
                error = 1;
                break;
            }
        }
        if (error == 0) {
            output.add(OutputPrinter.getInstance().printError());
        }
    }

    /**
     * Getter for movies
     * @return movies
     */
    public ArrayList<Movie> getMovies() {
        return movies;
    }

    /**
     * Getter for currentMovieList
     * @return currentMovieList
     */
    public ArrayList<Movie> getCurrentMovieList() {
        return currentMovieList;
    }

    /**
     * Getter for users
     * @return users
     */
    public ArrayList<User> getUsers() {
        return users;
    }
}
