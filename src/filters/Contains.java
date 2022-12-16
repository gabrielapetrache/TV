package filters;

import input.Movies;

import java.util.ArrayList;

public class Contains {
    private ArrayList<String> actors;
    private ArrayList<String> genre;

    /**
     * Default constructor
     */
    public Contains() {
    }

    /**
     * Method for filtering movies by actors and genre
     */
    public void execute(ArrayList<Movies> list) {
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
     * @param actors
     */
    public void setActors(ArrayList<String> actors) {
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
     * @param genres
     */
    public void setGenre(ArrayList<String> genres) {
        this.genre = genres;
    }
}
