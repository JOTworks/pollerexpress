package thePollerExpress.presenters.game.interfaces;

import com.shared.models.Player;

import java.util.Observer;

public interface IPlayerPresenter extends Observer
{
    public Player getPlayer();
}
