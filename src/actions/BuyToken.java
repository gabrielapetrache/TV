package actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import printer.OutputPrinter;
import users.User;

import java.util.ArrayList;

import static pages.PageStrings.UPGRADES;
import static platform.Platform.currentPage;

public class BuyToken implements Feature {
    User currentUser;

    public BuyToken(User currentUser) {
        this.currentUser = currentUser;
    }

    public void execute(Action currentAction, ArrayNode output, ArrayList<User> users) {
        OutputPrinter printer = OutputPrinter.getInstance();
        if (currentPage.equals(UPGRADES)) {
            int error = currentUser.buyTokens(currentAction.getCount());
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
}
