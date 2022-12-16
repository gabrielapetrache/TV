package pages;

import filters.Filters;
import users.Credentials;

public class Actions {
    private String type;
    private String page;
    private Credentials credentials;
    private String feature;
    private int count;
    private int rate;
    private String movie;
    private String startsWith;
    private Filters filters;

    /**
     * Default constructor
     */
    public Actions() {
    }

    /**
     * getter for type
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * setter for type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter for page
     * @return page
     */
    public String getPage() {
        return page;
    }

    /**
     * setter for page
     * @param page
     */
    public void setPage(String page) {
        this.page = page;
    }
    /**
     * getter for startsWith
     * @return startsWith
     */
    public String getStartsWith() {
        return startsWith;
    }

    /**
     * setter for startsWith
     * @param startsWith
     */
    public void setStartsWith(String startsWith) {
        this.startsWith = startsWith;
    }

    /**
     * getter for credentials
     * @return credentials
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * setter for credentials
     * @param credentials
     */
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    /**
     * getter for feature
     * @return feature
     */
    public String getFeature() {
        return feature;
    }

    /**
     * setter for feature
     * @param feature
     */
    public void setFeature(String feature) {
        this.feature = feature;
    }

    /**
     * getter for count
     * @return count
     */
    public int getCount() {
        return count;
    }

    /**
     * setter for count
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * getter for movie
     * @return movie
     */
    public String getMovie() {
        return movie;
    }

    /**
     * setter for movie
     * @param movie
     */
    public void setMovie(String movie) {
        this.movie = movie;
    }

    /**
     * getter for filters
     * @return filters
     */
    public Filters getFilters() {
        return filters;
    }

    /**
     * setter for filters
     * @param filters
     */
    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    /**
     * getter for rate
     * @return rate
     */
    public int getRate() {
        return rate;
    }

    /**
     * setter for rate
     * @param rate
     */
    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Actions{"
                + "type="
                + type
                + ", page="
                + page
                + ", credentials="
                + credentials
                + ", feature="
                + feature
                + ", movie="
                + movie
                + ", filters="
                + filters
                + '}';
    }
}
