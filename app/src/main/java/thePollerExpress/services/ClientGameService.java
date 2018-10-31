package thePollerExpress.services;

import android.util.Log;

import com.shared.models.Chat;
import com.shared.models.DestinationCard;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.Route;
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
    public static boolean startGame() {
        CD.getGame().setGameState(new GameState());
        return false;
    }

    public static boolean drawDestinationCards(Player player, List<DestinationCard> destinationCards)
    {
        if(!CD.getUser().equals(player)) return false;
        for (DestinationCard card : destinationCards) {
            CD.addDestCardToHand(card);
        }
        return true;
    }
    public static boolean drawDestinationCards(Player player, Integer cardNumber)
    {
        Player real = CD.getGame().getPlayer(player);
        real.setDestinationCardCount(real.getDestinationDiscardCount() + cardNumber.intValue());
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

    public static boolean claimRoute(Player p, int route)
    {
        claimRoute(p, (Route) CD.getGame().getMap().getRoutes().toArray()[route ]);
        return true;
    }
    public static boolean claimRoute(Player p, Route r)
    {
        CD.getGame().getMap().claimRoute(p, r);
        return true;
    }
}
