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
    public ArrayList<Movie> filter(ArrayList<Movie> list) {
        if (sort != null) {
            sort.execute(list);
        }
        if (contains != null) {
            contains.execute(list);
        }
        return list;
    }

    /**
     * getter for sort
     * @return sort
     */
    public Sort getSort() {
        return sort;
    }

    /**
     * setter for sort
     * @param sort
     */
    public void setSort(Sort sort) {
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
     * @param contains
     */
    public void setContains(Contains contains) {
        this.contains = contains;
    }
}
