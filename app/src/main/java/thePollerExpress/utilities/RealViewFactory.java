package thePollerExpress.utilities;

import com.shared.models.interfaces.IDatabaseFacade;

import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.game.GameFragment;
import thePollerExpress.views.game.interfaces.IGameView;
import thePollerExpress.views.setup.ICreateGameView;
import thePollerExpress.views.setup.LobbyFragment;

public class RealViewFactory implements IViewFactory
{
    @Override
    public IPollerExpressView createLobbyView()
    {
        return new LobbyFragment();
    }
    @Override
    public IGameView createGameView()
    {
        return new GameFragment();
    }
}
