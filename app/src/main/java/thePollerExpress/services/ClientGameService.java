package thePollerExpress.services;

import android.util.Log;

import com.shared.models.Chat;
import com.shared.models.DestinationCard;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.Route;
import com.shared.models.TrainCard;
import com.shared.models.VisibleCards;
import com.shared.models.states.GameState;

import java.util.ArrayList;
import java.util.List;

import thePollerExpress.models.ClientData;

public class ClientGameService {

    private static final ClientGameService ourInstance = new ClientGameService();

    static ClientData CD = ClientData.getInstance();

    static ClientGameService getInstance() {
        return ourInstance;
    }

    private ClientGameService()
    {
        CD = ClientData.getInstance();
    }

    /**
     *  Set the game state for the state you will have going into the game
     *  This will notify the observer and will cause every user to switch to the gameView
     * @return true if the state was changed and false otherwise
     */
    public static boolean startGame(TrainCard[] cards)
    {

        CD.getGame().getVisibleCards().set(cards);
        CD.getGame().setGameState(new GameState());
        return true;
    }

    /**
     *
     * @param player
     * @param destinationCards
     * @return
     */
    public static boolean drawDestinationCards(Player player, List<DestinationCard> destinationCards)
    {
        if(!CD.getUser().equals(player)) return false;
        for (DestinationCard card : destinationCards) {
            CD.addDestCardToHand(card);
        }
        return true;
    }

    /**
     *
     * @param player
     * @param cardNumber
     * @return
     */
    public static boolean drawDestinationCards(Player player, Integer cardNumber)
    {
        Player real = CD.getGame().getPlayer(player);
        CD.getGame().drawDestinationCards(player, cardNumber);
        return true;
    }


    public static boolean removeDestCardFromHand(Player player, List<DestinationCard> destinationCards)
    {
        for (DestinationCard card : destinationCards) {
            CD.addDestCardToHand(card);
        }
        return true;
    }

    //todo: make it discard for players


    public static boolean setAlertForStartGame() {
        //todo: implement
        //CD.getGame().
        return true;
    }


    public static boolean chat(Chat chat, GameInfo gameInfo)
    {
        //we dont need the gameinfo, becasue chats should only be sent to poeple in the game? but i guess i could check here too
        if(gameInfo.equals(CD.getGame()))
        {
            return true;
        }
        CD.getGame().addChat(chat);
        return true;
    }

    public static Route claimRoute(Player p, int route)
    {
        Route r = (Route)CD.getGame().getMap().getRoutes().toArray()[route];
        //claimRoute(p, r );
        return r;
    }
    public static boolean claimRoute(Player p, Route r)
    {
        Log.d("ClaimRoute", p.getName() + " " +r.toString());
        CD.getGame().getMap().claimRoute(p, r);
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
