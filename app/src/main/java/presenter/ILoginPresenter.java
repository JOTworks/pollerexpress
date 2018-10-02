package presenter;

/**
 * This class is responsible for defining the
 * methods that the loginViews can call on the
 * loginPresenter
 */
public interface ILoginPresenter {

    /**
     * Attempts to log a user in
     */
    public void logIn();

    /**
     * Attempts to register a user
     */
    public void register();

    /**
     * Checks whether the username and password
     * meet the requirements for enabling the
     * login button
     */
    public void updateLogin(String username, String password);

    /**
     *
     * @param username
     * @param password
     * @param confirm
     */
    public void updateRegister(String username, String password, String confirm);
}
