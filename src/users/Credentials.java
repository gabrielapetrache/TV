package users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Credentials {
    private String name;
    private String password;
    private String accountType;
    private String country;
    private String balance;

    /**
     * Default constructor
     */
    public Credentials() {
    }

    /**
     * Constructor for Credentials
     */
    public Credentials(final String name, final String password, final String accountType,
                       final String country, final String balance) {
        this.name = name;
        this.password = password;
        this.accountType = accountType;
        this.country = country;
        this.balance = balance;
    }

    /**
     * Method that writes the user's credentials in a JSON format
     */
    public ObjectNode printCredentials() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode credentials = mapper.createObjectNode();

        credentials.put("name", name);
        credentials.put("password", password);
        credentials.put("accountType", accountType);
        credentials.put("country", country);
        credentials.put("balance", balance);

        return credentials;
    }

    /**
     * getter for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for name
     * @param name name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * getter for password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter for password
     * @param password password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * getter for accountType
     * @return accountType
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * setter for accountType
     * @param accountType accountType
     */
    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }

    /**
     * getter for country
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * setter for country
     * @param country country
     */
    public void setCountry(final String country) {
        this.country = country;
    }

    /**
     * getter for balance
     * @return balance
     */
    public String getBalance() {
        return balance;
    }

    /**
     * setter for balance
     * @param balance balance
     */
    public void setBalance(final String balance) {
        this.balance = balance;
    }
}
