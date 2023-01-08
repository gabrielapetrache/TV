package actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Action;
import input.Movie;
import printer.OutputPrinter;
import users.User;

import java.util.ArrayList;

import static pages.PageStrings.HOMEPAGEONE;
import static pages.PageStrings.HOMEPAGETWO;
import static pages.PageStrings.LOGIN;
import static pages.PageStrings.UPGRADES;
import static pages.PageStrings.REGISTER;
import static pages.PageStrings.MOVIES;
import static pages.PageStrings.DETAILS;
import static platform.Platform.currentMovieList;

public class ChangePage {

    /**
     * Method that changes the current page if the conditions are acquired
     *
     * @param currentPage the page the user is currently on
     * @param toChange the page the user wants to go to
     * @return the page the user is currently on after the checks are done
     */
    public String errorCheck(final String currentPage, final String toChange) {
        switch (currentPage) {
            case HOMEPAGEONE:
                if (!toChange.equals(LOGIN)  && !toChange.equals(REGISTER)) {
                    return "error";
                }
                return toChange;

            case HOMEPAGETWO:
                if (toChange.equals(UPGRADES) || toChange.equals(MOVIES)
                         || toChange.equals(REGISTER)) {
                    return toChange;
                }
                return "error";

            case UPGRADES:
                if (toChange.equals(HOMEPAGETWO) || toChange.equals(MOVIES)
                         || toChange.equals(UPGRADES)) {
                    return toChange;
                }
                return "error";

            case MOVIES:
                if (toChange.equals(HOMEPAGETWO) || toChange.equals(DETAILS)
                        || toChange.equals(MOVIES)) {
                    return toChange;
                }
                return "error";

            case DETAILS:
                if (toChange.equals(HOMEPAGETWO) || toChange.equals(MOVIES)
                        || toChange.equals(UPGRADES) || toChange.equals(DETAILS)) {
                    return toChange;
                }
                return "error";

            default: return "unreachable";
        }
    }

    /**
     * Method that changes the current page
     *
     * @param currentPage the page the user is currently on
     * @param toChange the page the user wants to go to
     * @param currentUser the user that wants to change the page
     * @param movies the list of movies
     * @param action the action the user wants to do
     * @param output the output node
     * @return the page the user is on after the change
     */
    public String execute(final String currentPage, final String toChange, final ArrayNode output,
                          final ArrayList<Movie> movies, final User currentUser,
                          final Action action) {
        String page = errorCheck(currentPage, toChange);
        OutputPrinter printer = OutputPrinter.getInstance();

        if (page.equals("error")) {
            output.add(printer.printError());
            return currentPage;
        }

        /* if the user accessed the movie page,
           he will see all the movies available for his country */
        if (page.equals(MOVIES)) {
            currentMovieList = new ArrayList<>(filterMovies(movies, currentUser));
            output.add(printer.printSuccess(currentUser, currentMovieList));
        }

        /* if the user accessed the details page,
           he will see the details of the movie he chose */
        if (page.equals(DETAILS)) {
            String movieTitle = action.getMovie();
            int error = 1;
            ArrayList<Movie> filteredMovieList = new ArrayList<>(currentMovieList);

            for (Movie movie : currentMovieList) {
                if (!movie.getName().equals(movieTitle)) {
                    filteredMovieList.remove(movie);
                } else {
                    error = 0;
                }
            }

            /* if the movie is not in the list, the user will see an error */
            if (error == 1) {
                currentMovieList = new ArrayList<>(filterMovies(movies, currentUser));
                output.add(printer.printError());
                return MOVIES;
            } else {
                currentMovieList = new ArrayList<>(filteredMovieList);
                output.add(printer.printSuccess(currentUser, filteredMovieList));
            }
        }

        return page;
    }

    public String goBack(final String toChange, final ArrayNode output,
                         final ArrayList<Movie> movies, final User currentUser) {
        OutputPrinter printer = OutputPrinter.getInstance();

        /* if the user went back to the movie page,
           he will see all the movies available for his country */
        if (toChange.equals(MOVIES)) {
            currentMovieList = new ArrayList<>(filterMovies(movies, currentUser));
            output.add(printer.printSuccess(currentUser, currentMovieList));
        }

        return toChange;
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

    // Method that goes back to the previous page
    public String goBack(final String currentPage) {
        return null;
    }
}
