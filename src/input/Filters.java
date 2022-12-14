package input;

import java.util.ArrayList;

public class Filters {
    private Sort sort;
    private Contains contains;

    public Filters() {
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
