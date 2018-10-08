package presenter;

import com.pollerexpress.models.Player;

/**
 * Defines all the methods the
 * LobbyView would need to be able to call
 * on the LobbyPresenter
 */
public interface ILobbyPresenter {

    public void startButtonPressed();

    public void colorPicked();

    public void observeColorChanged(Player newColor);

    public void leaveGamePressed();
}
