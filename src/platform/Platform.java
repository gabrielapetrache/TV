package platform;

import com.fasterxml.jackson.databind.node.ArrayNode;
import filters.Filters;
import pages.Actions;
import input.Input;
import input.Movie;
import pages.ChangePage;
import printer.OutputPrinter;
import users.Credentials;
import users.User;

import java.util.ArrayList;
import java.util.HashSet;

import static pages.PageTypes.*;

public class Platform {
    private ArrayList<User> users;
    private ArrayList<Movie> movies;
    private ArrayList<Actions> actions;
    private String currentPage;
    private User currentUser;
    private ArrayList<Movie> currentMovieList;
    private ArrayList<Movie> filteredMovieList;

    public Platform() {
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
            ChangePage changePage = new ChangePage();

            switch (type) {
                case CHANGE:
                    String newPage = currentAction.getPage();
                     /* logout is a separate case from the others,
                         because it also resets the current user */
                    if (!newPage.equals(LOGOUT)) {
                        String check = changePage.execute(currentPage, newPage);

                        if (check.equals("error")) {
                            output.add(printer.printError());
                            break;
                        } else if (check.equals("login") && !currentPage.equals(HOMEPAGEONE)) {
                            output.add(printer.printError());
                            break;
                        }
                            /* the page was successfully changed */
                            currentPage = newPage;
                            /* if the user accessed the movie page,
                            he will see all the movies available for his country */
                            if (currentPage.equals(MOVIES)) {
                                currentMovieList = new ArrayList<>();
                                addMoviesAvailable();
                                output.add(printer.printSuccess(currentUser, currentMovieList));
                                break;
                            }
                            if (currentPage.equals(DETAILS)) {
                                String movieTitle = currentAction.getMovie();

                                int error = 1;
                                filteredMovieList = new ArrayList<>();
                                for (Movie movie : currentMovieList) {
                                    if (movie.getName().equals(movieTitle)) {
                                        error = 0;
                                        filteredMovieList.add(movie);
                                        break;
                                    }
                                }
                                if (error == 1) {
                                    output.add(printer.printError());
                                } else {
                                    output.add(printer.printSuccess(currentUser, filteredMovieList));
                                }
                            }
                    } else {
                        if (currentUser == null) {
                            output.add(printer.printError());
                            break;
                        }
                        /* the user logged out */
                        currentMovieList = new ArrayList<>();
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
                                for (User user : users) {
                                    if (user.getCredentials().getName().equals(name)
                                            && user.getCredentials().getPassword().equals(pass)) {
                                        currentUser = user;
                                        currentPage = HOMEPAGETWO;
                                        currentMovieList = new ArrayList<>();
                                        output.add(printer.printSuccess(currentUser, currentMovieList));
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
                                                || !movie.getName().startsWith(startsWith) ) {
                                            currentMovieList.remove(movie);
                                        }
                                    }
                                }
                                filteredMovieList = new ArrayList<>(currentMovieList);
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
                                filter.filter(currentMovieList);
                                filteredMovieList = new ArrayList<>(currentMovieList);
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
                                String movieTitle = currentAction.getMovie();
                                int error = 0;
                                if (filteredMovieList.isEmpty()) {
                                    output.add(printer.printError());
                                    break;
                                }
                                Movie movie = filteredMovieList.get(0);

                                error = currentUser.buyMovie(movie);
                                if (error == -1) {
                                    output.add(printer.printError());
                                    break;
                                }
                                output.add(printer.printSuccess(currentUser, filteredMovieList));
                                break;

                            } else {
                                /* if the current page is not details, the error is printed */
                                output.add(printer.printError());
                            }
                            break;
                        case WATCH:
                            if (currentPage.equals(DETAILS)) {
                                String movieTitle = currentAction.getMovie();
                                if (filteredMovieList.isEmpty()) {
                                    output.add(printer.printError());
                                    break;
                                }
                                Movie toWatch = filteredMovieList.get(0);

                                int error = currentUser.watchMovie(toWatch);
                                if (error == -1) {
                                    output.add(printer.printError());
                                    break;
                                }
                                if (error == 0) {
                                    output.add(printer.printSuccess(currentUser, filteredMovieList));
                                    break;
                                }
                            } else {
                                /* if the current page is not upgrades, the error is printed */
                                output.add(printer.printError());
                                break;
                            }
                        case LIKE:
                            if (currentPage.equals(DETAILS)) {
                                String movieTitle = currentAction.getMovie();
                                if (filteredMovieList.isEmpty()) {
                                    output.add(printer.printError());
                                    break;
                                }
                                Movie toLike = filteredMovieList.get(0);
                                int error = currentUser.likeMovie(toLike);
                                if (error == -1) {
                                    output.add(printer.printError());
                                    break;
                                }
                                output.add(printer.printSuccess(currentUser, filteredMovieList));
                            } else {
                                /* if the current page is not upgrades, the error is printed */
                                output.add(printer.printError());
                            }
                            break;
                        case RATE:
                            if (currentPage.equals(DETAILS)) {
                                String movieTitle = currentAction.getMovie();
                                if (filteredMovieList.isEmpty()) {
                                    output.add(printer.printError());
                                    break;
                                }
                                Movie toRate = filteredMovieList.get(0);
                                int error = currentUser.rateMovie(toRate, currentAction.getRate());
                                if (error == -1) {
                                    output.add(printer.printError());
                                    break;
                                }
                                output.add(printer.printSuccess(currentUser, filteredMovieList));

                            } else {
                                /* if the current page is not upgrades, the error is printed */
                                output.add(printer.printError());
                            }
                            break;
                        default:
                    }
                    break;
                default:
            }
        }
    }

    private void addMoviesAvailable() {
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
