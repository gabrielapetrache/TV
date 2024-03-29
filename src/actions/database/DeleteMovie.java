package actions.database;

import input.Movie;
import users.User;

import java.util.ArrayList;

public class DeleteMovie implements Command {
    private ChangeDatabase changeDatabase;

    /**
     * Constructor for DeleteMovie
     */
    public DeleteMovie(final ChangeDatabase changeDatabase) {
        this.changeDatabase = changeDatabase;
    }

    /**
     * Method that executes the command
     */
    public void execute() {
        changeDatabase.delete();
    }

    /**
     * Getter for users
     * @return users
     */
    public ArrayList<User> getUsers() {
        return changeDatabase.getUsers();
    }

    /**
     * Getter for movies
     * @return movies
     */
    public ArrayList<Movie> getMovies() {
        return changeDatabase.getMovies();
    }

    /**
     * Getter for currentMovieList
     * @return currentMovieList
     */
    public ArrayList<Movie> getCurrentMovieList() {
        return changeDatabase.getCurrentMovieList();
    }
}
