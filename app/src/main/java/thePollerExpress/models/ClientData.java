package thePollerExpress.models;

import android.util.Log;

import com.shared.models.Authtoken;
import com.shared.models.ChatHistory;

import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Map;
import com.shared.models.Player;
import com.shared.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;

import thePollerExpress.communication.PollerExpress;

/**
<<<<<<< HEAD
 * Who was in charge of this class?
 * This is a mastermodel. It is being observed by the
 presenters so that if there are any changes,
 they are reflected in the view.
 It contains a lot of different kinds of information.
=======
 * This class is called "ClientData" rather than "User" in order
 * to easily distinguish it from the "User" class in the shared
 * module. Take "client" and "user" to mean the same thing.
 *
 * This class contains user information, including information
 * about all the games the user is a part of and the chat history
 * for each of those games.
>>>>>>> origin/serverChatBranch
 */
public class ClientData extends Observable
{
    private static final ClientData ourInstance = new ClientData();

    public static ClientData getInstance() {
        return ourInstance;
    }

    private ClientData() {

        gameInfoList = new ArrayList<>();

        map = Map.DEFAULT_MAP;

    }

    public Map map;
    private User user;
    private Authtoken auth;

    /** Represents the user's currently active game.
     * That is, either the game they are currently playing
     * or the game they are in the process of joining.
     */
    private Game game;

    /** Information about each game the client is part of. */
    private ArrayList<GameInfo> gameInfoList;
    private PollerExpress theTrain;

    /** Maps every gameInfo to its chat history */
    /*i think unesseary becasue chat history is now in the game object
    private HashMap<GameInfo, ChatHistory> gameInfoChatHistoryMap = new HashMap<>();

    public HashMap<GameInfo, ChatHistory> getGameInfoChatHistoryMap() {
        return gameInfoChatHistoryMap;
    }*/

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

<<<<<<< HEAD

    public boolean gameExsists(GameInfo game){
=======
    public boolean gameExists(GameInfo game){
>>>>>>> origin/serverChatBranch
        for(int i = 0; i<gameInfoList.size(); i++){
            if(gameInfoList.get(i).getId()==game.getId()) {
                return true;
            }
        }
        return false;
    }
    public boolean gameExists(Game game){
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

    /** Adds the given player (we're expecting that player
     * to be the user) to the current game
     * @param player
     */
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

    /*This method is a bit nondescript. What does it do?*/
    public void set(PollerExpress pe)
    {
        theTrain = pe;
    }

    /**
     * Adds information about a game to the
     * client's list of information about the
     * games the client is in.
     * @param info information about a particular game
     */
    public void addGame(GameInfo info)
    {

        synchronized (this)
        {
            this.gameInfoList.add(info);
            this.setChanged();
            this.notifyObservers(this.gameInfoList);

        }
    }

    /**
     * Adds the player (the user) to the list of players
     * in the GameInfo.
     * @param i And index for a particular GameInfo object
     *          in a list of GameInfo objects.
     */
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
