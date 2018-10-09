package presenter;

import com.pollerexpress.models.Game;
import com.pollerexpress.models.GameInfo;

import java.util.Observable;
import java.util.Observer;

import Views.ILobbyView;
import Views.LobbyFragment;
import cs340.pollerexpress.ClientData;

/**
 * At least in phase 1, no async task is necessary for this class.
 */
public class LobbyPresenter implements ILobbyPresenter, Observer {

    ILobbyView lobbyView = new LobbyFragment();
    ClientData clientData = ClientData.getInstance();

    @Override
    public void startButtonPressed() {

        Game game = clientData.getGame();
        GameInfo gameInfo = game.getGameInfo();
        int playerNum = gameInfo.getNumPlayers();


        if( playerNum < 2 ) {

            // if the game has less than two people
            lobbyView.displayMessage("Not enough people");
        }
        else {
            // if the game has at least two people
            lobbyView.displayMessage("Move to Game View");
        }

    }

    @Override
    public void onBackArrowPressed() {

        lobbyView.changeToSetupGameView();
    }

    /*
    * What needs to happen to the lobby when model data changes?
    * When a player enters or leaves the game, the lobby view
    * needs to be updated to reflect that.
    * */
    @Override
    public void update(Observable o, Object arg) {


    }
}
