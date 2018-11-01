package thePollerExpress.views.game.interfaces;

import com.shared.models.cardsHandsDecks.TrainCard;

public interface IBankView //extends IPollerExpressView
{
    public void update(int i, TrainCard card);
    void update();
}
