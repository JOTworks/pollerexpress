package thePollerExpress.presenters.game;

import com.shared.models.Command;
import com.shared.models.User;
import com.shared.utilities.CommandsExtensions;

import thePollerExpress.facades.GameFacade;
import thePollerExpress.facades.SetupFacade;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.interfaces.IInitializationPresenter;
import thePollerExpress.utilities.AsyncRunner;
import thePollerExpress.views.game.interfaces.IGameView;
import thePollerExpress.views.setup.IGameSelectionView;

public class InitializationPresenter implements IInitializationPresenter {

    private IGameView view; //TODO: initialization view
    private GameFacade facade;
    ClientData clientData = ClientData.getInstance();

    @Override
    public void startGame() {
        //update = false;
        User user = clientData.getUser();

        AsyncRunner commandRunner = new AsyncRunner(view);

        Class<?>[] types = {User.class};
        Object[] params = {user};
        Command startGameCommand = new Command(CommandsExtensions.clientSideFacade + "GameFacade",
                "startGame", types, params, new GameFacade());

        commandRunner.execute(startGameCommand);
        //update = true;

    }
}
