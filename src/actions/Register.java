package actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import printer.OutputPrinter;
import users.Credentials;
import users.User;

import java.util.ArrayList;

import static pages.PageStrings.*;
import static platform.Platform.currentMovieList;
import static platform.Platform.currentPage;

public class Register implements Feature {
    User currentUser;
    ArrayList<User> users;

    public Register(User currentUser, ArrayList<User> users) {
        this.currentUser = currentUser;
        this.users = users;
    }

    public void execute(Action currentAction, ArrayNode output, ArrayList<User> users) {
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

    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
