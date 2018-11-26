package thePollerExpress.utilities;


import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.game.DestinationHandFragment;
import thePollerExpress.views.game.GameFragment;
import thePollerExpress.views.game.MapView;
import thePollerExpress.views.game.interfaces.IGameView;
import thePollerExpress.views.game.interfaces.IMapView;
import thePollerExpress.views.setup.LobbyFragment;

public class RealViewFactory implements IViewFactory
{
    @Override
    public IPollerExpressView createLobbyView()
    {
        return new LobbyFragment();
    }

    @Override
    public IPollerExpressView createDestinationHandView()
    {
        return new DestinationHandFragment();
    }

    @Override
    public IGameView createGameView()
    {
        return new GameFragment();
    }

    @Override
    public IMapView createMapView()
    {
        return new MapView();
    }

//    @Override
//    public IEndGameView createEndGameView() { return new EndGameView(); }
}
