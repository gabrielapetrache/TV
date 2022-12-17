package filters;

import input.Movie;

import java.util.ArrayList;

public interface FilterStrategy {
    /**
     * Method for filtering movies by strategy
     * @param list current list of movies
     */
    void execute(ArrayList<Movie> list);
}
