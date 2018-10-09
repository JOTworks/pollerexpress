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
        for(int i = 0; i<infoList.size(); i++){
            if(infoList.get(i).getId()==gameInfo.getId()) {
                if(infoList.get(i).getId()==CD.getGame().getId()){
                    System.out.println("!!!you deleated the game im in");
                    return false;
                }
            }
            infoList.remove(gameInfo);
            return true;
        }
        System.out.println("!!!you tried to deate a game that doesnt exist");
        return false;
    }

    public boolean startGame(GameInfo gameInfo){
        ArrayList<GameInfo> infoList = CD.getGameInfoList();

        for(int i = 0; i<infoList.size(); i++){
            if(infoList.get(i).getId()==gameInfo.getId()) {
                return true;
            }
        }
        return false;
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
        System.out.println("!!!yo im not in the game you tryin to make me join");
        return false;
    }

    public boolean joinGame(Player player, GameInfo info){
        ArrayList<GameInfo> infoList = CD.getGameInfoList();

        for(int i = 0; i<infoList.size(); i++){
            if(infoList.get(i).getId()==info.getId()) {
                ClientData.getInstance().getGameInfoList().get(i).addPlayer();

                //if its your game
                if (infoList.get(i).getId() == CD.getGame().getId()) {
                    CD.getGame().addPlayer(player);
                }
                return true;
            }
        }
        System.out.println("!!!you tried to have that dude join a game that didnt exist");
        return false;
    }

    public boolean leaveGame(Player player, GameInfo info){
        ArrayList<GameInfo> infoList = CD.getGameInfoList();
        for(int i = 0; i<infoList.size(); i++){
            if(infoList.get(i).getId()==info.getId()) {
                ClientData.getInstance().getGameInfoList().get(i).removePlayer();

                //if its your game
                if (infoList.get(i).getId() == CD.getGame().getId()) {
                    System.out.println("!!!yo this is my game, you used the wrong leaveGame command");
                }
                return true;
            }
        }
        return false;
    }
    public boolean leaveGame(GameInfo info){
        CD.setGame(null);
        return true;
    }


}
