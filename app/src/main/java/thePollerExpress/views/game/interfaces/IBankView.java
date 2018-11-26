package thePollerExpress.views.game.interfaces;

import com.shared.models.cardsHandsDecks.TrainCard;

import thePollerExpress.views.IPollerExpressView;

public interface IBankView extends IPollerExpressView
{
    public void update(int i, TrainCard card);
    void update();
}
