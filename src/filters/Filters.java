package filters;

import input.Movie;

import java.util.ArrayList;

public class Filters {
    private Sort sort;
    private Contains contains;

    /**
     * Default constructor
     */
    public Filters() {
    }

    /**
     * Method for filtering movies by strategy
     * @param list current list of movies
     */
    public ArrayList<Movie> filter(final ArrayList<Movie> list) {
        if (sort != null) {
            sort.execute(list);
        }
        if (contains != null) {
            contains.execute(list);
        }
        return list;
    }


    /* getters and setters are mandatory for the JSON parser
     * even if they are not used */
    /**
     * getter for sort
     * @return sort
     */
    public Sort getSort() {
        return sort;
    }

    /**
     * setter for sort
     * @param sort sort
     */
    public void setSort(final Sort sort) {
        this.sort = sort;
    }

    /**
     * getter for contains
     * @return contains
     */
    public Contains getContains() {
        return contains;
    }

    /**
     * setter for contains
     * @param contains contains
     */
    public void setContains(final Contains contains) {
        this.contains = contains;
    }
}
