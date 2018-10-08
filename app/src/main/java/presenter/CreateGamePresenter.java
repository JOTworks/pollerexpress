package presenter;

import com.pollerexpress.models.Color;
import com.pollerexpress.models.ErrorResponse;
import com.pollerexpress.models.GameInfo;

import java.util.Observable;
import java.util.Observer;

import Views.ICreateGameView;
import cs340.pollerexpress.SetupFacade;

public class CreateGamePresenter implements ICreateGamePresenter, Observer {

    private ICreateGameView view;
    private SetupFacade facade;
    private String gameName;
    private int numPlayers;
    private Color.PLAYER userColor;

    public CreateGamePresenter(ICreateGameView view) {

        this.view = view;
        facade = new SetupFacade();
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void setNumOfPlayers(String numOfPlayers) {

        numPlayers = Integer.parseInt(numOfPlayers);
    }

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
    public void createGame() {

        if( gameName.length() > 0 ) {

            ErrorResponse response = facade.createGame(gameName, numPlayers, userColor);

            if( response != null ) {

//                view.displayError(response.getMessage());
            }
            else {


            }
        }


    }

    @Override
    public void onOkClicked() {

    }

    @Override
    public void onCancelClicked() {

    }
}
