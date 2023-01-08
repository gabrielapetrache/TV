package actions.database;

import input.Movie;
import users.User;

import java.util.ArrayList;

public interface Command {
    /**
     * Method that executes the command
     */
    void execute();

    /**
     * Getter for users
     * @return users
     */
    ArrayList<User> getUsers();

    /**
     * Getter for movies
     * @return movies
     */
    ArrayList<Movie> getMovies();

    /**
     * Getter for currentMovieList
     * @return currentMovieList
     */
    ArrayList<Movie> getCurrentMovieList();
}
