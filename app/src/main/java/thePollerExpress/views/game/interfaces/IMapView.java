package thePollerExpress.views.game.interfaces;

import com.shared.models.Route;
import com.shared.models.cardsHandsDecks.TrainCard;

import java.util.List;

import thePollerExpress.views.IPollerExpressView;

public interface IMapView extends IPollerExpressView
{
    void redrawMap();

    /**
     * shows the user a list of cards to choose from to claim the route.
     * @param permutations
     */
    void showPopup(List<List<TrainCard>> permutations);
}
