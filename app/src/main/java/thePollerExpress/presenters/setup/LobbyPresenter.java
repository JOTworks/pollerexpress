package thePollerExpress.presenters.setup;

import android.os.Debug;
import android.util.Log;

import com.shared.exceptions.CommandFailed;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.interfaces.ICommand;
import com.shared.models.states.GameState;

import java.util.Observable;
import java.util.Observer;

import thePollerExpress.facades.GameFacade;
import thePollerExpress.utilities.AsyncRunner;
import thePollerExpress.utilities.ViewFactory;
import thePollerExpress.views.setup.ILobbyView;
import thePollerExpress.models.ClientData;

/**
 * Lobby presenter
 * @author multiple
 */
public class LobbyPresenter implements ILobbyPresenter, Observer {

    private ILobbyView lobbyView; //the view this presenter deals with
    private GameFacade facade = new GameFacade();
    private ClientData clientData; //where the data is stored.
    private Observable observing; // the data this presenter observes

    /**
     * LobbyPresenter Constructor
     * @pre none
     * @post the presenter is observing the game model.
     * @param lobbyView
     */
    public LobbyPresenter(ILobbyView lobbyView)
    {
        this.lobbyView = lobbyView;
        clientData = ClientData.getInstance();
        observing = clientData.getGame();
        observing.addObserver(this);
    }


    /**
     * implements the logic of the start game button
     * @pre the user on this client is the one who created the game
     * @pre the server is up
     * @pre game.getNumPlayers == game.getMaxPlayers()
     * @post the game is started this presenter switches views.
     * @post GameState = CanDiscardDestinationCards
     */
    @Override
    public void startButtonPressed() {

        if(clientData.getGame()==null){
            lobbyView.displayMessage("game is null");
        }else {
            Game game = clientData.getGame();
            int playerNum = game.getNumPlayers();
            if (playerNum < clientData.getGame().getMaxPlayers())
            {
                lobbyView.displayMessage(game.getNumPlayers() + " out of "+ clientData.getGame().getMaxPlayers());
            } else {
                //run an async task.
                AsyncRunner startGameTask = new AsyncRunner(lobbyView);
                startGameTask.execute(new ICommand()
                {
                    @Override
                    public Object execute() throws CommandFailed
                    {
                        return facade.startGame(clientData.getUser());
                    }
                });
            }
        }
    }

    /**
     * @pre none
     * @post returns the gameselection view,
     * @post the player leaves the game
     */
    @Override
    public void onBackArrowPressed()
    {
        //call player leaves game
        lobbyView.changeToSetupGameView();
    }

    /**
     * Called when the game updates
     * @pre arg is instance of gamestate | player
     * @post the view correctly reflects the model
     * @param o object being observed, the game model
     * @param arg the part being updated
     */
    @Override
    public void update(Observable o, Object arg)
    {
        System.out.println("Entered LobbyPresenterUpdate with " + arg.getClass().toString()); //TODO: remove debug statement

        if (arg instanceof GameState) //we need to go to the next phase
        {
            lobbyView.changeToGameView();
        }
        else if (arg instanceof Player) {

            Player p = (Player) arg;
            Log.d("LobbyPresenter", p.name);
            Game game = clientData.getGame();
            int dex = game.getPlayers().indexOf(p);
            Log.d("LobbyPresenter", String.format("%d index of player %d", dex, game.getNumPlayers()));
            if (dex == -1) {
                lobbyView.playerLeft(dex);
            } else {
                lobbyView.playerJoined(p);
            }
        }
    }

    /**
     * gets the game the user is currently in
     * @pre none
     * @post nothing is changed
     * @return the game the user is in
     */
    @Override
    public Game getGame()
    {
        return clientData.getGame();
    }

    /**
     * @pre nothing
     * @post this presenter no longer observes to the game.
     */
    public void onDestroy()
    {
        observing.deleteObserver(this);
    }
}
