package platform;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Actions;
import input.Input;
import input.Movies;
import pages.Page;
import users.Users;

import java.util.ArrayList;

public class Platform {
    private final ArrayNode output;
    private final ArrayList<Users> users;
    private final ArrayList<Movies> movies;
    private final ArrayList<Actions> actions;
    private Page currentPage;

    public Platform(final Input input, final ArrayNode output) {
        this.output = output;
        this.users = input.getUsers();
        this.actions = input.getActions();
        this.movies = input.getMovies();
    }

    public void stream() {
    }
}
