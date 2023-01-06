package actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Movie;
import printer.OutputPrinter;
import users.User;

import java.util.ArrayList;

import static pages.PageStrings.DETAILS;
import static platform.Platform.currentMovieList;
import static platform.Platform.currentPage;

public class Watch implements Feature {
    User currentUser;
    ArrayList<User> users;
    ArrayList<Movie> movies;

    public Watch(User currentUser, ArrayList<User> users, ArrayList<Movie> movies) {
        this.currentUser = currentUser;
        this.users = users;
        this.movies = movies;
    }

    public void execute(Action currentAction, ArrayNode output, ArrayList<User> users) {
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

    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
