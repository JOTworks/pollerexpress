package thePollerExpress.views.game.interfaces;

import com.shared.models.Player;

public interface IPlayerView {
    public void renderPlayer(Player p);
    public void isTurn();
    public void isNotTurn();
}
