package thePollerExpress.utilities;


import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.game.interfaces.IGameView;
import thePollerExpress.views.game.interfaces.IMapView;

public interface IViewFactory {

    IPollerExpressView createLobbyView();
    IGameView createGameView();
    IMapView createMapView();

}
