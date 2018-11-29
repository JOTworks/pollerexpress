package thePollerExpress.presenters.game;

import com.shared.exceptions.NotImplementedException;

import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.interfaces.IEndGamePresenter;
import thePollerExpress.views.game.EndGameFragment;
import thePollerExpress.views.game.interfaces.IEndGameView;

public class EndGamePresenter implements IEndGamePresenter {

    private IEndGameView view;
    private ClientData clientData;

    public EndGamePresenter(EndGameFragment endGameFragment) {
        view = endGameFragment;
        clientData = ClientData.getInstance();
    }

    @Override
    public void findNewGame() {
        throw new NotImplementedException("findNewGame()");
    }
}
