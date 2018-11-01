package thePollerExpress.presenters.game;

import com.shared.models.cardsHandsDecks.DestinationCard;

import java.util.List;

import thePollerExpress.models.ClientData;
import thePollerExpress.views.game.interfaces.IDestinationPresenter;
import thePollerExpress.views.game.interfaces.IDestinationView;

public class DestinationPresenter implements IDestinationPresenter
{
    IDestinationView view;
    public DestinationPresenter(IDestinationView view)
    {
        this.view = view;
    }
    @Override
    public List<DestinationCard> get()
    {
        return ClientData.getInstance().getUser().getDestCardHand().getDestinationCards();
    }
}
