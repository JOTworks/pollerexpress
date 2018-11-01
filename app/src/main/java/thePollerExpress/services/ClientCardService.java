package thePollerExpress.services;

import android.util.Log;

import com.shared.models.Chat;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.Route;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.cardsHandsDecks.TrainCard;
import com.shared.models.states.GameState;

import java.util.ArrayList;
import java.util.List;

import thePollerExpress.models.ClientData;

public class ClientCardService {

    private static final ClientCardService ourInstance = new ClientCardService();

    static ClientData CD = ClientData.getInstance();

    static ClientCardService getInstance() {
        return ourInstance;
    }

    private ClientCardService()
    {
        CD = ClientData.getInstance();
    }


    //----------------------------------Train Card methods------------------------------------------

    public static boolean drawFirstTrainCards(ArrayList<TrainCard> trainCards)
    {
            CD.getUser().getTrainCardHand().setTrainCards(trainCards);
        return true;
    }

    /**
     * For updating the number of cards a player has
     * @param player
     * @param numberOfCards
     * @return
     */
    public static boolean drawFirstTrainCards(Player player, Integer numberOfCards)
    {
        CD.getGame().drawTrainCards(player, numberOfCards);
        return true;
    }

    public static boolean drawTrainCard(TrainCard trainCard)
    {
        CD.getUser().getTrainCardHand().addToHand(trainCard);
        return true;
    }

    public static boolean drawTrainCard(Player player)
    {
        CD.getGame().drawTrainCard(player);
        return true;
    }


    //------------------------------Destination Card methods----------------------------------------


    /**
     * Skips drawing the cards if the user is not equal to the player in question.
     * This avoids sending all the players extra destination cards
     *
     * @param player
     * @param destinationCards
     * @return
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
     *
     * @param player
     * @param numberOfCards
     * @return
     */
    public static boolean drawDestinationCards(Player player, Integer numberOfCards)
    {
        Player real = CD.getGame().getPlayer(player);
        CD.getGame().drawDestinationCards(real, numberOfCards);
        return true;
    }


    public static boolean discardDestinationCards(Player player, List<DestinationCard> destinationCards)
    {
        for (DestinationCard card : destinationCards) {
            CD.removeDestCardFromOptions(card);
        }

        //TODO: take adding to the hand logic out and put it in another method. Violating single responsibility and have a misleading method name

        for (DestinationCard card : CD.getUser().getDestCardOptions().getDestinationCards())
            CD.addDestCardToHand(card);
        return true;
    }

    //todo: make it discard for players

    public static boolean discardDestinationCards(Player player, Integer numberOfCards)
    {
        Player real = CD.getGame().getPlayer(player);
        CD.getGame().discardDestinationCards(real, numberOfCards);
        return true;
    }


    public static boolean drawVisibleCard(Player p, TrainCard card, Integer drawsLeft, TrainCard visible[])
    {
        if(p.equals(CD.getUser()))
        {
            CD.getUser().getTrainCardHand().addToHand(card);
        }
        CD.getGame().drawTrainCard(p);
        CD.getGame().getVisibleCards().set(visible);
        return true;
    }
}
