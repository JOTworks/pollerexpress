package thePollerExpress.utilities;

import thePollerExpress.presenters.game.MapPresenter;
import thePollerExpress.presenters.game.interfaces.IMapPresenter;
import thePollerExpress.views.game.interfaces.IMapView;

public class PresenterFactory
{
    public static IMapPresenter createIMapPresenter(IMapView view)
    {
        return new MapPresenter(view);//TODO mirror the behavior in View Factory
    }
}
