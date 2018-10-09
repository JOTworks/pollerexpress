package presenter;

import android.os.AsyncTask;

import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.User;
import com.pollerexpress.reponse.ErrorResponse;

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

        JoinGameTask joinGameTask = new JoinGameTask();

        Request request = new Request(user, gameInfo);
        joinGameTask.execute(request);

    }

    @Override
    public GameInfo[] getGameList() {

        if(clientData.getGameInfoList() != null)
            return clientData.getGameInfoList().toArray(new GameInfo[clientData.getGameInfoList().size()]);
        return new GameInfo[]{};
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


        // refresh the list of games in the view
        view.renderGames(gameInfoList);

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
                view.displayError(response.getMessage());
            }
            else {
                view.changeToLobbyView();
            }

        }
    }
}
