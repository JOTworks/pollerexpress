package thePollerExpress.utilities;


import com.shared.models.EndGameResult;

import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.game.interfaces.IEndGameView;
import thePollerExpress.views.game.interfaces.IGameView;
import thePollerExpress.views.game.interfaces.IMapView;

public interface IViewFactory {

    IPollerExpressView createLobbyView();
    IPollerExpressView createDestinationHandView();
    IGameView createGameView();
    IMapView createMapView();
    IEndGameView createEndGameView(EndGameResult result);
}
