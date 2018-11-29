package com.thePollerServer.Model;

import com.shared.models.Chat;
import com.shared.models.ChatHistory;
import com.shared.models.Color;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Map;
import com.shared.models.Player;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.cardsHandsDecks.TrainCard;
import com.shared.models.cardsHandsDecks.VisibleCards;
import com.shared.models.states.GameState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
    public VisibleCards faceUpCards;

    private ServerGame()
    {
        faceUpCards = new VisibleCards();
        tcd = new TrainCardDeck();
        map = new Map(Map.DEFAULT_MAP);
        dcd = new DestinationCardDeck(map);
        for(int i =0; i < 5; ++i)
        {
            faceUpCards.set(i, tcd.drawCard());
        }
        gameState = new GameState();
        currentTurn = "";
    }
    /**
     *
     * @param info
     */
    public ServerGame(GameInfo info)
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
    public ServerGame(GameInfo info, Player[] players)
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
        return game;
    }

    public boolean join(Player p)
    {
        boolean canJoin = getGameInfo().getNumPlayers() <= getGameInfo().getMaxPlayers();
        if(canJoin)
        {
            _players.add(new ServerPlayer(p.getName()));
            getGameInfo().addPlayer();
        }
        return canJoin;
    }


    public TrainCard drawTrainCard(ServerPlayer player)
    {
        TrainCard drew = this.tcd.drawCard();
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

    private TrainCard drawVisible(int index)
    {
        TrainCard faceUp = faceUpCards.get(index);
        TrainCard drew = tcd.drawCard();
        faceUpCards.set(index, drew);
        //check if the visible cards are good enough
        int rainbowcount = 0;
        int allowed= 2;
        for(TrainCard card: faceUpCards.asArray())
        {
            if(card.getColor().equals(Color.TRAIN.RAINBOW))
            {
                rainbowcount+=1;
            }
        }
        if(rainbowcount >allowed)
        {
            //discard and draw.
            for(TrainCard card: faceUpCards.asArray())
            {
                tcd.discardCard(card);
            }
            for(int i =0; i < 5; ++i)
            {
                TrainCard card = tcd.drawCard();
                faceUpCards.set(i, card);//ideally we would continue to check, but i don't want to worry about infinite loops so we won't
            }
        }
        return faceUp;
    }
    public TrainCard drawVisible(ServerPlayer p, int index)
    {
        TrainCard drew= drawVisible(index);

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
            dcd.discardCard(card);
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
        return faceUpCards;
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
            Player player =p.toPlayer();
            player.setColor(Color.PLAYER.values()[i]);
            players.add(player);
            ++i;
        }
        return players;
    }
}
