package platform;

import com.fasterxml.jackson.databind.node.ArrayNode;
import filters.Filters;
import pages.Actions;
import input.Input;
import input.Movies;
import pages.ChangePage;
import pages.OnPage;
import printer.OutputPrinter;
import users.Credentials;
import users.Users;

import java.util.ArrayList;

import static pages.PageTypes.*;

/**
 * Class implemented using Singleton design pattern
 */
public class Platform {
    private static final Platform instance = new Platform();
    private ArrayList<Users> users;
    private ArrayList<Movies> movies;
    private ArrayList<Actions> actions;
    private String currentPage;
    private Users currentUser;
    private ArrayList<Movies> currentMovieList;
    private Platform() {
    }

    public void start(final Input input, final ArrayNode output) {
        this.users = input.getUsers();
        this.actions = input.getActions();
        this.movies = input.getMovies();
        currentPage = HOMEPAGEONE;
        stream(output);
    }

    public void stream(final ArrayNode output) {
        OutputPrinter printer = new OutputPrinter(output, currentUser, currentMovieList);
        currentMovieList = new ArrayList<>();

        for (Actions currentAction : actions) {
            String type = currentAction.getType();
            switch (type) {
                case CHANGE:
                    ChangePage changePage = new ChangePage();
                    String newPage = currentAction.getPage();
                     /* logout is a separate case from the others,
                         because it also resets the current user */
                    if (!newPage.equals(LOGOUT)) {
                        String check = changePage.execute(currentPage, newPage);

                        if (check.equals("error")) {
                            output.add(printer.printError(currentUser, currentMovieList));
                        } else if (check.equals("login") && currentUser != null) {
                            /* if the user is already logged in, he can't log in again,
                            but he can log out */
                            currentUser = null;
                            output.add(printer.printError(currentUser, currentMovieList));
                        } else {
                            /* the page was successfully changed */
                            currentPage = newPage;
                            /* if the user accessed the movie page,
                            he will see all the movies available for his country */
                            if (currentPage.equals(MOVIES)) {
                                for (Movies movie : movies) {
                                    for (String banned : movie.getCountriesBanned()) {
                                        if (banned.equals(currentUser.getCredentials().getCountry())) {
                                            break;
                                        } else {
                                            currentMovieList.add(movie);
                                        }
                                    }
                                }
                                output.add(printer.printSuccess(currentUser, currentMovieList));
                            }
                            if (currentPage.equals(DETAILS)) {
                                String movieTitle = currentAction.getMovie();
                                int error = 1;
                                for (Movies movie : currentMovieList) {
                                    if (movie.getName().equals(movieTitle)) {
                                        error = 0;
                                        break;
                                    }
                                }
                                if (error == 1) {
                                    output.add(printer.printError(null, null));
                                } else {
                                    output.add(printer.printSuccess(currentUser, currentMovieList));
                                }
                            }
                        }
                    } else {
                        /* the user logged out */
                        currentUser = null;
                        currentPage = HOMEPAGEONE;
                    }
                    break;
                case ON:
                    String feature = currentAction.getFeature();
                    switch (feature) {
                        case LOGIN:
                            if (currentPage.equals(LOGIN)) {
                                String name = currentAction.getCredentials().getName();
                                String pass = currentAction.getCredentials().getPassword();

                                /* check if the user exists */
                                for (Users user : users) {
                                    if (user.getCredentials().getName().equals(name)
                                            && user.getCredentials().getPassword().equals(pass)) {
                                        currentUser = user;
                                        currentPage = HOMEPAGETWO;
                                        output.add(printer.printSuccess(currentUser, currentMovieList));
                                        break;
                                    }
                                }

                                /* if the user is not found, the error is printed */
                                if (currentUser == null) {
                                    currentPage = HOMEPAGEONE;
                                    output.add(printer.printError(currentUser, currentMovieList));
                                    break;
                                }
                            } else {
                                /* if the current page is not login, the error is printed */
                                output.add(printer.printError(null, null));
                            }
                            break;

                        case REGISTER:
                            if (currentPage.equals(REGISTER)) {
                                String name = currentAction.getCredentials().getName();
                                int error = 0;
                                /* check if the user already exists */
                                for (Users user : users) {
                                    if (user.getCredentials().getName().equals(name)) {
                                        output.add(printer.printError(currentUser, currentMovieList));
                                        error = 1;
                                        break;
                                    }
                                }
                                if (error == 1) {
                                    /* if the user already exists, the error is printed */
                                    output.add(printer.printError(currentUser, currentMovieList));
                                    currentPage = HOMEPAGEONE;
                                    break;
                                } else {
                                    /* if the user does not exist, it's created */
                                    Credentials credentials = currentAction.getCredentials();
                                    Users newUser = new Users(credentials);
                                    users.add(newUser);
                                    currentUser = newUser;
                                    currentPage = HOMEPAGETWO;
                                    output.add(printer.printSuccess(currentUser, currentMovieList));
                                }
                            } else {
                                /* if the current page is not register, the error is printed */
                                output.add(printer.printError(currentUser, currentMovieList));
                            }
                            break;

                        case SEARCH:
                            if (currentPage.equals(MOVIES)) {
                                String startsWith = currentAction.getStartsWith();
                                currentMovieList = new ArrayList<>();
                                for (Movies movie : movies) {
                                    if (movie.getName().startsWith(startsWith)) {
                                        currentMovieList.add(movie);
                                    }
                                }
                                output.add(printer.printSuccess(currentUser, currentMovieList));
                            } else {
                                /* if the current page is not movies, the error is printed */
                                output.add(printer.printError(currentUser, currentMovieList));
                            }
                            break;
                        case FILTER:
                            if (currentPage.equals(MOVIES)) {
                                Filters filter = currentAction.getFilters();
                                if (filter.getSort() != null) {
                                    filter.getSort().execute(currentMovieList);
                                    output.add(printer.printSuccess(currentUser, currentMovieList));
                                }
                                if (filter.getContains() != null) {
                                    filter.getContains().execute(currentMovieList);
                                    output.add(printer.printSuccess(currentUser, currentMovieList));
                                }
                            } else {
                                /* if the current page is not movies, the error is printed */
                                output.add(printer.printError(null, null));
                            }
                            break;
                        default:
                    }
                    break;
                default:
            }
        }
    }

    /**
     * getter for instance
     * @return this instance
     */
    public static Platform getInstance() {
        return instance;
    }

}
