package thePollerExpress.presenters.game.interfaces;

import com.shared.models.cardsHandsDecks.DestinationCard;

import java.util.List;

public interface IDestinationSelectionPresenter {

    void discardDestCard(DestinationCard card);

    void discardButtonPressed(List<Boolean> selected);
}
