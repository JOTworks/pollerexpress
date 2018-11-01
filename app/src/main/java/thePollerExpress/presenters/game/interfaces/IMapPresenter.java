package thePollerExpress.presenters.game.interfaces;

import com.shared.models.Map;

import java.util.Observer;

public interface IMapPresenter extends Observer
{
    void onDestroy();
    Map getMap();
}
