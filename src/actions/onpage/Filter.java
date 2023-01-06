package actions.onpage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ArrayNode;
import filters.Filters;
import input.Movie;
import printer.OutputPrinter;
import users.User;

import java.util.ArrayList;

import static pages.PageStrings.MOVIES;
import static platform.Platform.currentMovieList;
import static platform.Platform.currentPage;

public class Filter implements Feature {
    private final User currentUser;
    private final ArrayList<User> users;
    private final  ArrayList<Movie> movies;

    /**
     * Constructor for the Filter class
     * @param currentUser the current user
     * @param users the list of users
     * @param movies the list of movies
     */
    public Filter(final User currentUser, final ArrayList<User> users,
                  final ArrayList<Movie> movies) {
        this.currentUser = currentUser;
        this.users = users;
        this.movies = movies;
    }

    /**
     * Executes the filter action
     * @param currentAction the current action
     * @param output the output
     * @param users the list of users
     */
    public void execute(final Action currentAction, final ArrayNode output,
                        final ArrayList<User> users) {
        OutputPrinter printer = OutputPrinter.getInstance();
        if (currentPage.equals(MOVIES)) {
            Filters filter = currentAction.getFilters();
            if (currentMovieList.isEmpty()) {
               currentMovieList = filterMovies(movies, currentUser);
            }
            currentMovieList = filter.filter(currentMovieList);
            output.add(printer.printSuccess(currentUser, currentMovieList));
        } else {
            /* if the current page is not movies, the error is printed */
            output.add(printer.printError());
        }
    }

    /**
     * Method that filters the movies based on the user's country
     *
     * @param movies the list of movies
     * @param currentUser the user that is currently logged in
     * @return the list of movies filtered by the user's country
     */
    public ArrayList<Movie> filterMovies(final ArrayList<Movie> movies, final User currentUser) {
        ArrayList<Movie> currentMovieList = new ArrayList<>(movies);
        for (Movie movie : movies) {
            for (String ban : movie.getCountriesBanned()) {
                if (ban.equals(currentUser.getCredentials().getCountry())) {
                    currentMovieList.remove(movie);
                }
            }
        }
        return currentMovieList;
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
