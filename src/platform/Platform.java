package platform;

import actions.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import filters.Filters;
import input.Input;
import input.Movie;
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
                    if (newPage.equals(LOGOUT)) {
                        logoutCase(output);
                    } else {
                        currentPage = changePage.execute(currentPage, newPage, output, movies,
                                currentUser, currentAction);
                    }
                }
                case ON -> {
                    String feature = currentAction.getFeature();
                    switch (feature) {
                        case LOGIN:
                            Login login = new Login(currentUser);
                            login.execute(currentAction, output, users);
                            currentUser = login.getCurrentUser();
                            break;
                        case REGISTER:
                            Register register = new Register(currentUser, users);
                            register.execute(currentAction, output, users);
                            currentUser = register.getCurrentUser();
                            users = register.getUsers();
                            break;
                        case SEARCH:
                            Search search = new Search(currentUser);
                            search.execute(currentAction, output, users);
                            break;
                        case FILTER:
                            Filter filter = new Filter(currentUser, movies);
                            filter.execute(currentAction, output, users);
                            break;
                        case BUYTOKEN:
                            BuyToken buyToken = new BuyToken(currentUser);
                            buyToken.execute(currentAction, output, users);
                            currentUser = buyToken.getCurrentUser();
                            break;
                        case PREMIUM:
                            BuyPremium buyPremium = new BuyPremium(currentUser);
                            buyPremium.execute(currentAction, output, users);
                            currentUser = buyPremium.getCurrentUser();
                            break;
                        case PURCHASE:
                            Purchase purchase = new Purchase(currentUser);
                            purchase.execute(currentAction, output, users);
                            currentUser = purchase.getCurrentUser();
                            break;
                        case WATCH:
                            Watch watch = new Watch(currentUser);
                            watch.execute(currentAction, output, users);
                            currentUser = watch.getCurrentUser();
                            break;
                        case LIKE:
                            Like like = new Like(currentUser);
                            like.execute(currentAction, output, users);
                            currentUser = like.getCurrentUser();
                            break;
                        case RATE:
                            Rate rate = new Rate(currentUser);
                            rate.execute(currentAction, output, users);
                            currentUser = rate.getCurrentUser();
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
}
