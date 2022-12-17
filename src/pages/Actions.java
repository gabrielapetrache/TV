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
     * @param type type
     */
    public void setType(final String type) {
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
     * @param page page
     */
    public void setPage(final String page) {
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
     * @param startsWith startsWith
     */
    public void setStartsWith(final String startsWith) {
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
     * @param credentials credentials
     */
    public void setCredentials(final Credentials credentials) {
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
     * @param feature feature
     */
    public void setFeature(final String feature) {
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
     * @param count count
     */
    public void setCount(final int count) {
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
     * @param movie movie
     */
    public void setMovie(final String movie) {
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
     * @param filters   filters
     */
    public void setFilters(final Filters filters) {
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
     * @param rate rate
     */
    public void setRate(final int rate) {
        this.rate = rate;
    }

    /**
     * print the actions
     * @return actions
     */
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
