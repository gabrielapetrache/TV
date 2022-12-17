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
    public void execute(ArrayList<Movie> list) {

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
     * @param rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * getter for duration
     * @return duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * setter for duration
     * @param duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }
}
