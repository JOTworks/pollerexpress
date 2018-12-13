package com.plugin.models;

import com.shared.models.Chat;
import com.shared.models.ChatHistory;
import com.shared.models.Color;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Map;
import com.shared.models.Player;
import com.shared.models.ServerPlayer;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.cardsHandsDecks.TrainCard;
import com.shared.models.cardsHandsDecks.VisibleCards;
import com.shared.models.states.GameState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class ServerGame extends Observable implements Serializable
{
    private final String UPDATE_ALL_STRING = "updateAll";

    GameInfo _info;
    public static final int DESTINATION_DECK_SIZE = 30;
    public static final int TRAIN_CARD_DECK_SIZE = 105;
    private GameState gameState;
    private Map map;
    public boolean started = false;
    private TrainCardDeck tcd;
    private DestinationCardDeck dcd;

    // the chat history for the game
    ChatHistory chatHistory = new ChatHistory();
    List<ServerPlayer> _players;

    //todo:make these private
    public String currentTurn; //right now is players name


    private ServerGame()
    {

        tcd = new TrainCardDeck();
        map = new Map(Map.DEFAULT_MAP);
        dcd = new DestinationCardDeck(map);

        gameState = new GameState();
        currentTurn = "";
    }
    /**
     *
     * @param info
     */
    public ServerGame(GameInfo info)   // never thrown
    {
        this();
        _info = info;
        _players = new ArrayList<>();
        switch(info.getMaxPlayers())
        {
            case 1:
                gameState.setState(GameState.State.WAITING_FOR_ONE_PLAYER);
                break;
            case 2:
                gameState.setState(GameState.State.WAITING_FOR_TWO_PLAYERS);
                break;
            case 3:
                gameState.setState(GameState.State.WAITING_FOR_THREE_PLAYERS);
                break;
            case 4:
                gameState.setState(GameState.State.WAITING_FOR_FOUR_PLAYERS);
                break;
            case 5:
                gameState.setState(GameState.State.WAITING_FOR_FIVE_PLAYERS);
        }

    }


    /**
     *
     * @param info
     * @param players
     */
    public ServerGame(GameInfo info, Player[] players)   // never thrown
    {
        this();
        map = Map.DEFAULT_MAP;
        _info = info;

        _players = new LinkedList<>();
        for(Player player: players)
        {
            _players.add(new ServerPlayer(player.getName()));
        }
    }

    /*---------------------------------------------------------------------
     *
     *  Logic
     */
    public Game toGame()
    {
        Game game = new Game(getGameInfo());
        List<Player> players = new ArrayList<>();
        for(ServerPlayer player: this._players)
        {
            players.add(player.toPlayer());
        }
        game.setPlayers(players);
        game.setDestinationDeckSize(this.dcd.size());
        game.setTrainCardDeckSize(this.tcd.size());
        game.setGameState(gameState);
        game.setVisibleCards(tcd.faceUpCards);
        game.setMap(map);
        return game;
    }

    public boolean join(Player p)
    {
        boolean canJoin = getGameInfo().getNumPlayers() <= getGameInfo().getMaxPlayers();
        System.out.print(getGameInfo().getNumPlayers());
        if(canJoin)
        {
            _players.add(new ServerPlayer(p.getName()));
            getGameInfo().addPlayer();

        }
        return canJoin;
    }


    public TrainCard drawTrainCard(ServerPlayer player) throws Exception
    {
        if (this.tcd.deck.size() == 0 && this.tcd.discard.size() == 0)
            throw new Exception("cannot draw from an empty deck when there is no discard pile");
        TrainCard drew = this.tcd.drawCard();
        if(drew == null)
        {
            return null;
        }
        player.getTrainCardHand().addToHand(drew);
        return drew;
    }


    public boolean discardTrainCard(ServerPlayer player, TrainCard card)
    {
        boolean removed = player.getTrainCardHand().removeFromHand(card);
        if(removed)
        {
            tcd.discardCard(card);
            return true;
        }
        return false;
    }


    public TrainCard drawVisible(ServerPlayer p, int index)
    {
        TrainCard drew = tcd.drawVisible(index);
        System.out.println(drew);
        if(drew == null)
        {
            return null;
        }

        p.getTrainCardHand().addToHand(drew);
        return drew;
    }

    /******************************************
     *
     * @param player
     * @return
     */

    public List<DestinationCard> drawDestinationCards( ServerPlayer player, int discards)
    {
        List<DestinationCard> cards = new ArrayList<>();
        if(dcd.discard.size() + dcd.deck.size() < 3) return cards;//not enough to draw.
        for(int i= 0; i < 3; ++i)
        {
            cards.add(dcd.drawCard());
        }
        player.getDestCardOptions().setDestinationCards(cards);
        player.setDiscards(discards);
        return cards;
    }

    public boolean discardDestinationCards(ServerPlayer player, Collection<DestinationCard> cards)
    {
        if(cards.size() > player.getDiscards())
        {
            return false;
        }
        for(DestinationCard card: cards)
        {
            player.getDestCardOptions().removeFromOptions(card);
            if( !dcd.discardCard(card))
            {
                return false;
            }
        }
        for(DestinationCard card: player.getDestCardOptions().getDestinationCards())
        {
            player.getDestCardHand().addToHand(card);
        }
        return true;
    }

    /*
     -----------------------------------------------------------------------
     QLife improvements.
     -----------------------------------------------------------------------
     */

    public boolean myTurn(ServerPlayer p)
    {
        return this.getGameState().getTurn().equals(p.getName());
    }
    /*
     -----------------------------------------------------------------------
                                  Getters & Setters
     -----------------------------------------------------------------------
     */

    public void setTurn(String name){
        this.currentTurn = name;
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
        return tcd.faceUpCards;
    }
    /**
     * initialize or change the gameState object
     * @param gameState
     */
    public void setGameState(GameState gameState)
    {
        this.gameState = gameState;
    }

    /**
     *
     * @return Returns a non linked copy of the games info
     */
    public GameInfo getGameInfo()
    {
        return (_info);
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


    public ServerPlayer getPlayer(Player p)
    {
        return _players.get( _players.indexOf(p) );
    }
    public ServerPlayer getPlayer(String name)
    {
        for (ServerPlayer p: _players
             ) {
            if(p.getName().equals(name)){
                return p;
            }
        }
        return null;
    }


    public DestinationCardDeck getDestinationDeck()
    {
        return dcd;
    }

    public TrainCardDeck getTrainCardDeck()
    {
        return tcd;
    }
    public Map getMap()
    {
        return map;
    }
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof ServerGame))
        {
            return false;
        }
        ServerGame game = (ServerGame) o;
        return _info.equals( game.getGameInfo() );
    }





    @Override
    public int hashCode()
    {
        return _info.hashCode();
    }



    @Override
    public String toString()
    {
        return _info.toString();
    }

    public List<ServerPlayer> getPlayers()
    {
        return _players;
    }

    public void setNextTurn(ServerPlayer player)
    {

    }

    public void updatePreGameState(ServerPlayer player)
    {

        switch(gameState.getState())
        {
            case WAITING_FOR_ONE_PLAYER:
                gameState.setState(GameState.State.NO_ACTION_TAKEN);
                gameState.setTurn(_players.get(0).getName());
                break;
            case WAITING_FOR_TWO_PLAYERS:
                gameState.setState(GameState.State.WAITING_FOR_ONE_PLAYER);
                break;
            case WAITING_FOR_THREE_PLAYERS:
                gameState.setState(GameState.State.WAITING_FOR_TWO_PLAYERS);
                break;
            case WAITING_FOR_FOUR_PLAYERS:
                gameState.setState(GameState.State.WAITING_FOR_THREE_PLAYERS);
                break;
            case WAITING_FOR_FIVE_PLAYERS:
                gameState.setState(GameState.State.WAITING_FOR_FOUR_PLAYERS);
        }
    }

    public List<Player> getFakePlayers()
    {
        List<Player> players = new ArrayList<>();
        int i =0;
        for( ServerPlayer p: this._players)
        {
            p.setColor(Color.PLAYER.values()[i]);
            Player player =p.toPlayer();
            //player.setColor(Color.PLAYER.values()[i]);
            players.add(player);
            ++i;
        }
        return players;
    }
}
