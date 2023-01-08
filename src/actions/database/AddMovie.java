package actions.database;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Action;
import input.Movie;
import users.User;

import java.util.ArrayList;

public class AddMovie implements Command {
    private ChangeDatabase changeDatabase;

    public AddMovie(final ChangeDatabase changeDatabase) {
        this.changeDatabase = changeDatabase;
    }

    public void execute() {
        changeDatabase.add();
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
