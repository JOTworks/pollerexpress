package thePollerExpress.models;

import com.shared.models.Authtoken;

import com.shared.models.EndGameResult;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;

import thePollerExpress.communication.PollerExpress;

/**
 * This class is called "ClientData" rather than "User" in order
 * to easily distinguish it from the "User" class in the shared
 * module. Take "client" and "user" to mean the same thing.
 */
public class ClientData extends Observable
{
    private static final ClientData ourInstance = new ClientData();

    public static ClientData getInstance() {
        return ourInstance;
    }

    private final String UPDATE_ALL_STRING = "updateAll";

    private ClientData() {

        gameInfoList = new ArrayList<>();
        gameResult = new EndGameResult();
    }



    private User user = null;
    private Authtoken auth;

    private Game game = new Game(new GameInfo("bob", 3));

    private EndGameResult gameResult;

    /** This will be used to display
     * the games the client can join
     * when in the lobby. */
    private ArrayList<GameInfo> gameInfoList;
    private PollerExpress theTrain;

    //--------------------------------methods-------------------------------------------------------

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
    public EndGameResult getGameResult() { return gameResult; }

    public boolean gameExists(GameInfo game){
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
            this.setChanged();
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
        this.getGame().addPlayer(player);
        synchronized (this)
        {
           // Log.d("addPlayerToGame", "11111111111111s");

            this.setChanged();
            notifyObservers(player);
        }
    }

    public void set(PollerExpress pe)
    {
        theTrain = pe;
    }


    /**
     * Adds information about a game
     * so that it will be displayed in the lobby.
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

    public void addDestCardToHand(DestinationCard card) {
        this.user.getDestCardHand().addToHand(card);
    }

    public void addDestCardToOptions(DestinationCard card) {
        this.user.getDestCardOptions().addToOptions(card);
    }

    public void removeDestCardFromOptions(DestinationCard card) {
        this.user.getDestCardOptions().removeFromOptions(card);
    }

    public void emptyDestOptions() {
        this.user.getDestCardOptions().makeEmpty();
    }

    public void updateAll() {
        game.updateObservables();
        game.getVisibleCards().updateObservables();
        user.getTrainCardHand().updateObservables();
        user.getDestCardHand().updateObservables();
        user.getDestCardOptions().updateObservables();

        synchronized (this)
        {
            this.setChanged();
            this.notifyObservers(UPDATE_ALL_STRING);
        }
    }

    public void setGameResult(EndGameResult gameResult) {
        this.gameResult.setPlayerScores(gameResult.getPlayerScores());
    }
}
