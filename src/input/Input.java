package input;

import pages.Actions;
import users.User;

import java.util.ArrayList;

public class Input {
    private ArrayList<User> users;
    private ArrayList<Movie> movies;
    private ArrayList<Actions> actions;

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
     * @param credentials
     */
    public void setUsers(ArrayList<User> credentials) {
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
     * @param moviesList
     */
    public void setMovies(ArrayList<Movie> moviesList) {
        this.movies = moviesList;
    }

    /**
     * getter for commands
     * @return commands
     */
    public ArrayList<Actions> getActions() {
        return actions;
    }

    /**
     * setter for commands
     * @param commands
     */
    public void setActions(ArrayList<Actions> commands) {
        this.actions = commands;
    }

    @Override
    public String toString() {
        return "Input {users=" +
                users +
                ", movies=" +
                movies +
                ", actions=" +
                actions +
                "}";
    }
}
