package thePollerExpress.presenters.game;

import com.shared.models.TrainCard;

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
    }
    @Override
    public void update(Observable observable, Object o)
    {
        for(int i = 0; i < 5; ++i)
        {
            view.update(i, CD.getGame().getVisibleCards().get(i));
        }
    }
}
