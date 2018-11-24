package com.thePollerServer.services;

import com.shared.exceptions.CommandFailed;
import com.shared.exceptions.ShuffleException;
import com.shared.exceptions.StateException;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Chat;
import com.shared.models.Color;
import com.shared.models.Command;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import pollerexpress.database.IDatabaseFacade;

import com.shared.models.cardsHandsDecks.TrainCard;
import com.shared.models.states.GameState;
import com.thePollerServer.utilities.Factory;

import java.util.Collections;
import java.util.List;

import static com.shared.models.Color.TRAIN.RAINBOW;
import static com.shared.models.states.GameState.State.DRAWN_ONE;
import static com.shared.models.states.GameState.State.NO_ACTION_TAKEN;


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
    public boolean discardDestinationCards(Player p, List<DestinationCard> discards) throws CommandFailed
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
                    GameState gamestate = new GameState(getNextPlayer(p),NO_ACTION_TAKEN);
                    df.setGameState(gamestate,df.getGameInfo(p.getGameId()));
                }

                return true;
            }
            else
            {
                throw new CommandFailed("discardDestinationCards", "you can only discard up to " + allowed + " cards.");
            }
        }
        catch (DatabaseException e)
        {
            //do nothing
            return false;
        }
    }

    /**
     * the int is the remaining number of draws
     */
    public TrainCard drawVisible(Player p, int i) throws DatabaseException, StateException
    {
        GameInfo info = df.getGameInfo(p.getGameId());
        GameState gs = df.getGameState(info);
        boolean isRainbow = df.getVisible(p,i).getColor().equals(RAINBOW);
        //ugly ifs to test for legality
        if(!gs.getTurn().equals(p.getName())){
            throw new StateException("draw visible card",gs.getTurn() + "'s turn");
        }
        if(!gs.getState().equals(DRAWN_ONE) && !gs.getState().equals(NO_ACTION_TAKEN)){
            throw new StateException("draw visible card",gs.getState().name());
        }
        if(gs.getState().equals(DRAWN_ONE) && isRainbow){
            throw new StateException("draw rainbow card",gs.getState().name());
        }

        TrainCard card = df.drawVisible(p,i);

        //determine the next state
        if(isRainbow || gs.getState().equals(DRAWN_ONE)){
            GameState newGameState = new GameState(getNextPlayer(p), NO_ACTION_TAKEN);
            df.setGameState(newGameState, info);
        }else{
            GameState newGameState = new GameState(gs.getTurn(), DRAWN_ONE);
            df.setGameState(newGameState, info);
        }

        return card;
    }

    public boolean checkVisibleForReset(GameInfo info) throws DatabaseException, ShuffleException {
        TrainCard[] visible = df.getVisible(info);
        int maxRainbowCount = 2; //if there are three or more, I have to get new visible cards

        int rainbowCount = 0;
        for(TrainCard card : visible) {
            if(card.getColor().equals(RAINBOW)) {
                rainbowCount += 1;
            }
        }

        if(rainbowCount > maxRainbowCount) {
            //check if I have to shuffle
            int deckSize = df.getTrainDeckSize(info);
            if(deckSize < visible.length) {
                throw new ShuffleException();
            } else {
                df.resetVisible(info);
                return true;
            }
        }
        return false;
    }

    public void updateGameState(Player p) throws DatabaseException {
        GameState gameState = df.getGameState(df.getGameInfo(p.getGameId()));

        switch (gameState.getState()) {
            case READY_FOR_GAME_START:
                GameState newGameState = new GameState(getNextPlayer(p), NO_ACTION_TAKEN);
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

    public List<DestinationCard> drawDestinationCards(Player p) throws StateException, DatabaseException {
        if(!p.getName().equals(df.getGameState(df.getGameInfo(p.getGameId())).getTurn()) || !df.getGameState(df.getGameInfo(p.getGameId())).getState().equals(NO_ACTION_TAKEN)){
            throw new StateException("draw destination cards", df.getGameState(df.getGameInfo(p.getGameId())).getState().name());
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

    public TrainCard drawTrainCard(Player p) throws Exception {
        GameState gameState = df.getGameState(df.getGameInfo(p.getGameId()));
        if(!p.getName().equals(gameState.getTurn()) ){
            throw new Exception("cannot draw train card if not your turn");
        }
        GameState.State state = gameState.getState();
        if (!state.equals(NO_ACTION_TAKEN) && !state.equals(DRAWN_ONE))
        {
            throw new Exception("cannot draw train card");
        }
        GameInfo gi = df.getGameInfo(p.getGameId());
        if(df.getDestinationDeckSize(gi) < 1) {
            System.out.println("throwing a shuffle exception!!!");
            throw new ShuffleException();
        }

        TrainCard card = df.drawTrainCard(p);

        //changing states
        GameState newGameState;
        if (state == NO_ACTION_TAKEN) {
            newGameState = new GameState(gameState.getTurn(), DRAWN_ONE);
        }else{
            newGameState = new GameState(getNextPlayer(p),NO_ACTION_TAKEN);
        }

        df.setGameState(newGameState,df.getGameInfo(p.getGameId()));
        return card;
    }


}
