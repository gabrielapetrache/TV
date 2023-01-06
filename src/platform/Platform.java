package platform;

import com.fasterxml.jackson.databind.node.ArrayNode;
import filters.Filters;
import actions.Action;
import input.Input;
import input.Movie;
import actions.ChangePage;
import printer.OutputPrinter;
import users.Credentials;
import users.User;

import java.util.ArrayList;

import static pages.PageStrings.*;

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
                    if (!newPage.equals(LOGOUT)) {
                        currentPage = changePage.execute(currentPage, newPage, output, movies,
                                currentUser, currentAction);
                    } else {
                        logoutCase(output);
                    }
                }
                case ON -> {
                    String feature = currentAction.getFeature();
                    switch (feature) {
                        case LOGIN:
                            if (currentPage.equals(LOGIN)) {
                                String name = currentAction.getCredentials().getName();
                                String pass = currentAction.getCredentials().getPassword();
                                /* check if the user exists */
                                for (User user : users) {
                                    if (user.getCredentials().getName().equals(name)
                                            && user.getCredentials().getPassword().equals(pass)) {
                                        currentUser = user;
                                        currentPage = HOMEPAGETWO;
                                        currentMovieList = new ArrayList<>();
                                        output.add(printer.printSuccess(currentUser,
                                                currentMovieList));
                                        break;
                                    }
                                }
                                /* if the user is not found, the error is printed */
                                if (currentUser == null) {
                                    currentPage = HOMEPAGEONE;
                                    output.add(printer.printError());
                                    break;
                                }
                            } else {
                                /* if the current page is not login, the error is printed */
                                output.add(printer.printError());
                            }
                            break;

                        case REGISTER:
                            if (currentPage.equals(REGISTER)) {
                                String name = currentAction.getCredentials().getName();
                                int error = 0;
                                /* check if the user already exists */
                                for (User user : users) {
                                    if (user.getCredentials().getName().equals(name)) {
                                        output.add(printer.printError());
                                        currentPage = HOMEPAGEONE;
                                        error = 1;
                                        break;
                                    }
                                }
                                if (error == 0) {
                                    /* if the user does not exist, it's created */
                                    Credentials credentials = currentAction.getCredentials();
                                    User newUser = new User(credentials);
                                    users.add(newUser);
                                    currentUser = newUser;
                                    currentPage = HOMEPAGETWO;
                                    output.add(printer.printSuccess(currentUser, currentMovieList));
                                }
                            } else {
                                /* if the current page is not register, the error is printed */
                                output.add(printer.printError());
                            }
                            break;

                        case SEARCH:
                            if (currentPage.equals(MOVIES)) {
                                String startsWith = currentAction.getStartsWith();
                                ArrayList<Movie> copyMovieList = new ArrayList<>(currentMovieList);

                                for (Movie movie : copyMovieList) {
                                    for (String ban : movie.getCountriesBanned()) {
                                        if (ban.equals(currentUser.getCredentials().getCountry())
                                                || !movie.getName().startsWith(startsWith)) {
                                            currentMovieList.remove(movie);
                                        }
                                    }
                                }
                                output.add(printer.printSuccess(currentUser, currentMovieList));
                            } else {
                                /* if the current page is not movies, the error is printed */
                                output.add(printer.printError());
                            }
                            break;
                        case FILTER:
                            if (currentPage.equals(MOVIES)) {
                                Filters filter = currentAction.getFilters();
                                if (currentMovieList.isEmpty()) {
                                    addMoviesAvailable();
                                }
                                currentMovieList = filter.filter(currentMovieList);
                                output.add(printer.printSuccess(currentUser, currentMovieList));
                            } else {
                                /* if the current page is not movies, the error is printed */
                                output.add(printer.printError());
                            }
                            break;
                        case BUYTOKEN:
                            if (currentPage.equals(UPGRADES)) {
                                int error = currentUser.buyTokens(currentAction.getCount());
                                if (error == -1) {
                                    output.add(printer.printError());
                                    break;
                                }
                            } else {
                                /* if the current page is not upgrades, the error is printed */
                                output.add(printer.printError());
                            }
                            break;
                        case PREMIUM:
                            if (currentPage.equals(UPGRADES)) {
                                int error = currentUser.buyPremium();
                                if (error == -1) {
                                    output.add(printer.printError());
                                    break;
                                }
                            } else {
                                /* if the current page is not upgrades, the error is printed */
                                output.add(printer.printError());
                            }
                            break;
                        case PURCHASE:
                            if (currentPage.equals(DETAILS)) {
                                int error;
                                if (currentMovieList.isEmpty()) {
                                    output.add(printer.printError());
                                    break;
                                }
                                Movie movie = currentMovieList.get(0);

                                error = currentUser.buyMovie(movie);
                                if (error == -1) {
                                    output.add(printer.printError());
                                    break;
                                }
                                output.add(printer.printSuccess(currentUser, currentMovieList));
                                break;

                            } else {
                                /* if the current page is not details, the error is printed */
                                output.add(printer.printError());
                            }
                            break;
                        case WATCH:
                            if (currentPage.equals(DETAILS)) {
                                if (currentMovieList.isEmpty()) {
                                    output.add(printer.printError());
                                    break;
                                }
                                Movie toWatch = currentMovieList.get(0);

                                int error = currentUser.watchMovie(toWatch);
                                if (error == -1) {
                                    output.add(printer.printError());
                                    break;
                                }
                                if (error == 0) {
                                    output.add(printer.printSuccess(currentUser,
                                            currentMovieList));
                                    break;
                                }
                            } else {
                                /* if the current page is not upgrades, the error is printed */
                                output.add(printer.printError());
                                break;
                            }
                        case LIKE:
                            if (currentPage.equals(DETAILS)) {
                                if (currentMovieList.isEmpty()) {
                                    output.add(printer.printError());
                                    break;
                                }
                                Movie toLike = currentMovieList.get(0);
                                int error = currentUser.likeMovie(toLike);
                                if (error == -1) {
                                    output.add(printer.printError());
                                    break;
                                }
                                output.add(printer.printSuccess(currentUser, currentMovieList));
                            } else {
                                /* if the current page is not upgrades, the error is printed */
                                output.add(printer.printError());
                            }
                            break;
                        case RATE:
                            if (currentPage.equals(DETAILS)) {
                                if (currentMovieList.isEmpty()) {
                                    output.add(printer.printError());
                                    break;
                                }
                                Movie toRate = currentMovieList.get(0);
                                int error = currentUser.rateMovie(toRate, currentAction.getRate());
                                if (error == -1) {
                                    output.add(printer.printError());
                                    break;
                                }
                                output.add(printer.printSuccess(currentUser, currentMovieList));

                            } else {
                                /* if the current page is not upgrades, the error is printed */
                                output.add(printer.printError());
                            }
                            break;
                        default:
                    }
                }
                default -> {
                }
            }
        }
    }

    /**
     * This method checks if the user is logged in, in case he wants to log out
     * @param output output in case of error
     */
    private void logoutCase(ArrayNode output) {
        if (currentUser == null) {
            output.add(printer.printError());
            return;
        }
        /* the user logged out */
        currentMovieList = new ArrayList<>();
        currentUser = null;
        currentPage = HOMEPAGEONE;
    }

    /**
     * Method that adds the movies available to the currentMovieList
     */
    public void addMoviesAvailable() {
        currentMovieList = new ArrayList<>();
        currentMovieList.addAll(movies);
        ArrayList<Movie> copyMovieList = new ArrayList<>(currentMovieList);

        for (Movie movie : copyMovieList) {
            for (String ban : movie.getCountriesBanned()) {
                if (ban.equals(currentUser.getCredentials().getCountry())) {
                    currentMovieList.remove(movie);
                }
            }
        }
    }
}
