package thePollerExpress.utilities;

import com.shared.models.Map;
import com.shared.models.interfaces.IDatabaseFacade;

import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.game.interfaces.IGameView;
import thePollerExpress.views.game.interfaces.IMapView;
import thePollerExpress.views.setup.ICreateGameView;

public class ViewFactory
{
    static IViewFactory _ivf = new RealViewFactory();//default db

    public static void setFactory(IViewFactory idf)
    {
        _ivf = idf;
    }
    public static IGameView createGameView() {return _ivf.createGameView();}
    public static IPollerExpressView createLobbyView()
    {
        return _ivf.createLobbyView();
    }
    public static IMapView createMapView()
    {
        return _ivf.createMapView();
    }
}
