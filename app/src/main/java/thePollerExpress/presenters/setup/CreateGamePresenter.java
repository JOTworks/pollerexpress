package thePollerExpress.presenters.setup;

import android.os.AsyncTask;

import com.shared.exceptions.CommandFailed;
import com.shared.models.Color;
import com.shared.models.PollResponse;
import com.shared.models.interfaces.ICommand;
import com.shared.models.reponses.ErrorResponse;

import java.util.Observable;

import thePollerExpress.utilities.AsyncRunner;
import thePollerExpress.utilities.RealViewFactory;
import thePollerExpress.utilities.ViewFactory;
import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.setup.ICreateGameView;
import thePollerExpress.facades.SetupFacade;

/**
 * Abby
 * Doesn't need to implement observer because the create
 * game view is not updated based on changed to models.
 */
public class CreateGamePresenter implements ICreateGamePresenter
{

    private ICreateGameView view;
    private SetupFacade facade;
    private String gameName;
    private int numPlayers;
    private Color.PLAYER userColor;

    public CreateGamePresenter(ICreateGameView view)
    {

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

        if( gameName.length() > 0 && gameName.length() < 1000 )
        {

            AsyncRunner createGameTask = new AsyncRunner(view);
            createGameTask.setNextView(ViewFactory.createLobbyView());

            createGameTask.execute(new ICommand()
            {
                @Override
                public Object execute() throws CommandFailed
                {
                    return facade.createGame(gameName, numPlayers, userColor);
                }
            });
        }
        else {

            view.displayError("Game name either too long or too short.");
        }
    }

    @Override
    public void update(Observable o, Object arg)
    {

    }

    @Override
    public void onBackArrowClicked()
    {

        // No game was actually created,
        // so no model data needs to be updated
        view.changeToSetupGameView();
    }


}
