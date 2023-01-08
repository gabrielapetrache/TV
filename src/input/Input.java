package input;

import users.User;

import java.util.ArrayList;

public class Input {
    private ArrayList<User> users;
    private ArrayList<Movie> movies;
    private ArrayList<Action> actions;

    /**
     * Default constructor
     */
    public Input() {
    }

    /**
     * getter for users
     * @return users
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * setter for users
     * @param credentials users
     */
    public void setUsers(final ArrayList<User> credentials) {
        this.users = credentials;
    }

    /**
     * getter for moviesList
     * @return moviesList
     */
    public ArrayList<Movie> getMovies() {
        return movies;
    }

    /**
     * setter for moviesList
     * @param moviesList moviesList
     */
    public void setMovies(final ArrayList<Movie> moviesList) {
        this.movies = moviesList;
    }

    /**
     * getter for commands
     * @return commands
     */
    public ArrayList<Action> getActions() {
        return actions;
    }

    /**
     * setter for commands
     * @param commands commands
     */
    public void setActions(final ArrayList<Action> commands) {
        this.actions = commands;
    }

    /**
     * Method for printing
     */
    @Override
    public String toString() {
        return "Input {users="
                + users
                + ", movies="
                + movies
                + ", actions="
                + actions
                + "}";
    }
}
