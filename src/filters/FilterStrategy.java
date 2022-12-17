package filters;

import input.Movie;

import java.util.ArrayList;

public interface FilterStrategy {
    void execute(ArrayList<Movie> list);
}
