package com.thePollerServer.services;

import com.shared.exceptions.CommandFailed;
import com.shared.exceptions.ShuffleException;
import com.shared.exceptions.StateException;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Chat;
import com.shared.models.EndGameResult;
import com.shared.models.PlayerScore;
import com.shared.models.Route;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import pollerexpress.database.IDatabaseFacade;

import com.shared.models.cardsHandsDecks.TrainCard;
import com.shared.models.states.GameState;
import com.thePollerServer.utilities.Factory;
import com.thePollerServer.utilities.RouteCalculator;

import java.util.ArrayList;
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
            if(number <= allowed)
            {
                df.discardDestinationCard(p, discards);


                String id = p.getGameId();
                GameInfo gi = df.getGameInfo(id);
                GameState gs = df.getGameState(gi);
                String turn = gs.getTurn();

                //update the game state after discarding
                if(turn == null ){  //if before game starts
                    df.updatePreGameState(gi);
                }else{  //else during a normal turn
                    GameState newState = new GameState(getNextPlayer(p), NO_ACTION_TAKEN);
                    df.setGameState(newState, gi);
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

    public void checkForEndGame(Player p) {
        String id = p.getGameId();
        try {
            GameInfo gi = df.getGameInfo(id);
            GameState gs = df.getGameState(gi);

            if (df.getPlayer(gs.getTurn()).getTrainCount() < 3) {
                endGame(gi);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getClass() + ":" + e.getCause().toString());
        }
    }

    private EndGameResult endGame(GameInfo gameInfo) {
        EndGameResult gameResult = new EndGameResult();
        try {

            for (Player player : df.getPlayersInGame(gameInfo)) {
                PlayerScore score = new PlayerScore(player.name);

                score = setDestinationCardPoints(player, score);
                score.setRoutePoints(calculateRoutePoints(player));

                gameResult.addScore(score);
            }

            gameResult.addBonusPoints();
            gameResult.totalPoints();

        }  catch (Exception e) {
            throw new RuntimeException(e.getClass() + ":" + e.getCause().toString());
        }

        return gameResult;
    }

    private PlayerScore setDestinationCardPoints(Player p, PlayerScore score) {
        try {
            List<DestinationCard> cards = df.getDestinationHand(p);

            int reachedPoints = 0;
            int unreachedPoints = 0;
            for (DestinationCard card : cards) {
                RouteCalculator rCalc = new RouteCalculator(p.getRoutes());
                boolean destinationReached = rCalc.checkDestinationReached(card);
                if (destinationReached)
                    reachedPoints += card.getPoints();
                else
                    unreachedPoints += card.getPoints();
            }

            score.setDestinationPoints(reachedPoints);
            score.setUnreachedDestinationPoints(unreachedPoints);
        } catch (Exception e) {
            throw new RuntimeException(e.getClass() + e.getCause().toString()); }

        return score;

    }

    private int calculateRoutePoints(Player player) {
        int points = 0;
        for (Route route : player.getRoutes())
            points += route.getDistance();

        return points;
    }

    public TrainCard drawTrainCard(Player p) throws Exception {
        GameState gameState = df.getGameState(df.getGameInfo(p.getGameId()));
        if (!p.getName().equals(gameState.getTurn())) {
            throw new Exception("cannot draw train card if not your turn");
        }
        GameState.State state = gameState.getState();
        if (!state.equals(NO_ACTION_TAKEN) && !state.equals(DRAWN_ONE)) {
            throw new Exception("cannot draw train card");
        }
        GameInfo gi = df.getGameInfo(p.getGameId());
        if (df.getDestinationDeckSize(gi) < 1) {
            System.out.println("throwing a shuffle exception!!!");
            throw new ShuffleException();
        }

        TrainCard card = df.drawTrainCard(p);

        //changing states
        GameState newGameState;
        if (state == NO_ACTION_TAKEN) {
            newGameState = new GameState(gameState.getTurn(), DRAWN_ONE);
        } else {
            newGameState = new GameState(getNextPlayer(p), NO_ACTION_TAKEN);
        }

        df.setGameState(newGameState, df.getGameInfo(p.getGameId()));
        return card;
    }

    public class Triple
    {
        public TrainCard card;
        public int drawsLeft;
        public GameInfo info;
        public TrainCard[] visible;
    }

}
