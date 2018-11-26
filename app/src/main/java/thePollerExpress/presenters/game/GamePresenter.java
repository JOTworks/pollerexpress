package thePollerExpress.presenters.game;

import com.shared.models.EndGameResult;

import java.util.Observable;
import java.util.Observer;

import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.interfaces.IGamePresenter;
import thePollerExpress.utilities.IViewFactory;
import thePollerExpress.utilities.RealViewFactory;
import thePollerExpress.views.game.interfaces.IGameView;

public class GamePresenter implements IGamePresenter, Observer {

    private IGameView view;
    ClientData CD = ClientData.getInstance();

    public GamePresenter(IGameView view) {
        this.view = view;
        CD.updateAll();
        CD.getGameResult().addObserver(this);
    }


    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof EndGameResult) {
            //view.displayError("update happened!");
            IViewFactory viewFactory = new RealViewFactory();
            view.changeView(viewFactory.createMapView());
        }
    }
}
