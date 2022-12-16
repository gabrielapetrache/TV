package printer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Movies;
import users.Users;

import java.util.ArrayList;

public class OutputPrinter {
    private ArrayNode output;
    private Users currentUser;
    private ArrayList<Movies> currentMovieList;
    private ObjectMapper mapper = new ObjectMapper();

    public OutputPrinter(ArrayNode output, Users curr, ArrayList<Movies> list) {
        this.output = output;
        this.currentMovieList = list;
        this.currentUser = curr;
    }

    public ObjectNode printError(Users curr, ArrayList<Movies> currentMovieList) {

        ObjectNode node = mapper.createObjectNode();
        String empty = null;
        this.currentMovieList = currentMovieList;

        node.put("error", "Error");
        node.set("currentMoviesList", printMovieList());
        if (curr != null) {
            node.set("currentUser", curr.printUser());
        } else {
            node.put("currentUser", empty);
        }
        return node;
    }

    public ObjectNode printSuccess(Users curr, ArrayList<Movies> currentMovieList) {
        currentUser = curr;
        this.currentMovieList = currentMovieList;
        ObjectNode node = mapper.createObjectNode();
        String empty = null;

        node.put("error", empty);
        node.set("currentMoviesList", printMovieList());
        if (currentUser != null) {
            node.set("currentUser", currentUser.printUser());
        } else {
            node.put("currentUser", empty);
        }
        return node;
    }

    public ArrayNode printMovieList() {
        ArrayNode list = mapper.createArrayNode();
        if (currentMovieList != null) {
            for (Movies movie : currentMovieList) {
                ObjectNode node = movie.printMovie();
                list.add(node);
            }
        }
        return list;
    }




}
