package presenter;

import com.pollerexpress.models.ErrorResponse;

import Views.ILoginView;
import cs340.pollerexpress.SetupFacade;

/**
 * (DONE!) Logic that was, in 240, being done in the user interface is
 * now going to be done in this presenter class.
 * That logic includes:
 * enabling/disabling buttons and
 * sending login/register requests
 */
public class LoginPresenter implements ILoginPresenter {

    private ILoginView loginView;
    private SetupFacade facade;

    public LoginPresenter(ILoginView loginView){

        this.loginView = loginView;
        facade = new SetupFacade();
    }

    /**
     * @pre username and password have length greater than zero
     */
    @Override
    public void logIn(String username, String password) {

        ErrorResponse response = facade.login(username, password);
        if( response != null)
        {
            loginView.displayError(response.getMessage());
        }
        else {

            loginView.changeToSetupGameView();
        }
    }

    /**
     * @pre username and password have length greater than zero
     */
    @Override
    public void register(String username, String password) {

        ErrorResponse response = facade.register(username, password);
        if( response != null)
        {
            loginView.displayError(response.getMessage());
        }
        else {

            loginView.changeToSetupGameView();
        }
    }

    @Override
    public void updateLogin(String username, String password) {

        if(username.length() > 0 && password.length() > 0) {
            loginView.enableLogin();
        }
    }

    @Override
    public void updateRegister(String username, String password, String confirm) {

        if(username.length() > 0 && password.length() > 0) {
            loginView.enableRegister();
        }

    }
}