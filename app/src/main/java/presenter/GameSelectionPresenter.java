package presenter;

import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.User;

import java.util.Observable;
import java.util.Observer;

import Views.IGameSelectionView;
import cs340.pollerexpress.ClientData;
import cs340.pollerexpress.SetupFacade;

/**
 * (UPDATE METHOD NEEDS IMPLEMENTATION!)
 * Responsible for implementing logic for game selection view
 */
public class GameSelectionPresenter implements IGameSelectionPresenter, Observer {

    private IGameSelectionView view;
    private SetupFacade facade;
    ClientData clientData = ClientData.getInstance();

    public GameSelectionPresenter(IGameSelectionView view) {
        this.view = view;
        facade = new SetupFacade();
    }

    @Override
    public void createGame() {

        /*
        * facade.createGame(...) should be called in the
        * create game presenter since we won't have the
        * necessary information until then.
        * */

        view.changeCreateGameView();
    }

    @Override
    public void joinGame(int gameIndex) {

        User user = clientData.getUser();
        GameInfo[] gameInfoList = clientData.getGameInfoList();

        GameInfo gameInfo = gameInfoList[gameIndex];
        facade.joinGame(user, gameInfo);

        if( gameInfo.getNumPlayers() == gameInfo.getMaxPlayers() ) {

            view.disableGame(gameIndex);
        }


        view.changeLobbyView();

    }

    @Override
    public GameInfo[] getGameList() {

        return clientData.getGameInfoList();
    }


    @Override
    public void update(Observable o, Object arg) {

        // call some view methods to update.
    }
}
