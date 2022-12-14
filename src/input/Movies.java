package input;

import java.util.ArrayList;

public class Movies {
    private String name;
    private int year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;

    public Movies() {
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
