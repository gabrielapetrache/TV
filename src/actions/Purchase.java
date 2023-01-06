package actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Movie;
import printer.OutputPrinter;
import users.User;

import java.util.ArrayList;

import static pages.PageStrings.DETAILS;
import static platform.Platform.currentMovieList;
import static platform.Platform.currentPage;

public class Purchase implements Feature {
    User currentUser;
    ArrayList<User> users;
    ArrayList<Movie> movies;

    public Purchase(User currentUser, ArrayList<User> users, ArrayList<Movie> movies) {
        this.currentUser = currentUser;
        this.users = users;
        this.movies = movies;
    }

    public void execute(Action currentAction, ArrayNode output, ArrayList<User> users) {
        OutputPrinter printer = OutputPrinter.getInstance();
        if (currentPage.equals(DETAILS)) {
            int error;
            if (currentMovieList.isEmpty()) {
                output.add(printer.printError());
                return;
            }
            Movie movie = currentMovieList.get(0);

            error = currentUser.buyMovie(movie);
            if (error == -1) {
                output.add(printer.printError());
                return;
            }
            output.add(printer.printSuccess(currentUser, currentMovieList));

        } else {
            /* if the current page is not details, the error is printed */
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
