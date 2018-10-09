package cs340.pollerexpress;

import android.util.Log;

import com.pollerexpress.models.Authtoken;
import com.pollerexpress.models.Game;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.Player;
import com.pollerexpress.models.PollResponse;
import com.pollerexpress.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/** This is a mastermodel. It is being observed by the
 presenters so that if there are any changes,
 they are reflected in the view.
 It contains a lot of different kinds of information.
 */
public class ClientData extends Observable
{
    private static final ClientData ourInstance = new ClientData();

    public static ClientData getInstance() {
        return ourInstance;
    }

    private ClientData() {

        gameInfoList = new ArrayList<>();
    }

    private User user;
    private Authtoken auth;
    private Game game;
    private ArrayList<GameInfo> gameInfoList;
    private PollerExpress theTrain;

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

    public boolean isMyGame(Game game)
    {
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

    public void setUser(User user)
    {
        this.user = user;

        synchronized (this)
        {
            notify();
        }
    }
    public void setAuth(Authtoken auth)
    {
        this.auth = auth;

        synchronized (this)
        {
            notify();
        }
    }
    public void setGame(Game game)
    {
        this.game = game;

        synchronized (this)
        {
            notify();
        }
    }

    public void setGameInfoList(ArrayList<GameInfo> gameInfoList)
    {
        this.gameInfoList = gameInfoList;

        synchronized (this)
        {
            notify();
        }
    }


    public void addPlayerToGame(Player player) {

        synchronized (this)
        {
            Log.d("addPlayerToGame", "11111111111111s");
            this.getGame().addPlayer(player);
            this.setChanged();
            notifyObservers(player);
        }
    }

    public void set(PollerExpress pe)
    {
        theTrain = pe;
    }
    public void addGame(GameInfo info)
    {

        synchronized (this)
        {
            this.gameInfoList.add(info);
            this.setChanged();
            this.notifyObservers(this.gameInfoList);

        }
    }

    public void addPlayerToGameInfo(int i)
    {
        synchronized (this)
        {
            this.getGameInfoList().get(i).addPlayer();
            this.setChanged();
            this.notifyObservers(Integer.valueOf(i));
        }
    }

}
