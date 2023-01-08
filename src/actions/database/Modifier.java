package actions.database;

import input.Movie;
import users.User;

import java.util.ArrayList;

public class Modifier {
    private Command command;

    /**
     * Constructor for Invoker
     */
    public Modifier() {
    }

    /**
     * Setter for command
     * @param command
     */
    public void setCommand(final Command command) {
        this.command = command;
    }

    /**
     * Method that executes the command
     */
    public void executeCommand() {
        command.execute();
    }

    /**
     * Getter for users
     * @return users
     */
    public ArrayList<User> getUsers() {
        return command.getUsers();
    }

    /**
     * Getter for movies
     * @return movies
     */
    public ArrayList<Movie> getMovies() {
        return command.getMovies();
    }

    /**
     * Getter for currentMovieList
     * @return currentMovieList
     */
    public ArrayList<Movie> getCurrentMovieList() {
        return command.getCurrentMovieList();
    }
}
