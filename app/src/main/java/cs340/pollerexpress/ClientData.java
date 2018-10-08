package cs340.pollerexpress;

import com.pollerexpress.models.Authtoken;
import com.pollerexpress.models.Game;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.User;

import java.util.ArrayList;
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
    private ArrayList<GameInfo> gameInfoList;

    public User getUser(){
        return user;
    }
    public Authtoken getAuth(){
        return auth;
    }
    public Game getGame(){
        return game;
    }
    public ArrayList<GameInfo> getGameInfoList(){
        return gameInfoList;
    }
    public void setUser(User user){
        this.user = user;
    }
    public void setAuth(Authtoken auth){
        this.auth = auth;
    }
    public void setGame(Game game) { this.game = game;}
    public void setGameInfoList(ArrayList<GameInfo> gameInfoList){
        this.gameInfoList = gameInfoList;
    }

}
