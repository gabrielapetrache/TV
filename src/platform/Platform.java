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

import static pages.PageStrings.*;

public class Platform {
    private ArrayList<User> users;
    private ArrayList<Movie> movies;
    private ArrayList<Action> actions;
    private User currentUser;
    public static String currentPage;
    private ArrayList<String> previousPages = new ArrayList<>();
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
                        String page = currentPage;
                        currentPage = changePage.execute(currentPage, newPage, output, movies,
                                currentUser, currentAction);
                        if (!currentPage.equals(page)) {
                            previousPages.add(page);
                        }
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
                case DATABASE -> {
                    String feature = currentAction.getFeature();
                    if (feature.equals(ADD)) {
                        Movie toAdd = currentAction.getAddedMovie();
                        int error = 0;
                        for (Movie movie : movies) {
                            if (movie.getName().equals(toAdd.getName())) {
                                error = 1;
                                break;
                            }
                        }
                        if (error == 0) {
                            movies.add(toAdd);
                            for (User user : users) {
                                user.addMovieNotification(toAdd);
                            }
                        } else {
                            output.add(printer.printError());
                        }

                    } else if (feature.equals(DELETE)) {
                        String toRemove = currentAction.getDeletedMovie();
                        int error = 0;
                        for (Movie movie : movies) {
                            if (movie.getName().equals(toRemove)) {
                                movies.remove(movie);
                                currentMovieList.remove(movie);
                                for (User user : users) {
                                    user.removeMovie(toRemove);
                                }
                                error = 1;
                                break;
                            }
                        }
                        if (error == 0) {
                            output.add(printer.printError());
                        }
                    }
                }
                case BACK -> {
                    if (currentPage.equals(HOMEPAGEONE) || currentPage.equals(HOMEPAGETWO)
                            || currentUser == null || previousPages.isEmpty()) {
                        output.add(printer.printError());
                    } else {
                        currentPage = changePage.goBack(
                                previousPages.get(previousPages.size() - 1), output, movies,
                                currentUser);
                        previousPages.remove(previousPages.size() - 1);
                    }
                }
                default -> { }
            }
        }
        if (currentUser != null) {
            currentUser.addRecommendation(movies, output);
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
        previousPages = new ArrayList<>();
    }
}
