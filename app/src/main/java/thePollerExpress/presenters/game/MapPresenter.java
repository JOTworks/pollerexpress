package thePollerExpress.presenters.game;

import com.shared.models.Route;

import java.util.Observable;

import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.interfaces.IMapPresenter;
import thePollerExpress.views.game.interfaces.IMapView;

public class MapPresenter implements IMapPresenter
{
    IMapView view;
    ClientData CD;
    Observable observing[];
    public MapPresenter(IMapView view)
    {
        this.view = view;
        CD = ClientData.getInstance();
        observing = new Observable[CD.getGame().getMap().getRoutes().size()];
        int i = 0;
        for (Observable o: CD.getGame().getMap().getRoutes() )
        {
            o.addObserver(this);
            observing[i] = o;
            i += 1;
        }

    }
    @Override
    public void update(Observable observable, Object o)
    {
        view.claimRoute();
    }
    @Override
    public void onDestroy()
    {
        for (Observable o: observing)
        {
            o.deleteObserver(this);
        }
    }
}
