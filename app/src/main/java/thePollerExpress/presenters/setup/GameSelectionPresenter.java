package thePollerExpress.presenters.setup;

import android.os.AsyncTask;
import android.util.Log;

import com.shared.models.Command;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.User;
import com.shared.models.reponses.ErrorResponse;
import com.shared.utilities.CommandsExtensions;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import thePollerExpress.utilities.AsyncRunner;
import thePollerExpress.utilities.ViewFactory;
import thePollerExpress.views.setup.IGameSelectionView;
import thePollerExpress.models.ClientData;
import thePollerExpress.facades.SetupFacade;
import thePollerExpress.views.setup.LobbyFragment;

/**
 * Abby
 * Responsible for implementing logic for game selection view
 */
public class GameSelectionPresenter implements IGameSelectionPresenter, Observer {

    private IGameSelectionView view;
    private SetupFacade facade;
    ClientData clientData = ClientData.getInstance();

    private boolean update = true;
    public GameSelectionPresenter(IGameSelectionView view) {
        this.view = view;
        Log.d("GameSelectionPresenter", "createdNew");
        facade = new SetupFacade();
        clientData.addObserver(this);

    }

    @Override
    public void createGame() {

        /*
        * facade.createGame(...) should be called in the
        * create game presenter since we won't have the
        * necessary information until then.
        * */
        update = false;

        clientData.deleteObserver(this);
        view.changeCreateGameView();
    }

    @Override
    public void joinGame(GameInfo info)
    {
        update = false;
        User user = clientData.getUser();


        AsyncRunner commandRunner = new AsyncRunner(view);
        commandRunner.setNextView(ViewFactory.createLobbyView());

        Class<?>[] types = {Player.class, GameInfo.class};
        Object[] params = {user, info};
        Command joinGameCommand = new Command(CommandsExtensions.clientSideFacade + "SetupFacade",
                "joinGame", types, params, new SetupFacade());

        commandRunner.execute(joinGameCommand);

        update = true;

    }

    @Override
    public List<GameInfo> getGameList()
    {

        return clientData.getGameInfoList();
    }

    /**
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg)
    {
        if(!update) return;
        // get the list of existing games
        Log.d("update", "ran update");
        // refresh the list of games in the view
        if( arg instanceof Integer)
        {
            view.modifyGameData( (Integer)arg);
        }
        else
        {
            view.renderGames(clientData.getGameInfoList());
        }

        /*
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
        }*/
    }
}
