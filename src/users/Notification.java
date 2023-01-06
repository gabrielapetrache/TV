package users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Notification {
    private final String movieName;
    private final String message;

    /**
     * Constructor for the Notification class
     * @param movieName the name of the movie
     * @param message the message
     */
    public Notification(final String movieName, final String message) {
        this.movieName = movieName;
        this.message = message;
    }

    /**
     * Method to print the notification
     * @return the notification
     */
    public ObjectNode printNotification() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode noti = mapper.createObjectNode();

        noti.put("movieName", movieName);
        noti.put("message", message);

        return noti;
    }
}
