package printer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Movie;
import users.User;

import java.util.ArrayList;

public class OutputPrinter {
    private ArrayNode output;
    private User currentUser;
    private ArrayList<Movie> currentMovieList;
    private ObjectMapper mapper = new ObjectMapper();

    public OutputPrinter(ArrayNode output, User curr, ArrayList<Movie> list) {
        this.output = output;
        this.currentMovieList = list;
        this.currentUser = curr;
    }

    public ObjectNode printError() {

        ObjectNode node = mapper.createObjectNode();
        ArrayNode list = mapper.createArrayNode();
        String empty = null;

        node.put("error", "Error");
        node.set("currentMoviesList", list);
        node.put("currentUser", empty);

        return node;
    }

    public ObjectNode printSuccess(User curr, ArrayList<Movie> currentMovieList) {
        currentUser = curr;
        ObjectNode node = mapper.createObjectNode();
        String empty = null;

        node.put("error", empty);
        node.set("currentMoviesList", printMovieList(currentMovieList));
        if (currentUser != null) {
            node.set("currentUser", currentUser.printUser());
        } else {
            node.put("currentUser", empty);
        }
        return node;
    }

    public ArrayNode printMovieList(ArrayList<Movie> currentMovieList) {
        ArrayNode list = mapper.createArrayNode();
        if (currentMovieList != null) {
            for (Movie movie : currentMovieList) {
                ObjectNode node = movie.printMovie();
                list.add(node);
            }
        }
        return list;
    }




}
