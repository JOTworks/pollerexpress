package presenter;

import com.pollerexpress.models.Player;

/**
 * Defines all the methods the
 * LobbyView would need to be able to call
 * on the LobbyPresenter
 */
public interface ILobbyPresenter {

    void startButtonPressed();

    void onBackArrowPressed();
}
