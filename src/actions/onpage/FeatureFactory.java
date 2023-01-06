package actions.onpage;

import input.Movie;
import users.User;

import java.util.ArrayList;

import static pages.PageStrings.*;
import static pages.PageStrings.RATE;

public class FeatureFactory {
    User currentUser;
    ArrayList<User> users;
    ArrayList<Movie> movies;

    public FeatureFactory(User currentUser, ArrayList<User> users, ArrayList<Movie> movies) {
        this.currentUser = currentUser;
        this.users = users;
        this.movies = movies;
    }

    public Feature getFeature(String feature) {
        switch (feature) {
            case LOGIN:
                return new Login(currentUser, users, movies);
            case REGISTER:
                return new Register(currentUser, users, movies);
            case SEARCH:
                return new Search(currentUser, users, movies);
            case FILTER:
                return new Filter(currentUser, users, movies);
            case BUYTOKEN:
                return new BuyToken(currentUser, users, movies);
            case PREMIUM:
                return new BuyPremium(currentUser, users, movies);
            case PURCHASE:
                return new Purchase(currentUser, users, movies);
            case WATCH:
                return new Watch(currentUser, users, movies);
            case LIKE:
                return new Like(currentUser, users, movies);
            case RATE:
                return new Rate(currentUser, users, movies);
            default:
                return null;
        }
    }
}
