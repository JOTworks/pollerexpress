package thePollerExpress.presenters.setup;

import android.os.AsyncTask;
import android.util.Log;

import com.shared.models.GameInfo;
import com.shared.models.User;
import com.shared.models.reponses.ErrorResponse;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import thePollerExpress.views.setup.IGameSelectionView;
import thePollerExpress.models.ClientData;
import thePollerExpress.facades.SetupFacade;

/**
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

        JoinGameTask joinGameTask = new JoinGameTask();

        Request request = new Request(user, info);
        joinGameTask.execute(request);
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

    private class Request {

        public User user;
        public GameInfo gameInfo;

        public Request(User user, GameInfo gameInfo) {
            this.user = user;
            this.gameInfo = gameInfo;
        }

        public User getUser() {
            return user;
        }

        public GameInfo getGameInfo() {
            return gameInfo;
        }
    }

    public class JoinGameTask extends AsyncTask<Request, Void, ErrorResponse> {

        @Override
        protected ErrorResponse doInBackground(Request... params) {

            Request request = params[0];
            User user = request.getUser();
            GameInfo gameInfo = request.getGameInfo();
            return facade.joinGame(user, gameInfo);
        }

        @Override
        protected void onPostExecute (ErrorResponse response) {

            if(response != null) {
                view.displayError("unable to join this game");
            }
            else {
                view.changeToLobbyView();
            }

        }
    }
}
