package input;

import java.util.ArrayList;

public class Contains {
    private ArrayList<String> actors;
    private ArrayList<String> genre;


    public Contains() {
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
