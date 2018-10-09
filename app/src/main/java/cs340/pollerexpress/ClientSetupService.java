package cs340.pollerexpress;

import com.pollerexpress.models.Game;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.ISetupService;
import com.pollerexpress.models.Player;

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


    public boolean createGame(GameInfo gameInfo){
        ArrayList<GameInfo> infoList = CD.getGameInfoList();
        infoList.add(gameInfo);
        return true;
    }
    public boolean deleteGame(GameInfo gameInfo){
        ArrayList<GameInfo> infoList = CD.getGameInfoList();
        infoList.remove(gameInfo);
        return true;
    }
    public boolean joinGame(Game game){
        ArrayList<GameInfo> infoList = CD.getGameInfoList();

        for(int i = 0; i<infoList.size(); i++){
            if(infoList.get(i).getId()==game.getId()) {
                ClientData.getInstance().getGameInfoList().get(i).addPlayer();
                ClientData.getInstance().setGame(game);
                return true;
            }
        }
        return false;
    }

    public boolean joinGame(Player player, GameInfo info){
         //for when its not you joining
        return false;
    }
}
