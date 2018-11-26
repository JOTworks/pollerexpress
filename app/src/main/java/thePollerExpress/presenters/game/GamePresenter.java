package thePollerExpress.presenters.game;

import com.shared.exceptions.CommandFailed;
import com.shared.exceptions.NotImplementedException;
import com.shared.models.EndGameResult;
import com.shared.models.interfaces.ICommand;

import java.util.Observable;
import java.util.Observer;

import thePollerExpress.facades.GameFacade;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.interfaces.IGamePresenter;
import thePollerExpress.utilities.AsyncRunner;
import thePollerExpress.utilities.IViewFactory;
import thePollerExpress.utilities.RealViewFactory;
import thePollerExpress.views.game.interfaces.IGameView;

public class GamePresenter implements IGamePresenter, Observer {

    private IGameView view;
    private GameFacade facade = new GameFacade();
    ClientData CD = ClientData.getInstance();

    public GamePresenter(IGameView view) {
        this.view = view;
        CD.updateAll();
        CD.getGameResult().addObserver(this);
    }


    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof EndGameResult) {
            IViewFactory viewFactory = new RealViewFactory();
            view.changeView(viewFactory.createMapView());
        }
    }
}
