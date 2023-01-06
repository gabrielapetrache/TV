package actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Movie;
import printer.OutputPrinter;
import users.User;

import java.util.ArrayList;

import static pages.PageStrings.UPGRADES;
import static platform.Platform.currentPage;

public class BuyPremium implements Feature {
    User currentUser;
    ArrayList<User> users;
    ArrayList<Movie> movies;

    public BuyPremium(User currentUser, ArrayList<User> users, ArrayList<Movie> movies) {
        this.currentUser = currentUser;
        this.users = users;
        this.movies = movies;
    }

    public void execute(Action currentAction, ArrayNode output, ArrayList<User> users) {
        OutputPrinter printer = OutputPrinter.getInstance();
        if (currentPage.equals(UPGRADES)) {
            int error = currentUser.buyPremium();
            if (error == -1) {
                output.add(printer.printError());
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
