package presenter;

import Views.ICreateGameView;

public class CreateGamePresenter implements ICreateGamePresenter {

    private ICreateGameView view;

    public CreateGamePresenter(ICreateGameView view) {
        this.view = view;
    }
}
