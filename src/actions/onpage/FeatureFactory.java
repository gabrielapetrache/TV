package actions.onpage;

import input.Movie;
import users.User;

import java.util.ArrayList;

import static pages.PageStrings.LOGIN;
import static pages.PageStrings.REGISTER;
import static pages.PageStrings.SEARCH;
import static pages.PageStrings.RATE;
import static pages.PageStrings.FILTER;
import static pages.PageStrings.BUYTOKEN;
import static pages.PageStrings.PREMIUM;
import static pages.PageStrings.PURCHASE;
import static pages.PageStrings.WATCH;
import static pages.PageStrings.LIKE;

public class FeatureFactory {
    private final User currentUser;
    private final ArrayList<User> users;
    private final ArrayList<Movie> movies;

    /**
     * Constructor for FeatureFactory
     */
    public FeatureFactory(final User currentUser, final ArrayList<User> users,
                          final ArrayList<Movie> movies) {
        this.currentUser = currentUser;
        this.users = users;
        this.movies = movies;
    }

    /**
     * Method that creates a feature
     * @param feature the feature to be created
     * @return the feature
     */
    public Feature getFeature(final String feature) {
        return switch (feature) {
            case LOGIN -> new Login(currentUser, users, movies);
            case REGISTER -> new Register(currentUser, users, movies);
            case SEARCH -> new Search(currentUser, users, movies);
            case FILTER -> new Filter(currentUser, users, movies);
            case BUYTOKEN -> new BuyToken(currentUser, users, movies);
            case PREMIUM -> new BuyPremium(currentUser, users, movies);
            case PURCHASE -> new Purchase(currentUser, users, movies);
            case WATCH -> new Watch(currentUser, users, movies);
            case LIKE -> new Like(currentUser, users, movies);
            case RATE -> new Rate(currentUser, users, movies);
            default -> null;
        };
    }
}
