package thePollerExpress.services;

import android.util.Log;

import com.shared.exceptions.NotImplementedException;
import com.shared.models.Chat;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.Route;
import com.shared.models.cardsHandsDecks.TrainCard;
import com.shared.models.states.GameState;
import java.util.List;

import thePollerExpress.models.ClientData;
import static com.shared.models.states.GameState.State.*;

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
        //only a setgamestatecommand from the server should ever change the game state hard coded rn but its a problem - jack
        CD.getGame().setGameState(new GameState(null, WAITING_FOR_ONE_PLAYER));
        CD.getGame().getVisibleCards().set(cards);

        return true;
    }

    public static boolean endGame()
    {
        CD.getGame().setGameState(new GameState());

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

    public static boolean setGameState(GameState gameState) {
        CD.getGame().setGameState(gameState);
        return true;
    }

    //----------------------------Unwritten methods-------------------------------------------------


    public static boolean updateHistory(Player p /*aHistoryObject goes here*/) {
        throw new NotImplementedException("ClientGameService.updateHistory()");
    }
}
