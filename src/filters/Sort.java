package filters;

import input.Movie;

import java.util.ArrayList;
import java.util.Comparator;

public class Sort implements FilterStrategy {
    private String rating;
    private String duration;

    /**
     * Default constructor
     */
    public Sort() {
    }

    /**
     * Method for sorting movies by rating and duration
     * @param list current list of movies
     */
    @Override
    public void execute(final ArrayList<Movie> list) {

        if (rating != null) {
            if (rating.equals("increasing")) {
                list.sort(Comparator.comparing(Movie::getRating));
            } else {
                list.sort(Comparator.comparing(Movie::getRating).reversed());
            }
        }
        if (duration != null) {
            if (duration.equals("increasing")) {
                list.sort(Comparator.comparing(Movie::getDuration));
            } else {
                list.sort(Comparator.comparing(Movie::getDuration).reversed());
            }
        }
    }

    /**
     * getter for rating
     * @return rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * setter for rating
     * @param rating rating
     */
    public void setRating(final String rating) {
        this.rating = rating;
    }

    /**
     * getter for duration
     * @return duration of the movie
     */
    public String getDuration() {
        return duration;
    }

    /**
     * setter for duration
     * @param duration of the movie
     */
    public void setDuration(final String duration) {
        this.duration = duration;
    }
}
