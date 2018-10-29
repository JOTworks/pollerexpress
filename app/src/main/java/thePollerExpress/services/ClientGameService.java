package thePollerExpress.services;

import android.util.Log;

import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.interfaces.ISetupService;

import java.util.ArrayList;

import thePollerExpress.models.ClientData;

class ClientGameService implements ISetupService {
    private static final ClientGameService ourInstance = new ClientGameService();

    static ClientData CD = ClientData.getInstance();
    static ClientGameService getInstance() {
        return ourInstance;
    }

    private ClientGameService() {
        CD = ClientData.getInstance();
    }


    public static boolean addDestCardsToHand()
    {
        CD.);
        return true;
    }

    public static boolean joinGame(Player player, GameInfo info){
        ArrayList<GameInfo> infoList = CD.getGameInfoList();

        for(int i = 0; i<infoList.size(); i++){
            if( infoList.get(i).getId().equals(info.getId() ) )
            {
                //if its your game
                if (CD.getGame() != null && infoList.get(i).getId().equals(CD.getGame().getId())) {
                    if(!( CD.getGame().hasPlayer(player) ) )
                    {
                        Log.d("joinGame", "someone joined my game!");
                        CD.addPlayerToGame(player);
                    }

                }
                CD.addPlayerToGameInfo(i);
                return true;
            }
        }
        System.out.println("!!!you tried to have that dude join a game that didnt exist");
        return false;
    }

    public static boolean loadGame(Game game)
    {
        CD.setGame(game);
        return true;
    }

    public static boolean startGame(GameInfo gameInfo){
        ArrayList<GameInfo> infoList = CD.getGameInfoList();

        for(int i = 0; i<infoList.size(); i++){
            if(infoList.get(i).getId()==gameInfo.getId()) {
                return true;
            }
        }
        return false;
    }

}
