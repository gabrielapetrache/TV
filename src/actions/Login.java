package actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import printer.OutputPrinter;
import users.User;

import java.util.ArrayList;

import static pages.PageStrings.*;
import static platform.Platform.*;

public class Login implements Feature {

    User currentUser;

    public Login(User currentUser) {
        this.currentUser = currentUser;
    }

    public void execute(Action currentAction, ArrayNode output, ArrayList<User> users) {
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

    public User getCurrentUser() {
        return currentUser;
    }
}
