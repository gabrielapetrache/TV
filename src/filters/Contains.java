package filters;

import input.Movie;

import java.util.ArrayList;

public class Contains implements FilterStrategy {
    private ArrayList<String> actors;
    private ArrayList<String> genre;

    /**
     * Default constructor
     */
    public Contains() {
    }

    /**
     * Method for filtering movies by actors and genre
     * @param list current list of movies
     */
    @Override
    public void execute(final ArrayList<Movie> list) {
        if (actors != null) {
            for (String actor : actors) {
                list.removeIf(movie -> !movie.getActors().contains(actor));
            }
        }

        if (genre != null) {
            for (String genre : genre) {
                list.removeIf(movie -> !movie.getGenres().contains(genre));
            }
        }
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
     * @param actors actors
     */
    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    /**
     * getter for genres
     * @return genres
     */
    public ArrayList<String> getGenre() {
        return genre;
    }

    /**
     * setter for genres
     * @param genres genres
     */
    public void setGenre(final ArrayList<String> genres) {
        this.genre = genres;
    }
}
