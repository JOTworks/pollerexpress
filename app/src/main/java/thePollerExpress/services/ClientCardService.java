package thePollerExpress.services;

import com.shared.models.Player;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.cardsHandsDecks.TrainCard;

import java.util.ArrayList;
import java.util.List;

import thePollerExpress.models.ClientData;

/**
 * Handles all card related actions performed on the ClientDataModel
 *
 * @invariant 2 < numberOfCards > 5
 * @invariant trainCards.size() = 4
 * @invariant DestinationCards.size() = 3
 * @invariant CD.getGame().getPlayers().contains(player) = true
 */
public class ClientCardService {

    /**
     * The ClientData model used for all clientSide data storage
     */
    static ClientData CD = ClientData.getInstance();

    //----------------------------------Train Card methods------------------------------------------

    /**
     *  set the face up cards so all players can view them
     * @return true if the state was changed and false otherwise
     */
    public static boolean setVisibleCards(TrainCard[] cards)
    {
        CD.getGame().getVisibleCards().set(cards);

        return true;
    }

    /**
     * Used to assign the first trainCards a player receives at the start of a game
     *
     * @post one or more train cards are added to the hand of the player drawing cards
     *
     * @param trainCards
     * @return true upon successful execution
     */
    public static boolean drawFirstTrainCards(ArrayList<TrainCard> trainCards)
    {
            CD.getUser().getTrainCardHand().setTrainCards(trainCards);
        return true;
    }

    /**
     * For updating the number of trainCards a player receives at the start of the game
     *
     * @post adds 'numberOfCards' to the integer that displays the number of train cards in a players hand
     * @param player
     * @param numberOfCards
     * @return true upon successful execution
     */
    public static boolean drawFirstTrainCards(Player player, Integer numberOfCards)
    {
        CD.getGame().drawTrainCards(player, numberOfCards);
        return true;
    }

    /**
     * add a single trainCard to the hand
     *
     * @post adds a trainCard to the hand of the user
     * @param trainCard
     * @return true upon successful execution
     */
    public static boolean drawTrainCard(TrainCard trainCard)
    {
        CD.getUser().getTrainCardHand().addToHand(trainCard);
        return true;
    }

    /**
     * add 1 to the number of traincards displayed for this player
     *
     * @post adds one to the integer that displays the number of train cards in a players hand
     * @param player
     * @return true upon successful execution
     */
    public static boolean drawTrainCard(Player player)
    {
        CD.getGame().drawTrainCard(player);
        return true;
    }


    //------------------------------Destination Card methods----------------------------------------
    /**
     * add destination cards to the options hand which a user can pick from to add to their
     * destination cards hand
     *
     * Skips drawing the cards if the user is not the player in question.
     * This avoids sending all the players extra destination cards
     *
     * @post Add a destination card to a player's hand
     * @param player
     * @param destinationCards
     * @return false if the player parameter is not the same as the user. true otherwise
     */
    public static boolean drawDestinationCards(Player player, ArrayList<DestinationCard> destinationCards)
    {
        if(!CD.getUser().equals(player)) return false;
        for (DestinationCard card : destinationCards) {
            CD.addDestCardToOptions(card);
        }
        return true;
    }

    /**
     * increments for all players the count of the number of destination cards a player has after drawing
     *
     * @post add one to the integer that displays the number of destination cards a player has
     * @param player
     * @param numberOfCards
     * @return true upon successful execution
     */
    public static boolean drawDestinationCards(Player player, Integer numberOfCards)
    {
        Player real = CD.getGame().getPlayer(player);
        CD.getGame().drawDestinationCards(real, numberOfCards);
        return true;
    }

    /**
     * Note for refactor: take adding to the hand logic out and put it in another method so as to
     * violate the singleResponsibility rule
     *
     * discards a destination card from the options and adds the remaining options to the users
     * destination cards hand
     *
     * @post removes a specific destination card from the users hand
     * @param player
     * @param destinationCards
     * @return true upon successful execution
     */
    public static boolean discardDestinationCards(Player player, List<DestinationCard> destinationCards)
    {
        for (DestinationCard card : destinationCards) {
            CD.removeDestCardFromOptions(card);
        }

        for (DestinationCard card : CD.getUser().getDestCardOptions().getDestinationCards()) {
            CD.addDestCardToHand(card);
        }

        CD.emptyDestOptions();
        return true;
    }

    /**
     * decrements for all players the count of the number of destination cards a player
     * has after discarding
     *
     * @post decrement by 'numberOfCards' the integer that displays the number of destination cards a player has
     * @param player
     * @param numberOfCards
     * @return true upon successful execution
     */
    public static boolean discardDestinationCards(Player player, Integer numberOfCards)
    {
        Player real = CD.getGame().getPlayer(player);
        CD.getGame().discardDestinationCards(real, numberOfCards);
        return true;
    }

    public static boolean shuffleDestinationDeck(Integer newDeckSize)
    {
        CD.getGame().setDestinationDeckSize(newDeckSize);
        return true;
    }

    /**
     * draws a "face up" traincard.
     * TODO: add logic to disallow draw if drawsLeft < 1
     *
     * @pre visible.size() = 5
     * @pre if 'drawsLeft' == 1 then card is not a locamotive
     * @post add a traincard to the users hand (if the user is the same as the player that comes in
     * as a parameter), add 1 to the integer displaying the number of Train cards the user has, and
     * update the "face up" deck cards.
     *
     * @param p the player who is drawing the card
     * @param card a trainCard
     * @param visible an array of the traincards that are currently "face up" on the board
     * @return true upon successful execution
     */
    public static boolean drawVisibleCard(Player p, TrainCard card, TrainCard visible[])
    {
        if(p.equals(CD.getUser()))
        {
            CD.getUser().getTrainCardHand().addToHand(card);
        }
        CD.getGame().drawTrainCard(p);
        CD.getGame().getVisibleCards().set(visible);
        return true;
    }

    public static boolean resetVisible(TrainCard visible[]) {
        CD.getGame().drawTrainCards(visible.length);
        CD.getGame().getVisibleCards().set(visible);
        return true;
    }
}
