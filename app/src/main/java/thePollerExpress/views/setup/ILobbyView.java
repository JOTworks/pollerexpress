package thePollerExpress.views.setup;

import com.shared.models.Player;

import thePollerExpress.views.IPollerExpressView;

public interface ILobbyView extends IPollerExpressView
{

//    void attachPresenter(IGamePresenter gamePresenter);
//    void setPlayers(player[] players);
//    void setPlayerColor(Player player,Color color);
//    void changeView(View newView);

    void changeToGameView();

    void playerJoined(Player p);
    void playerLeft(int i);

    void displayMessage(String ntmessage);

    void changeToSetupGameView();
}
