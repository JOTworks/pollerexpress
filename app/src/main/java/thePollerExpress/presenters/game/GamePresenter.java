package thePollerExpress.presenters.game;

import com.shared.exceptions.CommandFailed;
import com.shared.models.interfaces.ICommand;

import thePollerExpress.facades.GameFacade;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.interfaces.IGamePresenter;
import thePollerExpress.utilities.AsyncRunner;
import thePollerExpress.views.game.interfaces.IGameView;

public class GamePresenter implements IGamePresenter {

    private IGameView view;
    private GameFacade facade = new GameFacade();
    ClientData CD = ClientData.getInstance();

    public GamePresenter(IGameView view) {
        this.view = view;
    }



}
