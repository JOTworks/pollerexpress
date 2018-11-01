package thePollerExpress.presenters.setup;

import com.shared.models.Game;

import java.util.Observer;

/**
 * Abby
 * Defines all the methods the
 * LobbyView would need to be able to call
 * on the LobbyPresenter
 */
public interface ILobbyPresenter extends Observer
{

    void startButtonPressed();

    void onBackArrowPressed();

    Game getGame();
    void onDestroy();
}
