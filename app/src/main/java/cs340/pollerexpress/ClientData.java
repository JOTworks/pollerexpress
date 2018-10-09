package cs340.pollerexpress;

import com.pollerexpress.models.Authtoken;
import com.pollerexpress.models.Game;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.User;

import java.util.ArrayList;
import java.util.List;

/** This is a mastermodel. It is being observed by the
 presenters so that if there are any changes,
 they are reflected in the view.
 It contains a lot of different kinds of information.
 */
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

    public boolean gameExsists(GameInfo game){
        for(int i = 0; i<gameInfoList.size(); i++){
            if(gameInfoList.get(i).getId()==game.getId()) {
                return true;
            }
        }
        return false;
    }
    public boolean gameExsists(Game game){
        for(int i = 0; i<gameInfoList.size(); i++){
            if(gameInfoList.get(i).getId()==game.getId()) {
                return true;
            }
        }
        return false;
    }

    public boolean isMyGame(Game game){
            if(this.game.getId()==game.getId()) {
                return true;
            }
        return false;
    }
    public boolean isMyGame(GameInfo game){
        if(this.game.getId()==game.getId()) {
            return true;
        }
        return false;
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
