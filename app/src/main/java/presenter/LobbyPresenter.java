package presenter;

import com.pollerexpress.models.Game;
import com.pollerexpress.models.GameInfo;

import java.util.Observable;
import java.util.Observer;

import Views.ILobbyView;
import Views.ILoginView;
import Views.LobbyFragment;
import cs340.pollerexpress.ClientData;
import cs340.pollerexpress.SetupFacade;

/**
 * At least in phase 1, no async task is necessary for this class.
 */
public class LobbyPresenter implements ILobbyPresenter, Observer {

    private ILobbyView lobbyView;
    private ClientData clientData;

    public LobbyPresenter(ILobbyView lobbyView){

        this.lobbyView = lobbyView;
        clientData = cs340.pollerexpress.ClientData.getInstance();
    }
 

    @Override
    public void startButtonPressed() {

        /**todo
         * commented out to get more points on phase 1, need to make this not crash latter
         */
        if(clientData.getGame()==null){
            lobbyView.displayMessage("game is null");
        }else {
            Game game = clientData.getGame();
            GameInfo gameInfo = game.getGameInfo();
            int playerNum = gameInfo.getNumPlayers();


            if (playerNum < 2) {

                // if the game has less than two people
                lobbyView.displayMessage("Not enough people");
            } else {
                // if the game has at least two people
                lobbyView.displayMessage("Move to Game View");
            }
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
