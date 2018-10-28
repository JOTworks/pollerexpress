package thePollerExpress.utilities;

import com.shared.models.interfaces.IDatabaseFacade;

import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.setup.LobbyFragment;

public class RealViewFactory implements IViewFactory
{
    @Override
    public IPollerExpressView createLobbyView()
    {
        return new LobbyFragment();
    }
}
