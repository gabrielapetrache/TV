package actions.onpage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Movie;
import printer.OutputPrinter;
import users.User;

import java.util.ArrayList;

import static pages.PageStrings.DETAILS;
import static platform.Platform.currentMovieList;
import static platform.Platform.currentPage;

public class Watch implements Feature {
    private final User currentUser;
    private final ArrayList<User> users;
    private final ArrayList<Movie> movies;

    /**
     * Constructor for Watch
     * @param currentUser the current user
     * @param users the list of users
     * @param movies the list of movies
     */
    public Watch(final User currentUser, final ArrayList<User> users,
                 final ArrayList<Movie> movies) {
        this.currentUser = currentUser;
        this.users = users;
        this.movies = movies;
    }

    /**
     * Executes the watch action
     * @param currentAction the current action
     * @param output the output
     * @param users the list of users
     */
    public void execute(final Action currentAction, final ArrayNode output,
                        final ArrayList<User> users) {
        OutputPrinter printer = OutputPrinter.getInstance();
        if (currentPage.equals(DETAILS)) {
            if (currentMovieList.isEmpty()) {
                output.add(printer.printError());
                return;
            }
            Movie toWatch = currentMovieList.get(0);

            int error = currentUser.watchMovie(toWatch);
            if (error == -1) {
                output.add(printer.printError());
                return;
            }
            if (error == 0) {
                output.add(printer.printSuccess(currentUser,
                        currentMovieList));
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
