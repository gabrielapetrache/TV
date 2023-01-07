package actions.onpage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Movie;
import printer.OutputPrinter;
import users.User;

import java.util.ArrayList;

import static pages.PageStrings.DETAILS;
import static platform.Platform.currentPage;

public class Subscribe implements Feature {
    private final User currentUser;
    private final ArrayList<User> users;
    private final ArrayList<Movie> movies;

    /**
     * Constructor for Subscribe
     */
    public Subscribe(final User currentUser, final ArrayList<User> users,
                     final ArrayList<Movie> movies) {
        this.currentUser = currentUser;
        this.users = users;
        this.movies = movies;
    }

    /**
     * Executes the subscribe action
     * @param currentAction the current action
     * @param output the output
     * @param users the list of users
     */
    public void execute(final Action currentAction, final ArrayNode output,
                        final ArrayList<User> users) {
        if (currentUser != null && currentPage.equals(DETAILS)) {
            if (currentUser.getSubscribedGenres().contains(currentAction.getSubscribedGenre())) {
                output.add(OutputPrinter.getInstance().printError());
            } else {
                currentUser.addGenre(currentAction.getSubscribedGenre());
            }
        } else {
            output.add(OutputPrinter.getInstance().printError());
        }
    }

    /**
     * Getter for the current user
     * @return the current user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Getter for the list of users
     * @return the list of users
     */
    public ArrayList<User> getUsers() {
        return users;
    }
}
