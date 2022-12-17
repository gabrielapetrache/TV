package input;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.Comparator;

public class Movie implements Comparable<Movie> {
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    private int numLikes;
    private double rating;
    private int numRatings;
    private ArrayList<Double> ratings = new ArrayList<>();

    public Movie() {
    }

    public Movie(final String name, final int year, final int duration,
                 final ArrayList<String> genres, final ArrayList<String> actors,
                 final ArrayList<String> countriesBanned, final int numLikes,
                 final double rating, final int numRatings, final ArrayList<Double> ratings) {
        this.name = name;
        this.year = year;
        this.duration = duration;
        this.genres = genres;
        this.actors = actors;
        this.countriesBanned = countriesBanned;
        this.numLikes = numLikes;
        this.rating = rating;
        this.numRatings = numRatings;
        this.ratings = ratings;
    }

    /**
     * Method to calculate the average rating of a movie
     * @param rating to add
     */
    public void rateMovie(final double rating) {
        numRatings++;
        ratings.add(rating);
        this.rating = 0;
        for (Double rate : ratings) {
            this.rating += rate;
        }
        this.rating /= numRatings;
    }

    @Override
    public int compareTo(Movie movie) {
        return Comparator.comparing(Movie::getDuration)
                .thenComparing(Movie::getRating)
                .compare(this, movie);
    }

    /**
     * Helper method for printing a movie's details at output
     * @return object node
     */
    public ObjectNode printMovie() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode movie = mapper.createObjectNode();

        movie.put("name", name);
        movie.put("year", year);
        movie.put("duration", duration);

        ArrayNode genresArray = mapper.createArrayNode();
        for (String genre : genres) {
            genresArray.add(genre);
        }
        movie.set("genres", genresArray);

        ArrayNode actorsArray = mapper.createArrayNode();
        for (String actor : actors) {
            actorsArray.add(actor);
        }
        movie.set("actors", actorsArray);

        ArrayNode countriesBannedArray = mapper.createArrayNode();
        for (String country : countriesBanned) {
            countriesBannedArray.add(country);
        }
        movie.set("countriesBanned", countriesBannedArray);

        movie.put("numLikes", numLikes);
        movie.put("rating", rating);
        movie.put("numRatings", numRatings);

        return movie;
    }

    /**
     * getter for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for year
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * setter for year
     * @param year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * getter for duration
     * @return duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * setter for duration
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * getter for genres
     * @return genres
     */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * setter for genres
     * @param genres
     */
    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    /**
     * getter for actors
     * @return actors
     */
    public ArrayList<String> getActors() {
        return actors;
    }

    /**
     * setter for actors
     * @param actors
     */
    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }

    /**
     * getter for countriesBanned
     * @return countriesBanned
     */
    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    /**
     * setter for countriesBanned
     * @param countriesBanned
     */
    public void setCountriesBanned(ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    /**
     * getter for numLikes
     * @return numLikes
     */
    public int getNumLikes() {
        return numLikes;
    }

    /**
     * setter for numLikes
     * @param numLikes
     */
    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    /**
     * getter for rating
     * @return rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * setter for rating
     * @param rating
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * getter for numRatings
     * @return numRatings
     */
    public int getNumRatings() {
        return numRatings;
    }

    /**
     * setter for numRatings
     * @param numRatings
     */
    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

    /**
     * getter for ratings
     * @return ratings
     */
    public ArrayList<Double> getRatings() {
        return ratings;
    }

    /**
     * setter for ratings
     * @param ratings
     */
    public void setRatings(ArrayList<Double> ratings) {
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        return "Movies{"
                + "name='"
                + name
                + '\''
                + ", year="
                + year
                + ", duration="
                + duration
                + ", genres="
                + genres
                + ", actors="
                + actors
                + ", countriesBanned="
                + countriesBanned
                + '}';
    }
}
