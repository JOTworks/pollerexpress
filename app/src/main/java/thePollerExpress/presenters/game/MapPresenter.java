package thePollerExpress.presenters.game;

import com.shared.models.Route;

import java.util.Observable;

import thePollerExpress.presenters.game.interfaces.IMapPresenter;
import thePollerExpress.views.game.interfaces.IMapView;

public class MapPresenter implements IMapPresenter
{
    IMapView view;
    public MapPresenter(IMapView view)
    {
        this.view = view;
    }
    @Override
    public void update(Observable observable, Object o)
    {
        //view.claimRoute((Route) o);
    }
}
