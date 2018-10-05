package presenter;

/**
 * (DONE!) This class is responsible for defining the
 * methods that the login view can call on the
 * loginPresenter
 */
public interface ILoginPresenter {

    /**
     * Attempts to log a user in. If successful,
     * calls a method on the login view to switch views.
     * Otherwise, calls a method on the view to
     * display a message to the user.
     * @param username
     * @param password
     */
    public void logIn(String username, String password);


    /**
     * Attempts to register a user. If successful,
     * calls a method on the login view to switch views.
     * Otherwise, calls a method on the view to
     * display a message to the user.
     * @param username
     * @param password
     */
    public void register(String username, String password);

    /**
     * If the parameters meet the requirements
     * for enabling login, this method enables
     * login. Otherwise, it does nothing.
     * @param username
     * @param password
     */
    public void updateLogin(String username, String password);

    /**
     * If the parameters meet the requirements
     * for enabling register, this method
     * enables register. Otherwise, it does nothing.
     * @param username
     * @param password
     * @param confirm
     */
    public void updateRegister(String username, String password, String confirm);
}