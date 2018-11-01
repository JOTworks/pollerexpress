package thePollerExpress.presenters.game.interfaces;

import com.shared.models.cardsHandsDecks.DestinationCard;

public interface IDestinationSelectionPresenter {

    void discardDestCard(DestinationCard card);

    void discardButtonPressed();
}
