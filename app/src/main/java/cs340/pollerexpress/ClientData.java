package cs340.pollerexpress;

import com.pollerexpress.models.Authtoken;
import com.pollerexpress.models.Game;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.User;

import java.util.List;

class ClientData {
    private static final ClientData ourInstance = new ClientData();

    static ClientData getInstance() {
        return ourInstance;
    }

    private ClientData() {

    }

    private User user;
    private Authtoken auth;
    private Game game;
    private List<GameInfo> gameInfoList;

    public User getUser(){
        return user;
    }
    public Authtoken getAuth(){
        return auth;
    }
    public Game getGame(){
        return game;
    }
    public List<GameInfo> getGameInfoList(){
        return gameInfoList;
    }
    public void setUser(User user){
        this.user = user;
    }
    public void setAuth(Authtoken auth){
        this.auth = auth;
    }
    public void setGameInfoList(List<GameInfo> gameInfoList){
        this.gameInfoList = gameInfoList;
    }

}
