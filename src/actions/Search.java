package actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Movie;
import printer.OutputPrinter;
import users.User;

import java.util.ArrayList;

import static pages.PageStrings.MOVIES;
import static platform.Platform.currentMovieList;
import static platform.Platform.currentPage;

public class Search implements Feature {
    User currentUser;
    ArrayList<User> users;
    ArrayList<Movie> movies;

    public Search(User currentUser, ArrayList<User> users, ArrayList<Movie> movies) {
        this.currentUser = currentUser;
        this.users = users;
        this.movies = movies;
    }

    public void execute(Action currentAction, ArrayNode output, ArrayList<User> users) {
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

    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
