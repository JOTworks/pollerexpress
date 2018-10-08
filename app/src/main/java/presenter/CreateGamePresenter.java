package presenter;

import Views.ICreateGameView;

public class CreateGamePresenter implements ICreateGamePresenter {

    private ICreateGameView view;

    public CreateGamePresenter(ICreateGameView view) {
        this.view = view;
    }

    @Override
    public void setNumOfPlayers(String numOfPlayers) {}

    public void createGame() {
    }

    @Override
    public void setUserColor(String numOfPlayers) {}

    public void okButtonClicked() {

    }

    @Override
    public void cancelButtonClicked() {

    }

    @Override
    public void playerNumSelected() {

    }

    @Override
    public void nameUpdate() {

    }

    @Override
    public void colorPicked() {

    }
}
