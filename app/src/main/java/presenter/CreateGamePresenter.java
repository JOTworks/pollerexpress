package presenter;

import com.pollerexpress.models.Color;
import com.pollerexpress.models.ErrorResponse;

import Views.ICreateGameView;
import cs340.pollerexpress.SetupFacade;

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
    public void onCreateGameClicked(String numOfPlayers, String user_color) {

        numPlayers = Integer.parseInt(numOfPlayers);
        userColor = Color.PLAYER.valueOf(user_color);

        if( gameName.length() > 0 ) {

            ErrorResponse response = facade.createGame(gameName, numPlayers, userColor);

            if( response != null ) {

                view.displayError(response.getMessage());
            }
            else {

                // if the game was successfully created,
                // go back to the selection view.
                view.changeToSetupGameView();
            }
        }
    }

    @Override
    public void onBackArrowClicked() {

        // No game was actually created,
        // so no model data needs to be updated
        view.changeToSetupGameView();
    }


//    public class createGameTask extends AsyncTask<CreateGameRequest, Void, ErrorResponse> {
//
//        @Override
//        protected ErrorResponse doInBackground(CreateGameRequest... createGameRequests) {
//            return null;
//        }
//    }

}
