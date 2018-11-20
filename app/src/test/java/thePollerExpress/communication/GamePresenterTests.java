package thePollerExpress.communication;

import org.junit.Test;

import thePollerExpress.facades.GameFacade;
import thePollerExpress.presenters.game.GamePresenter;
import thePollerExpress.views.game.GameFragment;

public class GamePresenterTests {

    @Test
    public void startGame_sendsToClientCommunicator() {
        //GamePresenter gp = new GamePresenter(new GameFragment());
        GameFacade gf = new GameFacade();
    }
}
