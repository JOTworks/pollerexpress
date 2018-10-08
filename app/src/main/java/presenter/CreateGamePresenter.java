package presenter;

import java.util.Observable;
import java.util.Observer;

import Views.ICreateGameView;

public class CreateGamePresenter implements ICreateGamePresenter, Observer {

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

    /**
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {


    }
}
