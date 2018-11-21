package com.thePollerServer.services;

import com.shared.exceptions.ShuffleException;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Chat;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import pollerexpress.database.IDatabaseFacade;

import com.shared.models.cardsHandsDecks.TrainCard;
import com.shared.models.states.GameState;
import com.thePollerServer.utilities.Factory;

import java.util.Collections;
import java.util.List;



/*
 * In this class, you should rebuild the command and put
 * it on the command manager.
 *
 * Client-side GameService and chat(Command chatCommand) are
 * the method and class you will define with the command.
 *
 * Cool.
 *
 * Just do things the way they have been done.
 * */
public class GameService
{
    private IDatabaseFacade df = Factory.createDatabaseFacade();


    public void chat(Chat chat, GameInfo gameInfo) throws DatabaseException {
        //df.chat(chat, gameInfo);
    }


    //returns true if all
    public boolean discardDestinationCards(Player p, List<DestinationCard> discards)
    {
        int number = discards.size();
        try
        {
            int allowed = df.getPlayerDiscards(p);
            if(number<= allowed)
            {
                df.discardDestinationCard(p, discards);

                //if before game starts, else durring a normal turn
                String id = p.getGameId();
                GameInfo gi = df.getGameInfo(id);
                GameState gs = df.getGameState(gi);
                String turn = gs.getTurn();
                if(turn == null ){
                    df.updatePreGameState(df.getGameInfo(p.getGameId()));
                }else{
                    GameState gamestate = new GameState(getNextPlayer(p),GameState.State.NO_ACTION_TAKEN);
                    df.setGameState(gamestate,df.getGameInfo(p.getGameId()));
                }

                return true;
            }
        }
        catch (DatabaseException e)
        {
            //do nothing
            return false;
        }
        return false;
    }

    /**
     * the int is the remaining number of draws
     */
    public Triple drawVisible(Player player, int i) throws DatabaseException
    {

        Triple p = new Triple();
        GameInfo info = df.getGameInfo(player.getGameId());
        //TODO check if the player can draw.
        TrainCard visible = df.getVisible(player,i);
        //TODO check if the player can draw the visible card
        df.drawVisible(player, i);
        p.drawsLeft = 0;//Default
        p.card = visible;
        p.info = info;
        p.visible = df.getVisible(info);
        return p;
    }

    public void updateGameState(Player p) throws DatabaseException {
        GameState gameState = df.getGameState(df.getGameInfo(p.getGameId()));

        switch (gameState.getState()) {
            case READY_FOR_GAME_START:
                GameState newGameState = new GameState(getNextPlayer(p), GameState.State.NO_ACTION_TAKEN);
                df.setGameState(newGameState,df.getGameInfo(p.getGameId()));
                break;
        }
        // TODO: add more cases to be able to change states and switch turns
        return;
    }

    private String getNextPlayer(Player p) {
        List<Player> players;
        try {
            GameInfo info = df.getGameInfo(p.getGameId());
            players = df.getGame(info).getPlayers();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        Collections.sort(players);
        int i = players.indexOf(p);
        i += 1;
        if (i == players.size())
            i = 0;
        return players.get(i).getName();
    }

    public List<DestinationCard> drawDestinationCards(Player p) throws Exception {
        if(!p.getName().equals(df.getGameState(df.getGameInfo(p.getGameId())).getTurn()) || !df.getGameState(df.getGameInfo(p.getGameId())).getState().equals(GameState.State.NO_ACTION_TAKEN)){
            throw new Exception("cannot draw destination cards in this state");
        }
        GameInfo gi = df.getGameInfo(p.getGameId());
        int drawnumber = 3;
        if(df.getDestinationDeckSize(gi) < drawnumber) {
            System.out.println("throwing a shuffle exception!!!");
            throw new ShuffleException();
        }

        List<DestinationCard> dlist = df.drawDestinationCards(p,2) ;
        GameState newGameState = new GameState(df.getGameState(df.getGameInfo(p.getGameId())).getTurn(), GameState.State.DRAWN_DEST);
        df.setGameState(newGameState,df.getGameInfo(p.getGameId()));
        return dlist;
    }

    public class Triple
    {
        public TrainCard card;
        public int drawsLeft;
        public GameInfo info;
        public TrainCard[] visible;
    }
}
