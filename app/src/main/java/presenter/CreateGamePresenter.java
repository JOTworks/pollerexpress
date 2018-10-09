package presenter;

import android.os.AsyncTask;

import com.pollerexpress.models.Color;
import com.pollerexpress.reponse.ErrorResponse;

import Views.ICreateGameView;
import cs340.pollerexpress.SetupFacade;

/**
 * Doesn't need to implement observer because the create
 * game view is not updated based on changed to models.
 */
public class CreateGamePresenter implements ICreateGamePresenter {

    private ICreateGameView view;
    private SetupFacade facade;
    private String gameName;
    private int numPlayers;
    private Color.PLAYER userColor;

    public CreateGamePresenter(ICreateGameView view) {

        this.view = view;
        facade = new SetupFacade();
    }

    // DONE!
    @Override
    public void setNumOfPlayers(String numOfPlayers) {

        numPlayers = Integer.parseInt(numOfPlayers);
    }

    // DONE!
    @Override
    public void setUserColor(String color) {

        // convert string color to an enum
        userColor = Color.PLAYER.valueOf(color);
    }

    @Override
    public void setGameName(String name) {

        gameName = name;
    }

    @Override
    public void onCreateGameClicked(String numOfPlayers, String user_color)
    {

        numPlayers = Integer.parseInt(numOfPlayers);
        userColor = Color.PLAYER.valueOf(user_color);

        if( gameName.length() > 0 && gameName.length() < 1000 ) {

            CreateGameTask createGameTask = new CreateGameTask();

            Request request = new Request(gameName, numPlayers, userColor);
            createGameTask.execute(request);
        }
        else {

            view.displayError("Game name either too long or too short.");
        }
    }

    @Override
    public void onBackArrowClicked() {

        // No game was actually created,
        // so no model data needs to be updated
        view.changeToSetupGameView();
    }

    private class Request {
        public String name;
        public int num;
        public Color.PLAYER color;

        public Request(String name, int num, Color.PLAYER color) {

            this.name = name;
            this.num = num;
            this.color = color;
        }

        public String getName() { return name; }
        public int getNum() { return num; }

        public Color.PLAYER getColor() {
            return color;
        }
    }

    public class CreateGameTask extends AsyncTask<Request, Void, ErrorResponse> {

        @Override
        protected ErrorResponse doInBackground(Request... params) {

            Request request = params[0];
            String name = request.getName();
            int num = request.getNum();
            Color.PLAYER userColor = request.getColor();

            return facade.createGame(name, numPlayers, userColor);
        }

        @Override
        protected void onPostExecute(ErrorResponse response) {

            if( response != null ) {

                view.displayError(response.getMessage());
            }
            else {

                // if the game was successfully created,
                // go back to the selection view.
                view.changeToLobbyView();
            }

        }
    }

}
