package cs340.pollerexpress;

import com.pollerexpress.models.Game;
import com.pollerexpress.models.User;

class ClientData {
    private static final ClientData ourInstance = new ClientData();

    static ClientData getInstance() {
        return ourInstance;
    }

    private ClientData() {
    }

    private User user;
    private Game game;



}
