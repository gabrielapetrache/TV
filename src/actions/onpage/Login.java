package actions.onpage;

import input.Action;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Movie;
import printer.OutputPrinter;
import users.User;

import java.util.ArrayList;

import static pages.PageStrings.LOGIN;
import static pages.PageStrings.HOMEPAGETWO;
import static pages.PageStrings.HOMEPAGEONE;
import static platform.Platform.currentMovieList;
import static platform.Platform.currentPage;
public class Login implements Feature {

    private User currentUser;
    private final ArrayList<User> users;
    private final ArrayList<Movie> movies;

    /**
     * Constructor for Login
     * @param currentUser the current user
     * @param users the list of users
     * @param movies the list of movies
     */
    public Login(final User currentUser, final ArrayList<User> users,
                 final ArrayList<Movie> movies) {
        this.currentUser = currentUser;
        this.users = users;
        this.movies = movies;
    }

    /**
     * Executes the login action
     * @param currentAction the current action
     * @param output the output
     * @param users the list of users
     */
    public void execute(final Action currentAction, final ArrayNode output,
                        final ArrayList<User> users) {
        OutputPrinter printer = OutputPrinter.getInstance();
        if (currentPage.equals(LOGIN)) {
            String name = currentAction.getCredentials().getName();
            String pass = currentAction.getCredentials().getPassword();
            /* check if the user exists */
            for (User user : users) {
                if (user.getCredentials().getName().equals(name)
                        && user.getCredentials().getPassword().equals(pass)) {
                    currentUser = user;
                    currentPage = HOMEPAGETWO;
                    currentMovieList = new ArrayList<>();
                    output.add(printer.printSuccess(currentUser,
                            currentMovieList));
                    return;
                }
            }
            /* if the user is not found, the error is printed */
            if (currentUser == null) {
                currentPage = HOMEPAGEONE;
                output.add(printer.printError());
            }
        } else {
            /* if the current page is not login, the error is printed */
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
