package actions.onpage;

import input.Action;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Movie;
import printer.OutputPrinter;
import users.User;

import java.util.ArrayList;

import static pages.PageStrings.UPGRADES;
import static platform.Platform.currentPage;

public class BuyToken implements Feature {
    private final User currentUser;
    private final ArrayList<User> users;
    private final ArrayList<Movie> movies;

    /**
     * Constructor for BuyToken
     * @param currentUser the current user
     * @param users the list of users
     * @param movies the list of movies
     */
    public BuyToken(final User currentUser, final ArrayList<User> users,
                    final ArrayList<Movie> movies) {
        this.currentUser = currentUser;
        this.users = users;
        this.movies = movies;
    }

    /**
     * Executes the action
     * @param currentAction the current action
     * @param output the output
     * @param users the list of users
     */
    public void execute(final Action currentAction, final ArrayNode output,
                        final ArrayList<User> users) {
        OutputPrinter printer = OutputPrinter.getInstance();
        if (currentPage.equals(UPGRADES)) {
            int error = currentUser.buyTokens(currentAction.getCount());
            if (error == -1) {
                output.add(printer.printError());
            }
        } else {
            /* if the current page is not upgrades, the error is printed */
            output.add(printer.printError());
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
