package presenter;

import com.pollerexpress.models.Game;
import com.pollerexpress.models.Player;

import java.util.Observable;
import java.util.Observer;

/**
 * Defines all the methods the
 * LobbyView would need to be able to call
 * on the LobbyPresenter
 */
public interface ILobbyPresenter extends Observer
{

    void startButtonPressed();

    void onBackArrowPressed();

    Game getGame();
}
