package actions.onpage;

import input.Action;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Movie;
import printer.OutputPrinter;
import users.Credentials;
import users.User;

import java.util.ArrayList;

import static pages.PageStrings.HOMEPAGEONE;
import static pages.PageStrings.HOMEPAGETWO;
import static pages.PageStrings.REGISTER;
import static platform.Platform.currentMovieList;
import static platform.Platform.currentPage;

public class Register implements Feature {
    private User currentUser;
    private final ArrayList<User> users;
    private final ArrayList<Movie> movies;

    /**
     * Constructor for Register
     * @param currentUser the current user
     * @param users the list of users
     * @param movies the list of movies
     */
    public Register(final User currentUser, final ArrayList<User> users,
                    final ArrayList<Movie> movies) {
        this.currentUser = currentUser;
        this.users = users;
        this.movies = movies;
    }

    /**
     * Executes the register action
     * @param currentAction the current action
     * @param output the output
     * @param users the list of users
     */
    public void execute(final Action currentAction, final ArrayNode output,
                        final ArrayList<User> users) {
        OutputPrinter printer = OutputPrinter.getInstance();
        if (currentPage.equals(REGISTER)) {
            String name = currentAction.getCredentials().getName();
            int error = 0;
            /* check if the user already exists */
            for (User user : users) {
                if (user.getCredentials().getName().equals(name)) {
                    output.add(printer.printError());
                    currentPage = HOMEPAGEONE;
                    error = 1;
                    break;
                }
            }
            if (error == 0) {
                /* if the user does not exist, it's created */
                Credentials credentials = currentAction.getCredentials();
                User newUser = new User(credentials);
                this.users.add(newUser);
                currentUser = newUser;
                currentPage = HOMEPAGETWO;
                output.add(printer.printSuccess(currentUser, currentMovieList));
            }
        } else {
            /* if the current page is not register, the error is printed */
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
