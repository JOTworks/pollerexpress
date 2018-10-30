package thePollerExpress.views.setup;

import thePollerExpress.views.IPollerExpressView;

public interface ICreateGameView extends IPollerExpressView
{

//    void enableCancelButton(Boolean yn);
//    void enableCreateButton(Boolean yn);
//    void highlightColor(Color color);
    void displayError(String errorMessage);
    void changeToSetupGameView();
    void changeToLobbyView();
}
