package com.thePollerServer.services;

import com.shared.exceptions.NotImplementedException;
import com.shared.exceptions.ShuffleException;
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

import java.util.ArrayList;
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
                    GameState newGs = new GameState(getNextPlayer(p),GameState.State.NO_ACTION_TAKEN);
                    df.setGameState(newGs, gi);
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

    public void checkForEndGame(Player p) {
        String id = p.getGameId();
        try {
            GameInfo gi = df.getGameInfo(id);
            GameState gs = df.getGameState(gi);

            if (df.getPlayer(gs.getTurn()).getTrainCount() < 3) {
                endGame(gi);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getClass() + ":" + e.getCause().toString())
        }
    }

    private EndGameResult endGame(GameInfo gameInfo) {
        EndGameResult gameResult = new EndGameResult();
        try {

            for (Player player : df.getPlayersInGame(gameInfo)) {
                PlayerScore score = new PlayerScore();


                score = setDestinationCardPoints(player, score);
                score.setRoutePoints(calculateRoutePoints(player));
                score.setBonusAwardPoints(calculateBonusPoints(player));

                score.setTotalPoints();

                gameResult.addScore(score);
            }
        }  catch (Exception e) {
            throw new RuntimeException(e.getClass() + ":" + e.getCause().toString());
        }

        return gameResult;
    }

    private PlayerScore setDestinationCardPoints(Player p, PlayerScore score) {
        List<DestinationCard> cards = new ArrayList<DestinationCard>(); //TODO: get actual cards from database
        int reachedPoints = 0;
        int unreachedPoints = 0;
        for (DestinationCard card : cards) {
            RouteCalculator rCalc = new RouteCalculator();
        }

        score.setDestinationPoints(reachedPoints);
        score.setUnreachedDestinationPoints(unreachedPoints);
    }

//    private int calculateUnreachedDestinationPoints(Player player) {
//        return 0;
//    }
//
//    private int calculateDestinationPoints(Player player) {
//        return 0;
//    }

    private int calculateBonusPoints(Player player) {
        return 0; //TODO: calculate bonus points
    }

    private int calculateRoutePoints(Player player) {
        int points = 0;
        for (Route route : player.getRoutes())
            points += route.getDistance();

        return points;
    }

    public class Triple
    {
        public TrainCard card;
        public int drawsLeft;
        public GameInfo info;
        public TrainCard[] visible;
    }
}
