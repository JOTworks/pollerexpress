package presenter;

import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.User;

import java.util.Observable;
import java.util.Observer;

import Views.IGameSelectionView;
import cs340.pollerexpress.ClientData;
import cs340.pollerexpress.SetupFacade;

/**
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
        GameInfo[] gameInfoList = clientData.getGameInfoList().toArray(new GameInfo[0]);

        GameInfo gameInfo = gameInfoList[gameIndex];
        facade.joinGame(user, gameInfo);

        /* If facade.joinGame causes update to get called,
        * then this is check is redundant. */
        if( gameInfo.getNumPlayers() == gameInfo.getMaxPlayers() ) {

            view.disableGame(gameIndex);
        }

        view.changeLobbyView();
    }

    @Override
    public GameInfo[] getGameList() {
        return (GameInfo[]) clientData.getGameInfoList().toArray();
    }

    /**
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {

        // get the list of existing games
        GameInfo[] gameInfoList = clientData.getGameInfoList().toArray(new GameInfo[0]);

        // determine which games the user can join
        for(int i = 0; i < gameInfoList.length; i++) {

            GameInfo gameInfo = gameInfoList[i];
            if (gameInfo.getNumPlayers() < gameInfo.getMaxPlayers())
            {
                view.enableGame(i);
            }
            else {
                view.disableGame(i);
            }
        }

        // refresh the list of games in the view
        view.renderGames(gameInfoList);
    }
}
