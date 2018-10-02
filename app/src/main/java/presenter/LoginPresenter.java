package presenter;

import Views.ILoginView;

public class LoginPresenter implements ILoginPresenter {

    private ILoginView view;

    public LoginPresenter(ILoginView view){
        this.view = view;
    }

    @Override
    public void logIn() {

    }

    @Override
    public void register() {

    }

    @Override
    public void updateLogin(String username, String password) {

    }

    @Override
    public void updateRegister(String username, String password, String confirm) {

    }
}
