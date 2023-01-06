package actions;

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
    User currentUser;
    ArrayList<Movie> movies;

    public Filter(User currentUser, ArrayList<Movie> movies) {
        this.currentUser = currentUser;
        this.movies = movies;
    }

    public void execute(Action currentAction, ArrayNode output, ArrayList<User> users) {
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
    public ArrayList<Movie> filterMovies(ArrayList<Movie> movies, User currentUser) {
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
}
