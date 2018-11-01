package thePollerExpress.presenters.game;

import com.shared.models.Game;
import com.shared.models.cardsHandsDecks.VisibleCards;

import java.util.Observable;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.interfaces.IBankPresenter;
import thePollerExpress.views.game.interfaces.IBankView;

public class BankPresenter implements IBankPresenter
{

    IBankView view;
    ClientData CD;

    public BankPresenter(IBankView view)
    {
        this.view = view;
        CD = ClientData.getInstance();
        CD.getGame().getVisibleCards().addObserver(this);
        CD.getGame().addObserver(this);
    }
    @Override
    public void update(Observable observable, Object o)
    {
        if(observable instanceof VisibleCards)
        {
            for (int i = 0; i < 5; ++i)
            {
                view.update(i, CD.getGame().getVisibleCards().get(i));
            }
        }
        else if(observable instanceof Game)
        {
            view.update();
        }
        else if(o instanceof String)
        {
            view.update();
        }
    }

    @Override
    public int getDestinationDeckSize()
    {
        return CD.getGame().DestinationCardDeck;
    }

    @Override
    public int getTrainDeckSize()
    {
        return CD.getGame().TrainCardDeck;
    }
    public void onDestroy()
    {
        try
        {
            CD.getGame().getVisibleCards().deleteObserver(this);
            CD.getGame().deleteObserver(this);
        }
        catch (Exception e)
        {
            //TODO log the exception.
        }
    }
}
