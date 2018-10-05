package presenter;

import Views.ILoginView;
import cs340.pollerexpress.SetupFacade;

public class LoginPresenter implements ILoginPresenter {

    private ILoginView loginView;

    public LoginPresenter(ILoginView loginView){
        this.loginView = loginView;
    }


    @Override
    public void logIn(String username, String password) {

        if(username.length() > 0 ) {
            try {
                SetupFacade facade = new SetupFacade();
                facade.login(username, password);
            }
            catch(Exception e) {
                loginView.displayMessage(e.getMessage());
            }
        }

    }

    @Override
    public void register(String username, String password) {

    }

    @Override
    public void updateLogin(String username, String password) {

    }

    @Override
    public void updateRegister(String username, String password, String confirm) {

    }
}