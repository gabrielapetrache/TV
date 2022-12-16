package input;

import pages.Actions;
import users.Users;

import java.util.ArrayList;

public class Input {
    private ArrayList<Users> users;
    private ArrayList<Movies> movies;
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
    public ArrayList<Users> getUsers() {
        return users;
    }

    /**
     * setter for users
     * @param credentials
     */
    public void setUsers(ArrayList<Users> credentials) {
        this.users = credentials;
    }

    /**
     * getter for moviesList
     * @return moviesList
     */
    public ArrayList<Movies> getMovies() {
        return movies;
    }

    /**
     * setter for moviesList
     * @param moviesList
     */
    public void setMovies(ArrayList<Movies> moviesList) {
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
