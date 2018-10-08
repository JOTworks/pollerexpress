package cs340.pollerexpress;

import com.pollerexpress.models.Authtoken;
import com.pollerexpress.models.Game;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.User;

import java.util.List;

public class ClientData {
    private static final ClientData ourInstance = new ClientData();

    public static ClientData getInstance() {
        return ourInstance;
    }

    private ClientData() {

    }

    private User user;
    private Authtoken auth;
    private Game game;
    private GameInfo[] gameInfoList;

    public User getUser(){
        return user;
    }
    public Authtoken getAuth(){
        return auth;
    }
    public Game getGame(){
        return game;
    }
    public GameInfo[] getGameInfoList(){
        return gameInfoList;
    }
    public void setUser(User user){
        this.user = user;
    }
    public void setAuth(Authtoken auth){
        this.auth = auth;
    }
    public void setGameInfoList(GameInfo[] gameInfoList){
        this.gameInfoList = gameInfoList;
    }

}
