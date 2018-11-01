package thePollerExpress.views.game.interfaces;

import com.shared.models.Player;

import thePollerExpress.views.IPollerExpressView;

public interface IPlayerView extends IPollerExpressView {
    public void renderPlayer(Player p);

}
