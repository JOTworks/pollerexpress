package thePollerExpress.presenters.game.interfaces;

import com.shared.models.Map;
import com.shared.models.Route;
import com.shared.models.cardsHandsDecks.TrainCard;

import java.util.List;
import java.util.Observer;

public interface IMapPresenter extends Observer
{
    void onDestroy();
    Map getMap();
    void claimRoute(Route r);
    void claim(List<TrainCard> cards);
}
