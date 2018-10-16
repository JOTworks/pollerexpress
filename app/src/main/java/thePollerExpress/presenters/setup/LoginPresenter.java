package thePollerExpress.presenters.setup;

import android.os.AsyncTask;

import com.shared.models.reponses.ErrorResponse;
import com.shared.models.requests.LoginRequest;

import thePollerExpress.views.setup.ILoginView;
import thePollerExpress.facades.SetupFacade;

/**
 * Logic that was, in 240, being done in the user interface is
 * now going to be done in this presenter class.
 * That logic includes:
 * enabling/disabling buttons and
 * sending login/register requests
 *
 * This class doesn't need to implement observer because
 * the login view does not get updated based on model data
 * changing.
 */
public class LoginPresenter implements ILoginPresenter {

    private ILoginView loginView;
    private SetupFacade facade;

    public LoginPresenter(ILoginView loginView){

        this.loginView = loginView;
        facade = new SetupFacade();
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

    /**
     * @pre username and password have length greater than zero
     */
    @Override
    public void logIn(String username, String password) {

        if(username.length() <= 0 || username.length() > 1000
                && password.length() <= 0 || password.length() > 1000) {
            loginView.displayError("field with too many or too few characters");
            return;
        }

            LoginTask loginTask = new LoginTask();

            LoginRequest loginRequest = new LoginRequest(username, password);

            loginTask.execute(loginRequest);


    }

    /**
     * @pre username and password have length greater than zero
     */
    @Override
    public void register(String username, String password, String confirm) {

        if(!password.equals(confirm)) {
            loginView.displayError("your passwords dont match");
            return;
        }
        if(username.length() <= 0 || username.length() > 1000
                && password.length() <= 0 || password.length() > 1000) {
            loginView.displayError("field with too many or too few characters");
            return;
        }

            RegisterTask registerTask = new RegisterTask();

            LoginRequest loginRequest = new LoginRequest(username, password);

            registerTask.execute(loginRequest);
    }

    public class RegisterTask extends AsyncTask<LoginRequest, Void, ErrorResponse> {

        @Override
        protected ErrorResponse doInBackground(LoginRequest... params) {

            LoginRequest loginRequest = params[0];
            return facade.register(loginRequest);
        }

        @Override
        protected void onPostExecute(ErrorResponse errorResponse) {

            if( errorResponse != null ) {

                loginView.displayError(errorResponse.getMessage());
            }
            else {

                loginView.changeToSetupGameView();
            }
        }
    }

    public class LoginTask extends AsyncTask<LoginRequest, Void, ErrorResponse> {

        @Override
        protected ErrorResponse doInBackground(LoginRequest... params) {

            LoginRequest loginRequest = params[0];
            return facade.login(loginRequest);
        }

        @Override
        protected void onPostExecute(ErrorResponse errorResponse) {

            if( errorResponse != null)
            {
                loginView.displayError(errorResponse.getMessage());
            }
            else {

                loginView.changeToSetupGameView();
            }
        }
    }

}