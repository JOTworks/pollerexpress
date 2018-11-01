package thePollerExpress.services;

import android.util.Log;

import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.interfaces.ISetupService;
import com.shared.models.Player;
import com.shared.models.states.GameState;

import java.util.ArrayList;
import java.util.List;

import thePollerExpress.models.ClientData;

class ClientSetupService implements ISetupService {
    private static final ClientSetupService ourInstance = new ClientSetupService();

    static ClientData CD = ClientData.getInstance();
    static ClientSetupService getInstance() {
        return ourInstance;
    }

    private ClientSetupService() {
        CD = ClientData.getInstance();
    }


    public static boolean createGame(GameInfo gameInfo)
    {
        ClientData.getInstance().addGame(gameInfo);
        return true;
    }

    public static boolean joinGame(Player player, GameInfo info) {
        ArrayList<GameInfo> infoList = CD.getGameInfoList();

        //if it is you being joined
        if (player.getName().equals(CD.getUser().getName())) {
            CD.getUser().setGameID(info.getId());
        }

            for (int i = 0; i < infoList.size(); i++) {
                if (infoList.get(i).getId().equals(info.getId())) {
                    //if its your game
                    if (CD.getGame() != null && infoList.get(i).getId().equals(CD.getGame().getId())) {
                        if (!(CD.getGame().hasPlayer(player))) {
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

        public static boolean loadGame (Game game)
        {
            CD.setGame(game);
            return true;
        }




//    public static boolean startGame(GameInfo gameInfo){
//        ArrayList<GameInfo> infoList = CD.getGameInfoList();
//
//        for(int i = 0; i<infoList.size(); i++){
//            if(infoList.get(i).getRotation()==gameInfo.getRotation()) {
//                return true;
//            }
//        }
//        return false;
//    }
        /**************not requiered for phase 1*******************/
        public static boolean deleteGame (GameInfo gameInfo){
            ArrayList<GameInfo> infoList = CD.getGameInfoList();
            for (int i = 0; i < infoList.size(); i++) {
                if (infoList.get(i).getId() == gameInfo.getId()) {
                    if (infoList.get(i).getId() == CD.getGame().getId()) {
                        System.out.println("!!!you deleated the game im in");
                        return false;
                    }
                }
                infoList.remove(gameInfo);
                return true;
            }
            System.out.println("!!!you tried to delete a game that doesnt exist");
            return false;
        }
        public boolean leaveGame (Player player, GameInfo info){
            ArrayList<GameInfo> infoList = CD.getGameInfoList();
            for (int i = 0; i < infoList.size(); i++) {
                if (infoList.get(i).getId() == info.getId()) {
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
        public boolean leaveGame (GameInfo info){
            CD.setGame(null);
            return true;
        }

    public static Boolean setPlayerColors(ArrayList<Player> players) {
        for (Player player : players) {
            CD.getGame().getPlayer(player)
                    .setColor(player.getColor());
        }
        return true;
    }


    }
