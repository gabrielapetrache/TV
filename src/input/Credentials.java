package input;

public class Credentials {
    private String name;
    private String password;
    private String accountType;
    private String country;
    private int balance;

    public Credentials() {
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
     * @param name
     */
    public void setName(String name) {
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
     * @param password
     */
    public void setPassword(String password) {
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
     * @param accountType
     */
    public void setAccountType(String accountType) {
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
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * getter for balance
     * @return balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * setter for balance
     * @param balance
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "credentials{"
                + "name="
                + name
                + ", password="
                + password
                + ", accountType="
                + accountType
                +  ", country="
                + country
                + ", balance="
                + balance
                + "}";
    }
}
