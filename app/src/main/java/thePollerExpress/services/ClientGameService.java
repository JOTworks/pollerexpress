package thePollerExpress.services;

import com.shared.models.Chat;
import com.shared.models.GameInfo;

import thePollerExpress.models.ClientData;

public class ClientGameService {

    private static final ClientGameService ourInstance = new ClientGameService();

    static ClientData CD = ClientData.getInstance();
    static ClientGameService getInstance() {
        return ourInstance;
    }

    private ClientGameService() {
        CD = ClientData.getInstance();
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
