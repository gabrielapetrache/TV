package platform;

import actions.Action;
import actions.ChangePage;
import actions.onpage.Feature;
import actions.onpage.FeatureFactory;
import com.fasterxml.jackson.databind.node.ArrayNode;
import input.Input;
import input.Movie;
import printer.OutputPrinter;
import users.User;

import java.util.ArrayList;

import static pages.PageStrings.HOMEPAGEONE;
import static pages.PageStrings.CHANGE;
import static pages.PageStrings.ON;
import static pages.PageStrings.LOGOUT;

public class Platform {
    private ArrayList<User> users;
    private ArrayList<Movie> movies;
    private ArrayList<Action> actions;
    public static String currentPage;
    private User currentUser;
    public static ArrayList<Movie> currentMovieList;
    private final OutputPrinter printer = OutputPrinter.getInstance();

    /**
     * Constructor for Platform
     */
    public Platform() {
    }

    /**
     * Method that initializes the platform
     */
    public void start(final Input input, final ArrayNode output) {
        this.users = input.getUsers();
        this.actions = input.getActions();
        this.movies = input.getMovies();
        currentPage = HOMEPAGEONE;
        stream(output);
    }

    /**
     * Method that executes the actions
     */
    public void stream(final ArrayNode output) {
        currentMovieList = new ArrayList<>();

        for (Action currentAction : actions) {
            String type = currentAction.getType();
            ChangePage changePage = new ChangePage();

            switch (type) {
                case CHANGE -> {
                    String newPage = currentAction.getPage();
                     /* logout is a separate case from the others,
                         because it also resets the current user */
                    if (newPage.equals(LOGOUT)) {
                        logoutCase(output);
                    } else {
                        currentPage = changePage.execute(currentPage, newPage, output, movies,
                                currentUser, currentAction);
                    }
                }
                case ON -> {
                    String feature = currentAction.getFeature();
                    FeatureFactory featureFactory = new FeatureFactory(currentUser, users, movies);
                    Feature currentFeature = featureFactory.getFeature(feature);
                    currentFeature.execute(currentAction, output, users);
                    currentUser = currentFeature.getCurrentUser();
                    users = currentFeature.getUsers();
                }
                default -> {
                }
            }
        }
    }

    /**
     * This method logs out the current user
     * @param output output in case of error
     */
    private void logoutCase(final ArrayNode output) {
        if (currentUser == null) {
            /* if the user is not logged in, he cannot log out */
            output.add(printer.printError());
            return;
        }
        /* the user logged out */
        currentMovieList = new ArrayList<>();
        currentUser = null;
        currentPage = HOMEPAGEONE;
    }
}
