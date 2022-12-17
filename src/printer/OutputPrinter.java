package printer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Movie;
import users.User;

import java.util.ArrayList;

public class OutputPrinter {
    private final ArrayNode output;
    private User currentUser;
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Constructor for OutputPrinter
     */
    public OutputPrinter(final ArrayNode output, final User curr) {
        this.output = output;
        this.currentUser = curr;
    }

    /**
     * Method that prints the output in case of error
     */
    public ObjectNode printError() {

        ObjectNode node = mapper.createObjectNode();
        ArrayNode list = mapper.createArrayNode();
        String empty = null;

        node.put("error", "Error");
        node.set("currentMoviesList", list);
        node.put("currentUser", empty);

        return node;
    }

    /**
     * Method that prints the output in case of success
     */
    public ObjectNode printSuccess(final User curr, final ArrayList<Movie> currentMovieList) {
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

    /**
     * Method that prints the movie list
     */
    public ArrayNode printMovieList(final ArrayList<Movie> currentMovieList) {
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
