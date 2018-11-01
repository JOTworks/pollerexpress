package thePollerExpress.views.game.interfaces;

import com.shared.models.cardsHandsDecks.DestinationCard;

import java.util.List;

import thePollerExpress.views.IPollerExpressView;

public interface IDestinationSelectionView extends IPollerExpressView {

    public void renderCards(List<DestinationCard> cards);

}
