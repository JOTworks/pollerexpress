package presenter;

import android.os.AsyncTask;

import com.pollerexpress.reponse.ErrorResponse;
import com.pollerexpress.request.LoginRequest;

import Views.ILoginView;
import cs340.pollerexpress.SetupFacade;

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

        LoginTask loginTask = new LoginTask();

        LoginRequest loginRequest = new LoginRequest(username, password);

        loginTask.execute(loginRequest);
    }

    /**
     * @pre username and password have length greater than zero
     */
    @Override
    public void register(String username, String password) {

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
//            Test_ClientCommunicator_Jack cc = Test_ClientCommunicator_Jack.instance();
//            String response = cc.sendTest();
//            ErrorResponse errorResponse = new ErrorResponse(response, new Exception(), new Command());
//            return errorResponse;
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