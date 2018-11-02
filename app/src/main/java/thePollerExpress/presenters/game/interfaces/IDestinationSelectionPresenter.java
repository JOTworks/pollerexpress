package thePollerExpress.presenters.game.interfaces;

import com.shared.models.cardsHandsDecks.DestinationCard;

import java.util.List;

public interface IDestinationSelectionPresenter {

    void discardDestCards(List<DestinationCard> cards);

    void discardButtonPressed(List<Boolean> selected);

    List<DestinationCard> get();
}
