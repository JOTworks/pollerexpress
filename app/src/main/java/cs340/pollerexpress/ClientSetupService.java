package cs340.pollerexpress;

import com.pollerexpress.models.Game;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.ISetupService;

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
        GameInfo[] oldArray = CD.getGameInfoList();

        //define the new array
        GameInfo[] newArray = new GameInfo[oldArray.length + 1];

        //copy values into new array
        for(int i=0;i < oldArray.length;i++)
            newArray[i] = oldArray[i];

        //add new value to the new array
        newArray[newArray.length-1] = gameInfo;

        CD.setGameInfoList(newArray);

        return true;
    }
    public boolean DeleateGame(GameInfo gameInfo){

        GameInfo[] original = CD.getGameInfoList();
        GameInfo[] n = new GameInfo[original.length - 1];
        int element = -1;
        for(int i=0;i < original.length;i++) {
            if (original[i].equals(gameInfo)) {
                element = i;
            }
        }

        System.arraycopy(original, 0, n, 0, element );
        System.arraycopy(original, element+1, n, element, original.length - element-1);
        CD.setGameInfoList(n);

        return true;
    }
    public boolean joinGame(Game game){
        ClientData.getInstance().setGame(game);
        return true;
    }
}
