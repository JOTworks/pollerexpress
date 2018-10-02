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

    void boolean logIn();

    /**
     * Attempts to register a user
     */
    void boolean register();

    /**
     */
    void updateLogin(String username, String password);

    /**
     *
     * @param username
     * @param password
     * @param confirm
     */
    void updateRegister(String username, String password, String confirm);
}
