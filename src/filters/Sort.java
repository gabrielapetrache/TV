package filters;

import input.Movies;

import java.util.ArrayList;

public class Sort {
    private String rating;
    private String duration;

    /**
     * Default constructor
     */
    public Sort() {
    }

    /**
     * Method for sorting movies by rating and duration
     */
    public void execute(ArrayList<Movies> list) {
        if (rating != null) {
            if (rating.equals("increasing")) {
                list.sort((movie1, movie2) -> {
                    if (movie1.getRating() > movie2.getRating()) {
                        return 1;
                    } else if (movie1.getRating() < movie2.getRating()) {
                        return -1;
                    } else {
                        return 0;
                    }
                });
            } else if (rating.equals("decreasing")) {
                list.sort((movie1, movie2) -> {
                    if (movie1.getRating() > movie2.getRating()) {
                        return -1;
                    } else if (movie1.getRating() < movie2.getRating()) {
                        return 1;
                    } else {
                        return 0;
                    }
                });
            }
        }

        if (duration != null) {
            if (duration.equals("increasing")) {
                list.sort((movie1, movie2) -> {
                    if (movie1.getDuration() > movie2.getDuration()) {
                        return 1;
                    } else if (movie1.getDuration() < movie2.getDuration()) {
                        return -1;
                    } else {
                        return 0;
                    }
                });
            } else if (duration.equals("decreasing")) {
                list.sort((movie1, movie2) -> {
                    if (movie1.getDuration() > movie2.getDuration()) {
                        return -1;
                    } else if (movie1.getDuration() < movie2.getDuration()) {
                        return 1;
                    } else {
                        return 0;
                    }
                });
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
