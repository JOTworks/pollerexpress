package cs340.pollerexpress;

import com.pollerexpress.models.Game;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.ISetupService;

import java.util.ArrayList;

class ClientSetupService implements ISetupService {
    private static final ClientSetupService ourInstance = new ClientSetupService();

    ClientData CD;
    static ClientSetupService getInstance() {
        return ourInstance;
    }

    private ClientSetupService() {
        CD = ClientData.getInstance();
    }

    public boolean addGame(GameInfo gameInfo){
        ArrayList<GameInfo> infoList = CD.getGameInfoList();
        infoList.add(gameInfo);
        return true;
    }
    public boolean DeleteGame(GameInfo gameInfo){
        ArrayList<GameInfo> infoList = CD.getGameInfoList();
        infoList.remove(gameInfo);
        return true;
    }
    public boolean joinGame(Game game){
        ClientData.getInstance().setGame(game);
        return true;
    }
}
