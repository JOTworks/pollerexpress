package thePollerExpress.utilities;

import com.shared.models.interfaces.IDatabaseFacade;

import thePollerExpress.views.IPollerExpressView;

public class ViewFactory
{
    static IViewFactory _ivf = new RealViewFactory();//default db

    public static void setFactory(IViewFactory idf)
    {
        _ivf = idf;
    }

    public static IPollerExpressView createLobbyView()
    {
        return _ivf.createLobbyView();
    }
}
