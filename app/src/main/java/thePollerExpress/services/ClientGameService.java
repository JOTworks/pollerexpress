package thePollerExpress.services;

import android.util.Log;

import com.shared.models.Chat;
import com.shared.models.DestinationCard;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;

import java.util.ArrayList;
import java.util.List;

import thePollerExpress.models.ClientData;

class ClientGameService {

    private static final ClientGameService ourInstance = new ClientGameService();

    static ClientData CD = ClientData.getInstance();

    static ClientGameService getInstance() {
        return ourInstance;
    }

    private ClientGameService() {
        CD = ClientData.getInstance();
    }

    public static boolean addDestCardsToHand(List<DestinationCard> destinationCards)
    {
        for (DestinationCard card : destinationCards) {
            CD.addDestCardToHand(card);
        }
        return true;
    }

    public static boolean removeDestCardFromHand(List<DestinationCard> destinationCards)
    {
        for (DestinationCard card : destinationCards) {
            CD.addDestCardToHand(card);
        }
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

}
