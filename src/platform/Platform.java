package platform;

import com.fasterxml.jackson.databind.node.ArrayNode;
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

        for (Actions currentAction : actions) {
            String type = currentAction.getType();
            switch (type) {
                case CHANGE:
                    ChangePage changePage = new ChangePage();
                    String newPage = currentAction.getPage();
                    if (!newPage.equals(LOGOUT)) {
                        String check = changePage.execute(currentPage, newPage);
                        if (check.equals("error")) {
                            output.add(printer.printError(currentUser));
                        } else if (check.equals("login") && currentUser != null) {
                            /* if the user is already logged in, he can't log in again,
                            but he can log out */
                            currentUser = null;
                            output.add(printer.printError(currentUser));
                        } else {
                            currentPage = newPage;
                        }

                    } else {
                        /* logout is a separate case from the others,
                         because it also resets the current user */
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
                                        output.add(printer.printSuccess(currentUser));
                                        break;
                                    }
                                }

                                /* if the user is not found, the error is printed */
                                if (currentUser == null) {
                                    currentPage = HOMEPAGEONE;
                                    output.add(printer.printError(currentUser));
                                    break;
                                }
                            } else {
                                /* if the current page is not login, the error is printed */
                                output.add(printer.printError(null));
                            }
                            break;

                        case REGISTER:
                            if (currentPage.equals(REGISTER)) {
                                String name = currentAction.getCredentials().getName();
                                int error = 0;
                                /* check if the user already exists */
                                for (Users user : users) {
                                    if (user.getCredentials().getName().equals(name)) {
                                        System.out.println("Register error");
                                        output.add(printer.printError(currentUser));
                                        error = 1;
                                        break;
                                    }
                                }
                                if (error == 1) {
                                    /* if the user already exists, the error is printed */
                                    currentPage = HOMEPAGEONE;
                                    break;
                                } else {
                                    /* if the user does not exist, it's created */
                                    Credentials credentials = currentAction.getCredentials();
                                    Users newUser = new Users(credentials);
                                    users.add(newUser);
                                    currentUser = newUser;
                                    currentPage = HOMEPAGETWO;
                                    output.add(printer.printSuccess(currentUser));
                                }
                            } else {
                                /* if the current page is not register, the error is printed */
                                System.out.println("Register serror");
                                output.add(printer.printError(currentUser));
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
