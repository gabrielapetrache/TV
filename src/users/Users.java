package users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Movies;

import java.util.ArrayList;

public class Users {
    private Credentials credentials;
    private int tokensCount = 0;
    private int numFreePremiumMovies = 15;
    private ArrayList<Movies> purchasedMovies;
    private ArrayList<Movies> watchedMovies;
    private ArrayList<Movies> likedMovies;
    private ArrayList<Movies> ratedMovies;

    /**
     * Default constructor
     */
    public Users() {
    }

    public Users(final Credentials credentials) {
        this.credentials = credentials;
        this.purchasedMovies = new ArrayList<>();
        this.watchedMovies = new ArrayList<>();
        this.likedMovies = new ArrayList<>();
        this.ratedMovies = new ArrayList<>();
    }

    /**
     * Method that writes the user's data in a JSON format
     * @return the user's data in a JSON format
     */
    public ObjectNode printUser() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode user = mapper.createObjectNode();
        ArrayNode empty = mapper.createArrayNode();

        user.set("credentials", credentials.printCredentials());
        user.put("tokensCount", tokensCount);
        user.put("numFreePremiumMovies", numFreePremiumMovies);

        if (purchasedMovies == null) {
            user.set("purchasedMovies", empty);
        } else {
            ArrayNode list = mapper.createArrayNode();
            for (Movies movie : purchasedMovies) {
                ObjectNode node = movie.printMovie();
                list.add(node);
            }
            user.set("purchasedMovies", list);
        }

        if (watchedMovies == null) {
            user.set("watchedMovies", empty);
        } else {
            ArrayNode list = mapper.createArrayNode();
            for (Movies movie : watchedMovies) {
                ObjectNode node = movie.printMovie();
                list.add(node);
            }
            user.set("watchedMovies", list);
        }

        if (likedMovies == null) {
            user.set("likedMovies", empty);
        } else {
            ArrayNode list = mapper.createArrayNode();
            for (Movies movie : likedMovies) {
                ObjectNode node = movie.printMovie();
                list.add(node);
            }
            user.set("likedMovies", list);
        }

        if (ratedMovies == null) {
            user.set("ratedMovies", empty);
        } else {
            ArrayNode list = mapper.createArrayNode();
            for (Movies movie : ratedMovies) {
                ObjectNode node = movie.printMovie();
                list.add(node);
            }
            user.set("ratedMovies", list);
        }

        return user;
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
     * @param users
     */
    public void setCredentials(Credentials users) {
        this.credentials = users;
    }

    /**
     * getter for tokens
     * @return tokens
     */
    public int getTokensCount() {
        return tokensCount;
    }

    /**
     * setter for tokens
     * @param tokens
     */
    public void setTokensCount(int tokens) {
        this.tokensCount = tokens;
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
        return "Users{"
                +  "users="
                + credentials
                + ", tokensCount="
                + tokensCount
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
