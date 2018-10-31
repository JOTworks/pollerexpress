package thePollerExpress.presenters.game.interfaces;

import com.shared.models.DestinationCard;

public interface IDestinationSelectionPresenter {

    void discardDestCard(DestinationCard card);

    /*
    * "The following items should be demonstrated:
    * . add/remove player destination cards for this player
    * . update the number of destination cards for opponent players
    * . update the number of cards in destination card deck" -- specs
    *
    * "Each players receives 3 destination cards to start the game: 10 points
    * Each player returns up to 1 destination card to the server: 10 points"
    * -- implementation rubric */

}
