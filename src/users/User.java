package users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Movie;

import java.util.ArrayList;

public class User {
    private Credentials credentials;
    private int tokensCount = 0;
    private int numFreePremiumMovies = 15;
    private ArrayList<Movie> purchasedMovies = new ArrayList<>();
    private ArrayList<Movie> watchedMovies = new ArrayList<>();
    private ArrayList<Movie> likedMovies = new ArrayList<>();
    private ArrayList<Movie> ratedMovies = new ArrayList<>();

    /**
     * Default constructor
     */
    public User() {
    }

    public User(final Credentials credentials) {
        this.credentials = credentials;
        this.purchasedMovies = new ArrayList<>();
        this.watchedMovies = new ArrayList<>();
        this.likedMovies = new ArrayList<>();
        this.ratedMovies = new ArrayList<>();
    }

    /**
     * Allows the user to buy tokens
     * @param tokens to buy
     * @return error status
     */
    public int buyTokens(final int tokens) {
        int balance = Integer.parseInt(credentials.getBalance());
        if (balance >= tokens) {
            balance -= tokens;
            credentials.setBalance(String.valueOf(balance));
            tokensCount += tokens;
            return 0;
        }
        return -1;
    }

    /**
     * Allows the user to buy a premium subscription
     * @return error status
     */
    public int buyPremium() {
        if (tokensCount >= 10) {
            tokensCount -= 10;
            numFreePremiumMovies = 15;
            credentials.setAccountType("premium");
            return 0;
        }
        return -1;
    }

    /**
     * Allows the user to buy a movie
     * @param movie to buy
     * @return error status
     */
    public int buyMovie(final Movie movie) {
        if (movie == null) {
            return -1;
        }
        if (credentials.getAccountType().equals("premium")) {
            if (numFreePremiumMovies > 0) {
                numFreePremiumMovies--;
                purchasedMovies.add(movie);
                return 0;
            }
        }
        if (tokensCount >= 2) {
            tokensCount -= 2;
            purchasedMovies.add(movie);
            return 0;
        }
        return -1;
    }

    /**
     * Allows the user to watch a movie
     * @param movie to watch
     * @return error status
     */
    public int watchMovie(final Movie movie) {
        if (movie == null) {
            return -1;
        }
        if (purchasedMovies.contains(movie)) {
            watchedMovies.add(movie);
            return 0;
        }
        return -1;
    }

    /**
     * Allows the user to like a movie
     * @param movie to like
     * @return error status
     */
    public int likeMovie(final Movie movie) {
        if (movie == null) {
            return -1;
        }
        if (purchasedMovies.contains(movie) && watchedMovies.contains(movie)) {
            likedMovies.add(movie);
            movie.setNumLikes(movie.getNumLikes() + 1);
            return 0;
        }
        return -1;
    }

    /**
     * Allows the user to rate a movie
     * @param movie to rate
     * @param rating to give
     * @return error status
     */
    public int rateMovie(final Movie movie, final double rating) {
        if (movie == null) {
            return -1;
        }
        if (rating < 0 || rating > 5) {
            return -1;
        }
        if (purchasedMovies.contains(movie) && watchedMovies.contains(movie)) {
            movie.rateMovie(rating);
            ratedMovies.add(movie);
            return 0;
        }
        return -1;
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
            for (Movie movie : purchasedMovies) {
                ObjectNode node = movie.printMovie();
                list.add(node);
            }
            user.set("purchasedMovies", list);
        }

        if (watchedMovies == null) {
            user.set("watchedMovies", empty);
        } else {
            ArrayNode list = mapper.createArrayNode();
            for (Movie movie : watchedMovies) {
                ObjectNode node = movie.printMovie();
                list.add(node);
            }
            user.set("watchedMovies", list);
        }

        if (likedMovies == null) {
            user.set("likedMovies", empty);
        } else {
            ArrayNode list = mapper.createArrayNode();
            for (Movie movie : likedMovies) {
                ObjectNode node = movie.printMovie();
                list.add(node);
            }
            user.set("likedMovies", list);
        }

        if (ratedMovies == null) {
            user.set("ratedMovies", empty);
        } else {
            ArrayNode list = mapper.createArrayNode();
            for (Movie movie : ratedMovies) {
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
     * @param users credentials
     */
    public void setCredentials(final Credentials users) {
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
     * @param tokens to set
     */
    public void setTokensCount(final int tokens) {
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
     * @param numFreePremiumMovies to set
     */
    public void setNumFreePremiumMovies(final int numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    /**
     * getter for purchasedMovies
     * @return purchasedMovies
     */
    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    /**
     * setter for purchasedMovies
     * @param purchasedMovies to set
     */
    public void setPurchasedMovies(final ArrayList<Movie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    /**
     * getter for watchedMovies
     * @return watchedMovies
     */
    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    /**
     * setter for watchedMovies
     * @param watchedMovies to set
     */
    public void setWatchedMovies(final ArrayList<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    /**
     * getter for likedMovies
     * @return likedMovies
     */
    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    /**
     * setter for likedMovies
     * @param likedMovies to set
     */
    public void setLikedMovies(final ArrayList<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    /**
     * getter for ratedMovies
     * @return ratedMovies
     */
    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    /**
     * setter for ratedMovies
     * @param ratedMovies to set
     */
    public void setRatedMovies(final ArrayList<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    /**
     * prints the user's data
     * @return the user's data
     */
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
