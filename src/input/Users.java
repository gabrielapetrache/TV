package input;

import java.util.ArrayList;

public class Users {
    private Credentials credentials;
    private int tokens;
    private int numFreePremiumMovies;
    private ArrayList<Movies> purchasedMovies;
    private ArrayList<Movies> watchedMovies;
    private ArrayList<Movies> likedMovies;
    private ArrayList<Movies> ratedMovies;

    public Users() {
    }

    /**
     * getter for users
     * @return users
     */
    public Credentials getCredentials() {
        return credentials;
    }

    /**
     * setter for users
     * @param users
     */
    public void setCredentials(Credentials users) {
        this.credentials = users;
    }

    /**
     * getter for tokens
     * @return tokens
     */
    public int getTokens() {
        return tokens;
    }

    /**
     * setter for tokens
     * @param tokens
     */
    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    /**
     * getter for numFreePremiumMovies
     * @return numFreePremiumMovies
     */
    public int getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    /**
     * setter for numFreePremiumMovies
     * @param numFreePremiumMovies
     */
    public void setNumFreePremiumMovies(int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    /**
     * getter for purchasedMovies
     * @return purchasedMovies
     */
    public ArrayList<Movies> getPurchasedMovies() {
        return purchasedMovies;
    }

    /**
     * setter for purchasedMovies
     * @param purchasedMovies
     */
    public void setPurchasedMovies(ArrayList<Movies> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    /**
     * getter for watchedMovies
     * @return watchedMovies
     */
    public ArrayList<Movies> getWatchedMovies() {
        return watchedMovies;
    }

    /**
     * setter for watchedMovies
     * @param watchedMovies
     */
    public void setWatchedMovies(ArrayList<Movies> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    /**
     * getter for likedMovies
     * @return likedMovies
     */
    public ArrayList<Movies> getLikedMovies() {
        return likedMovies;
    }

    /**
     * setter for likedMovies
     * @param likedMovies
     */
    public void setLikedMovies(ArrayList<Movies> likedMovies) {
        this.likedMovies = likedMovies;
    }

    /**
     * getter for ratedMovies
     * @return ratedMovies
     */
    public ArrayList<Movies> getRatedMovies() {
        return ratedMovies;
    }

    /**
     * setter for ratedMovies
     * @param ratedMovies
     */
    public void setRatedMovies(ArrayList<Movies> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    @Override
    public String toString() {
        return "UsersInput{"
                +  "users="
                + credentials
                + ", tokens="
                + tokens
                + ", numFreePremiumMovies="
                + numFreePremiumMovies
                + ", purchasedMovies="
                + purchasedMovies
                + ", watchedMovies="
                + watchedMovies
                + ", likedMovies="
                + likedMovies
                + ", ratedMovies="
                + ratedMovies
                + '}';
    }
}
