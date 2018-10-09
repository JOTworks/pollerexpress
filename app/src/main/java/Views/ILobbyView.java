package Views;

import android.view.View;

import com.pollerexpress.models.Game;
import com.pollerexpress.models.Player;

public interface ILobbyView {

//    void attachPresenter(IGamePresenter gamePresenter);
//    void setPlayers(player[] players);
//    void setPlayerColor(Player player,Color color);
//    void changeView(View newView);

    void changeToGameView();

    void playerJoined(Player p, int i);

    void displayMessage(String message);

    void changeToSetupGameView();
}
