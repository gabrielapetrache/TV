package actions.onpage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Movie;
import printer.OutputPrinter;
import users.User;

import java.util.ArrayList;

import static pages.PageStrings.MOVIES;
import static platform.Platform.currentMovieList;
import static platform.Platform.currentPage;

public class Search implements Feature {
    private final User currentUser;
    private final ArrayList<User> users;
    private final ArrayList<Movie> movies;

    /**
     * Constructor for the Search class
     * @param currentUser the current user
     * @param users the list of users
     * @param movies the list of movies
     */
    public Search(final User currentUser, final ArrayList<User> users,
                  final ArrayList<Movie> movies) {
        this.currentUser = currentUser;
        this.users = users;
        this.movies = movies;
    }

    /**
     * Executes the search action
     * @param currentAction the current action
     * @param output the output
     * @param users the list of users
     */
    public void execute(final Action currentAction, final ArrayNode output,
                        final ArrayList<User> users) {
        OutputPrinter printer = OutputPrinter.getInstance();
        if (currentPage.equals(MOVIES)) {
            String startsWith = currentAction.getStartsWith();
            ArrayList<Movie> copyMovieList = new ArrayList<>(currentMovieList);

            for (Movie movie : copyMovieList) {
                for (String ban : movie.getCountriesBanned()) {
                    if (ban.equals(currentUser.getCredentials().getCountry())
                            || !movie.getName().startsWith(startsWith)) {
                        currentMovieList.remove(movie);
                    }
                }
            }
            output.add(printer.printSuccess(currentUser, currentMovieList));
        } else {
            /* if the current page is not movies, the error is printed */
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
