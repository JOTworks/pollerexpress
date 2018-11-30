package com.shared.models;

import com.shared.models.cardsHandsDecks.VisibleCards;
import com.shared.models.states.GameState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class Game extends Observable implements Serializable
{
    private final String UPDATE_ALL_STRING = "updateAll";

    GameInfo _info;
    public static final int DESTINATION_DECK_SIZE = 30;
    public static final int TRAIN_CARD_DECK_SIZE = 105;
    private GameState gameState;
    private Map map;

    // the chat history for the game
    ChatHistory chatHistory = new ChatHistory();
    List<Player> _players;

    public GameHistory getGameHistory() {
        return gameHistory;
    }

    public void setGameHistory(GameHistory gameHistory) {
        this.gameHistory = gameHistory;
    }

    private GameHistory gameHistory = new GameHistory();

    //todo:make these private
    public String currentTurn; //right now is players name
    public VisibleCards faceUpCards;
    public int destinationCardDeck;
    public int trainCardDeck;

    private Game()
    {
        faceUpCards = new VisibleCards();
        destinationCardDeck = DESTINATION_DECK_SIZE;
        trainCardDeck = TRAIN_CARD_DECK_SIZE;
        map = new Map(Map.DEFAULT_MAP);
        currentTurn = "";
    }
    /**
     *
     * @param info
     */
    public Game(GameInfo info)
    {
        this();

        _info = info;
        _players = new ArrayList<>();
    }


    /**
     *
     * @param info
     * @param players
     */
    public Game(GameInfo info, Player[] players)
    {
        this();
        map = Map.DEFAULT_MAP;
        _info = info;
        _players = new LinkedList<Player>(Arrays.asList(players) );
    }
    /*
     -----------------------------------------------------------------------
                                  Getters & Setters
     -----------------------------------------------------------------------
     */

    public void setTurn(String name){
        this.currentTurn = name;
        synchronized(this)
        {
            this.setChanged();
            notifyObservers(name);
        }
    }
    public ChatHistory getChatHistory()
    {
        return chatHistory;
    }
    public void setChatHistory(ChatHistory chatHistory)
    {
        this.chatHistory = chatHistory;
    }

    public void addChat(Chat chat)
    {
        chatHistory.addChat(chat);
    }

    public VisibleCards getVisibleCards()
    {
        return faceUpCards;
    }
    /**
     * initialize or change the gameState object
     * @param gameState
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        synchronized(this)
        {
            this.setChanged();
            notifyObservers(gameState);
        }
    }

    /**
     *
     * @return Returns a non linked copy of the games info
     */
    public GameInfo getGameInfo()
    {
        return new GameInfo(_info);
    }
    /**
     * Getter for gameId
     * @return the rotation of the game this game info is connected to.
     */
    public String getId()
    {
        return _info.getId();
    }

    /**
     * Getter for game name
     * @return name of the game
     */
    public String getName()
    {
        return _info.getName();
    }

    /**
     *
     * @return number of _players in the game.
     */
    public int getNumPlayers()
    {
        return _info.getNumPlayers();//might be better to return
    }

    /**
     * Increase the record of the number of _players in a game
     */
    public void addPlayer(Player p)
    {
        if(!_players.contains(p))
        {
            _players.add(p);
            _info.addPlayer();
        }
        synchronized(this)
        {
            this.setChanged();
            notifyObservers(p);
        }
    }

    public void removePlayer(Player p)
    {
        for(int i = 0; i < this.getMaxPlayers(); ++i)
        {
            if(_players.get(i) != null && _players.get(i).equals(p) )
            {
                _players.remove(p);
                _info.removePlayer();
                synchronized(this)
                {
                    this.setChanged();
                    notifyObservers(p);
                }
            }
        }
        //could not remove player, player already in game
        //TODO: Throw an error
    }

    public GameState getGameState() {
        return gameState;
    }

    @Deprecated
    public int getPlayerDex(Player p)
    {
        return _players.indexOf(p);
    }

    public boolean hasPlayer(Player p)
    {
        return _players.contains(p);
    }

    /**
     *
     * @return The maximum _players allowed in this game
     */
    public int getMaxPlayers()
    {
        return _info.getMaxPlayers();
    }

    public Player getPlayer(Player p)
    {
        return _players.get( _players.indexOf(p) );
    }
    public Player getPlayer(String name)
    {
        for (Player p: _players
             ) {
            if(p.getName().equals(name)){
                return p;
            }
        }
        return null;
    }

    public void setPlayers(List<Player> players)
    {
        this._players = players;
    }
    public List<Player> getPlayers()
    {
        return _players;
    }

    public Map getMap()
    {
        return map;
    }
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Game))
        {
            return false;
        }
        Game game = (Game) o;
        return _info.equals( game.getGameInfo() );
    }

    public void drawDestinationCards(Player player, int number)
    {
        //TODO add check...

        //this.DestinationCardDeck -= number;
        getPlayer(player).setDestinationCardCount(getPlayer(player).destinationCardCount + number);//TODO use getter

        synchronized (this)
        {
            this.setChanged();
            this.notifyObservers(number);
        }
    }

    public void discardDestinationCards(Player player, int number)
    {
        //TODO add check...
        getPlayer(player).setDestinationCardCount(getPlayer(player).destinationCardCount - number);//TODO use getter

        synchronized (this)
        {
            this.setChanged();
            this.notifyObservers(number);
        }
    }

    public void setDestinationDeckSize(int size)
    {
        this.destinationCardDeck = size;

        synchronized (this)
        {
            this.setChanged();
            this.notifyObservers(size);
        }
    }

    public void setTrainCardDeckSize(int size)
    {
        this.trainCardDeck = size;
        synchronized (this)
        {
            this.setChanged();
            this.notifyObservers(size);
        }
    }

    public void drawTrainCard(Player player)
    {
        if (trainCardDeck != 0)
            trainCardDeck -= 1;
        getPlayer(player).setTrainCardCount(getPlayer(player).trainCardCount + 1);
        synchronized (this)
        {
            this.setChanged();
            this.notifyObservers();
        }
    }

    //public

    public void drawTrainCards(Player player, int numberOfCards)
    {
        //TODO add check...
        this.trainCardDeck -= numberOfCards;
        getPlayer(player).setTrainCardCount(getPlayer(player).trainCardCount + numberOfCards);
        synchronized (this)
        {
            this.setChanged();
            this.notifyObservers();
        }
    }

    public void drawTrainCards(int numberOfCards)
    {
        this.trainCardDeck -= numberOfCards;
        synchronized (this)
        {
            this.setChanged();
            this.notifyObservers();
        }
    }


    @Override
    public int hashCode()
    {
        return _info.hashCode();
    }

    public void updateObservables() {
        synchronized (this)
        {
            this.setChanged();
            this.notifyObservers(UPDATE_ALL_STRING);
        }
    }

    @Override
    public String toString() {
//        StringBuilder builder = new StringBuilder("");
//        builder.append("ID: ");
//        builder.append(_info.getId())
//        return super.toString();
        return _info.toString();
    }
}
