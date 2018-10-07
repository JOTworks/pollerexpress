package cs340.pollerexpress;

import com.pollerexpress.models.Game;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.ISetupService;

class ClientSetupService implements ISetupService {
    private static final ClientSetupService ourInstance = new ClientSetupService();

    static ClientSetupService getInstance() {
        return ourInstance;
    }

    private ClientSetupService() {
    }

    public boolean addGame(GameInfo gameInfo){
        return true;
    }
    public boolean DeleateGame(GameInfo gameInfo){
        return true;
    }
    public boolean joinGame(Game game){
        ClientData.getInstance().setGame(game);
        return true;
    }
}
