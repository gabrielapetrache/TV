package users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.Movie;
import printer.OutputPrinter;

import java.util.*;

import static pages.PageStrings.MAXRATING;
import static pages.PageStrings.PREMIUMMOVIES;
import static pages.PageStrings.PREMIUMCOST;
import static pages.PageStrings.MOVIECOST;

public class User {
    private Credentials credentials;
    private int tokensCount = 0;
    private int numFreePremiumMovies = PREMIUMMOVIES;
    private ArrayList<Movie> purchasedMovies = new ArrayList<>();
    private ArrayList<Movie> watchedMovies = new ArrayList<>();
    private ArrayList<Movie> likedMovies = new ArrayList<>();
    private ArrayList<Movie> ratedMovies = new ArrayList<>();
    private ArrayList<Notification> notifications = new ArrayList<>();
    private ArrayList<String> subscribedGenres = new ArrayList<>();

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
        this.notifications = new ArrayList<>();
        this.subscribedGenres = new ArrayList<>();
    }

    /**
     * Allows the user to buy tokens
     * @param tokens to buy
     * @return error status
     */
    public int buyTokens(final int tokens) {
        int balance = Integer.parseInt(credentials.getBalance());
        if (balance >= tokens) {
            credentials.setBalance(String.valueOf(balance - tokens));
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
        if (credentials.getAccountType().equals("standard")) {
            if (tokensCount >= PREMIUMCOST) {
                tokensCount -= PREMIUMCOST;
                numFreePremiumMovies = PREMIUMMOVIES;
                credentials.setAccountType("premium");
                return 0;
            }
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
        if (purchasedMovies.contains(movie)) {
            return -1;
        }

        if (credentials.getAccountType().equals("premium")) {
            if (numFreePremiumMovies > 0) {
                numFreePremiumMovies--;
                purchasedMovies.add(movie);
                return 0;
            }
        }
        if (tokensCount >= MOVIECOST) {
            tokensCount -= MOVIECOST;
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
            if (!watchedMovies.contains(movie)) {
                watchedMovies.add(movie);
            }
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
        if (likedMovies.contains(movie)) {
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
        if (rating < 0 || rating > MAXRATING) {
            return -1;
        }
        if (purchasedMovies.contains(movie) && watchedMovies.contains(movie)) {
            if (ratedMovies.contains(movie)) {
                int index = ratedMovies.indexOf(movie);
                movie.rerateMovie(rating, index);
                return 0;
            }
            movie.rateMovie(rating);
            ratedMovies.add(movie);
            return 0;
        }
        return -1;
    }

    /**
     * Allows the user to add a genre to their subscription
     * @param genre to add
     */
    public void addGenre(final String genre) {
        if (!subscribedGenres.contains(genre)) {
            subscribedGenres.add(genre);
        }
    }

    /**
     * Method that removes a movie from all the user's lists
     * @param movie to remove
     */
    public void removeMovie(final String movie) {
        Notification notification = new Notification(movie, "DELETE");
        for (Movie m : purchasedMovies) {
            if (m.getName().equals(movie)) {
                purchasedMovies.remove(m);
                notifications.add(notification);
                if (credentials.getAccountType().equals("premium")) {
                    numFreePremiumMovies++;
                } else {
                    tokensCount += MOVIECOST;
                }
            }
        }
        watchedMovies.removeIf(m -> m.getName().equals(movie));
        likedMovies.removeIf(m -> m.getName().equals(movie));
        ratedMovies.removeIf(m -> m.getName().equals(movie));
    }

    /**
     * Method that notifies the user of a new movie in their subscribed genres
     * @param movie to notify of
     */
    public void addMovieNotification(final Movie movie) {
        Notification notification = new Notification(movie.getName(), "ADD");

        for (String country : movie.getCountriesBanned()) {
            if (credentials.getCountry().equals(country)) {
                return;
            }
        }
        for (String genres : movie.getGenres()) {
            if (subscribedGenres.contains(genres)) {
                notifications.add(notification);
                return;
            }
        }
    }

    /**
     * Method that recommends a movie to the user at the end of the program
     * @param movies movie list on the platform
     * @param output array node
     */
    public void addRecommendation(final ArrayList<Movie> movies, final ArrayNode output) {
        if (credentials.getAccountType().equals("premium")) {
            String recommendation = "No recommendation";

            if (likedMovies.isEmpty()) {
                Notification notification = new Notification(recommendation, "Recommendation");
                notifications.add(notification);
                output.add(OutputPrinter.getInstance().printEnd(this));
                return;
            }

            // calculate most liked genres
            Map<String, Integer> genres = new HashMap<>();
            for (Movie movie : likedMovies) {
                for (String genre : movie.getGenres()) {
                    if (genres.containsKey(genre)) {
                        genres.put(genre, genres.get(genre) + 1);
                    } else {
                        genres.put(genre, 1);
                    }
                }
            }

            // sort genres by number of likes,descending, then by alphabetical order, ascending
            List<Map.Entry<String, Integer>> sortedGenres = new ArrayList<>(genres.entrySet());
            sortedGenres.sort((o1, o2) -> {
                if (o1.getValue().equals(o2.getValue())) {
                    return o1.getKey().compareTo(o2.getKey());
                }
                return o2.getValue().compareTo(o1.getValue());
            });

            ArrayList<Movie> moviesCopy = new ArrayList<>(movies);
            moviesCopy.removeAll(purchasedMovies);
            // sort movies by number of likes, descending
            moviesCopy.sort((o1, o2) -> o2.getNumLikes() - o1.getNumLikes());
            // get the first movie that has the most liked genre
            for (Movie movie : moviesCopy) {
                for (String genre : movie.getGenres()) {
                    if (sortedGenres.get(0).getKey().equals(genre)) {
                        recommendation = movie.getName();
                        Notification notification = new Notification(recommendation,
                                "Recommendation");
                        notifications.add(notification);
                        output.add(OutputPrinter.getInstance().printEnd(this));
                        return;
                    }
                }
            }
        }
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

        if (notifications == null) {
            user.set("notifications", empty);
        } else {
            ArrayNode list = mapper.createArrayNode();
            for (Notification notification : notifications) {
                ObjectNode node = notification.printNotification();
                list.add(node);
            }
            user.set("notifications", list);
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
     * getter for notifications
     * @return notifications
     */
    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    /**
     * setter for notifications
     * @param notifications to set
     */
    public void setNotifications(final ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    /**
     * Getter for the user's subscribed genre
     * @return the user's subscribed genre
     */
    public ArrayList<String> getSubscribedGenres() {
        return subscribedGenres;
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
