package thePollerExpress.presenters.game;

import android.util.Log;

import com.shared.models.Game;
import com.shared.models.Player;

import java.util.Observable;
import java.util.Observer;

import thePollerExpress.models.ClientData;
import thePollerExpress.views.game.interfaces.IPlayerView;

public class PlayerPresenter implements IPlayerPresenter, Observer {
    IPlayerView playerView;
    ClientData clientData;
    String playerName;

    public PlayerPresenter(IPlayerView playerView, String playerName) {
        this.playerView = playerView;
        this.playerName = playerName;
        clientData = ClientData.getInstance();
        clientData.getGame().getPlayer(playerName);
    }

    public Player getPlayer() {
        //
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        if( !(arg instanceof Player) ) return;
        Player p = (Player) arg;
        Log.d("LobbyPresenter", p.name);
        Game game = clientData.getGame();
        int dex = game.getPlayers().indexOf(p);
        Log.d("LobbyPresenter", String.format("%d index of player %d", dex, game.getNumPlayers()));
        if(game.currentTurn.equals(playerName)) {
            playerView.isTurn();
        } else {
            playerView.isNotTurn();
        }
    }

    //
}
