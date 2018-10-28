package thePollerExpress.utilities;

import com.shared.models.interfaces.IDatabaseFacade;

import thePollerExpress.views.IPollerExpressView;

public interface IViewFactory {

    IPollerExpressView createLobbyView();

}
