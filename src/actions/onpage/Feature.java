package actions.onpage;

import actions.Action;
import com.fasterxml.jackson.databind.node.ArrayNode;
import users.User;

import java.util.ArrayList;

public interface Feature {
    /**
     * Executes the action
     * @param currentAction the current action
     * @param output the output
     * @param users the list of users
     */
    void execute(Action currentAction, ArrayNode output, ArrayList<User> users);

    /**
     * Getter for the current user
     * @return the current user
     */
    ArrayList<User> getUsers();

    /**
     * Getter for the current user
     * @return the current user
     */
    User getCurrentUser();
}
