package thePollerExpress.presenters.game;

import com.shared.models.Player;

import java.util.Observable;
import java.util.Observer;

import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.interfaces.IPlayerPresenter;
import thePollerExpress.views.game.interfaces.IPlayerView;

public class PlayerPresenter implements IPlayerPresenter, Observer {
    IPlayerView playerView;
    ClientData clientData;
    public String playerName;

    public PlayerPresenter(IPlayerView playerView, String playerName) {
        this.playerView = playerView;
        this.playerName = playerName;
        clientData = ClientData.getInstance();
        clientData.getGame().getPlayer(playerName).addObserver(this);
        clientData.getGame().addObserver(this);
    }

    public Player getPlayer() {

        return ClientData.getInstance().getGame().getPlayer(playerName);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof  Player)
        {
            playerView.renderPlayer(clientData.getGame().getPlayer(playerName));
        }

       // if(clientData.getGame().currentTurn.equals(playerName)){
        //    playerView.isTurn();
        //}else{
        //    playerView.isNotTurn();
        //}
    }

}
