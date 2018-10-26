package thePollerExpress.models;

import android.util.Log;

import com.shared.models.Authtoken;
import com.shared.models.ChatMessage;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;

import thePollerExpress.communication.PollerExpress;

/**
 * Who was in charge of this class?
 * This is a mastermodel. It is being observed by the
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
        chatMessageList = new ArrayList<>();
    }

    private User user;
    private Authtoken auth;
    private Game game;
    private ArrayList<GameInfo> gameInfoList;
    private PollerExpress theTrain;
    private ArrayList<ChatMessage> chatMessageList;

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

        synchronized (this)
        {
            this.game = game;
            if(this.game.getPlayers() == null)
            {
                this.game.setPlayers(new LinkedList<Player>());
            }
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


    public void addPlayerToGame(Player player)
    {
        if(this.getGame().hasPlayer(player)) return;
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
