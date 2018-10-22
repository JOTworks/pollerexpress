package thePollerExpress.presenters.setup;

import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;

import java.util.Observable;
import java.util.Observer;

import thePollerExpress.views.setup.ILobbyView;
import thePollerExpress.models.ClientData;

/**
 * Abby
 * At least in phase 1, no async task is necessary for this class.
 */
public class LobbyPresenter implements ILobbyPresenter, Observer {

    private ILobbyView lobbyView;
    private ClientData clientData;

    public LobbyPresenter(ILobbyView lobbyView){

        this.lobbyView = lobbyView;
        clientData = ClientData.getInstance();
        clientData.addObserver(this);
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


            if (playerNum < clientData.getGame().getMaxPlayers())
            {

                // if the game has less than two people
                lobbyView.displayMessage("Not enough people");
            } else {
                // if the game has at least two people
                lobbyView.displayMessage("At this point, we want to move to Game View");
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
    public void update(Observable o, Object arg)
    {
        if( !(arg instanceof Player) ) return;
        Player p = (Player) arg;
        Game game = clientData.getGame();
        int dex = game.getPlayers().indexOf(p);
        if(dex == -1)
        {
            lobbyView.playerLeft(dex);
        }
        else
        {
            lobbyView.playerJoined(p);
        }
    }

    @Override
    public Game getGame()
    {
        return clientData.getGame();
    }
}