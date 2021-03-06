package thePollerExpress.services;

import android.util.Log;

import com.shared.exceptions.NotImplementedException;
import com.shared.models.Chat;


import com.shared.models.User;
import com.shared.models.EndGameResult;
import com.shared.models.HistoryItem;
import com.shared.models.cardsHandsDecks.DestinationCard;

import com.shared.models.User;
import com.shared.models.EndGameResult;
import com.shared.models.HistoryItem;


import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.Route;
import com.shared.models.cardsHandsDecks.TrainCard;
import com.shared.models.states.GameState;
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


    public static boolean endGame()
    {
        CD.getGame().setGameState(new GameState());

        return true;
    }

    public static boolean sendGameHistory(HistoryItem historyItem){
//        chat(new Chat(historyItem.getaction(),historyItem.getTimestamp(),historyItem.getactionSender()), CD.getGame().getGameInfo());
        CD.getGame().getGameHistory().addHistoryItem(historyItem);
        return true;

    }
    public static boolean chat(Chat chat, GameInfo gameInfo)
    {
        //we dont need the gameInfo, becasue chats should only be sent to poeple in the game? but i guess i could check here too
        if(gameInfo.equals(CD.getGame()))
        {
            return true;
        }
        CD.getGame().addChat(chat);
        return true;
    }


    public static boolean claimRoute(Player p, Route r, List<TrainCard> cards)
    {
        Log.d("ClaimRoute", p.getName() + " " +r.toString());
        Player real = CD.getGame().getPlayer(p);
        CD.getGame().getMap().claimRoute(real, r);

        User user = CD.getUser();
        if(p.equals(user))
        {

            for(TrainCard card: cards)
            {
                user.getTrainCardHand().removeFromHand(card);
            }
        }
        real.setTrainCount(p.getTrainCount());
        real.setPoints(real.getPoints() + r.getRouteValue());
        return true;
    }

    public static boolean setGameState(GameState gameState) {
        System.out.println("setting gameState");
        System.out.println(gameState.getState().toString());
        CD.getGame().setGameState(gameState);
        return true;
    }

    public static boolean endGame(EndGameResult gameResult) {
        CD.setGameResult(gameResult);
        return true;
    }

    //----------------------------Unwritten methods-------------------------------------------------

//    public static boolean updateHistory(Player p, HistoryItem historyItem) {
//        CD.getGame().getGameHistory().addHistoryItem(historyItem);
//        return true;
//    }
}
