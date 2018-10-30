package thePollerExpress.presenters.game;

import com.shared.models.Command;
import com.shared.models.User;
import com.shared.utilities.CommandsExtensions;

import thePollerExpress.facades.GameFacade;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.interfaces.IGamePresenter;
import thePollerExpress.utilities.AsyncRunner;
import thePollerExpress.views.game.interfaces.IGameView;

/**
 * Abby
 */
public class DestinationSelectionPresenter implements IGamePresenter {

    private IGameView view;
    private GameFacade facade;
    ClientData clientData = ClientData.getInstance();

    @Override
    public void discardDestCard(int destCardIndex) {
        //update = false;
        User user = clientData.getUser();

        AsyncRunner commandRunner = new AsyncRunner(view);

        Class<?>[] types = {User.class, Integer.class};
        Object[] params = {user, destCardIndex};
        Command discardDestCard = new Command(CommandsExtensions.clientSideFacade + "GameFacade",
                "discardDestCard", types, params, new GameFacade());

        commandRunner.execute(discardDestCard);
        //update = true;
    }

}
