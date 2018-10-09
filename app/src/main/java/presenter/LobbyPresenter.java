package presenter;

import com.pollerexpress.models.Player;

import java.util.Observable;
import java.util.Observer;

import Views.ILobbyView;
import Views.LobbyFragment;

public class LobbyPresenter implements ILobbyPresenter, Observer {

    ILobbyView lobbyView = new LobbyFragment();

    @Override
    public void startButtonPressed()
    {

    }

    @Override
    public void colorPicked() {

    }

    @Override
    public void observeColorChanged(Player newColor) {

    }

    @Override
    public void leaveGamePressed() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
