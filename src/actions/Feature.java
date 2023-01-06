package actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import users.User;

import java.util.ArrayList;

public interface Feature {
    void execute(Action currentAction, ArrayNode output, ArrayList<User> users);
}
