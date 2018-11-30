package com.thePollerServer.services;

import com.shared.exceptions.CommandFailed;
import com.shared.exceptions.NoCardToDrawException;
import com.shared.exceptions.StateException;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Chat;


import com.shared.models.EndGameResult;
import com.shared.models.PlayerScore;


import com.shared.models.Route;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.cardsHandsDecks.TrainCard;
import com.shared.models.states.GameState;
import com.thePollerServer.Model.ServerData;
import com.thePollerServer.Model.ServerGame;
import com.shared.models.ServerPlayer;

import com.thePollerServer.utilities.LongestRouteCalculator;
import com.thePollerServer.utilities.RouteCalculator;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
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

    ServerData model = ServerData.instance();

    /**
     * Done for refactor
     * @param chat
     * @param gameInfo
     * @throws DatabaseException
     */
    public void chat(Chat chat, GameInfo gameInfo) throws DatabaseException
    {
        //df.chat(chat, gameInfo);
        ServerGame game = model.getGame(gameInfo);
        game.addChat(chat);
    }


    //returns true if all
    /*
     *
     */
    public boolean discardDestinationCards(Player p, List<DestinationCard> discards) throws CommandFailed
    {
        int number = discards.size();
        ServerGame game = model.getGame(p);
        ServerPlayer player = game.getPlayer(p);
        if(  game.discardDestinationCards(player, discards))
        {
            GameState gs = game.getGameState();
            String turn = gs.getTurn();

            //update the game state after discarding
            if(turn == null ){  //if before game starts
                game.updatePreGameState(player);
            }else{  //else during a normal turn
                GameState newState = new GameState(getNextPlayer(p), NO_ACTION_TAKEN);
                game.setGameState(newState);
            }

            return true;
        }
        return false;
    }

    /**
     * the int is the remaining number of draws
     */
    public TrainCard drawVisible(Player p, int i) throws StateException, NoCardToDrawException
    {
        GameInfo info = model.getMyGame(p);
        ServerGame game = model.getGame(info);
        GameState gs = game.getGameState();
        boolean isRainbow = game.getVisibleCards().get(i).getColor().equals(RAINBOW);
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

        ServerPlayer real = game.getPlayer(p);
        TrainCard card = game.drawVisible(real,i);

        //determine the next state
        if(isRainbow || gs.getState().equals(DRAWN_ONE)){
            GameState newGameState = new GameState(getNextPlayer(p), NO_ACTION_TAKEN);
            game.setGameState(newGameState);
        }else{
            GameState newGameState = new GameState(gs.getTurn(), DRAWN_ONE);
            game.setGameState(newGameState);
        }

        return card;
    }


    private String getNextPlayer(Player p)
    {
        ServerGame game = model.getGame(p);
        boolean useNext = false;
        for(ServerPlayer player: game.getPlayers())
        {
            if(useNext)
            {
                return player.getName();
            }
            if(p.equals(player))
            {
                useNext = true;
            }
        }
        return game.getPlayers().get(0).getName();
    }

    public List<DestinationCard> drawDestinationCards(Player p) throws StateException
    {
        ServerGame game = model.getGame(p);
        ServerPlayer player = game.getPlayer(p);
        if(!game.myTurn(player)|| !game.getGameState().getState().equals(NO_ACTION_TAKEN)){
            throw new StateException("draw destination cards", game.getGameState().getTurn());
        }
        List<DestinationCard> dlist = game.drawDestinationCards(player,2) ;
        if(dlist.size() != 3)
        {
            throw new StateException("not enought destination cards", game.getGameState().getTurn());
        }
        GameState newGameState = new GameState(p.name, GameState.State.DRAWN_DEST);
        game.setGameState(newGameState);
        return dlist;
    }

    public EndGameResult checkForEndGame(Player p) {
        EndGameResult gameResult = new EndGameResult();

        String id = p.getGameId();

        ServerGame game = model.getGame(p);

        GameState gs = game.getGameState();

        // the state should have already changed to the next player's turn by this point
        int trainCount = game.getPlayer(gs.getTurn()).getTrainCount();
        System.out.println(trainCount);
        if (trainCount < 3) {
            return gameResult = endGame(game);

        }

        return null;
    }

    private EndGameResult endGame(ServerGame game)
    {
        EndGameResult gameResult = new EndGameResult();
        try
        {
            HashMap<String, PlayerScore> scores = new HashMap<>();
            HashMap<String, Integer> longestRoute = new HashMap<>();
            //initialize score map.

            for (ServerPlayer player : game.getPlayers())
            {
                scores.put(player.getName(), new PlayerScore(player.getName()));
            }
            //set the gameResult Scores.
            gameResult.setPlayerScores(new LinkedList<>(scores.values()));

            LongestRouteCalculator LRC = new LongestRouteCalculator(game.getMap().getCities());
            for (ServerPlayer player : game.getPlayers())
            {
                PlayerScore score = scores.get(player.name);
                setDestinationCardPoints(player, score, game.getMap().getRoutes());
                score.setRoutePoints(calculateRoutePoints(player));
                score.setLongestRouteScore(LRC.getLongestRoute(player));
            }

            // addBonusPoints must be called after setLongestRoute is run for every player
            gameResult.addBonusPoints();
            gameResult.totalPoints();

        }  catch (Exception e) {
            throw new RuntimeException(e.getClass() + ":" + e.getCause().toString());
        }

        return gameResult;
    }

    private PlayerScore setDestinationCardPoints(ServerPlayer p, PlayerScore score, Collection<Route> routes) {
        try
        {
            List<DestinationCard> cards = p.getDestCardHand().getDestinationCards();

            int reachedPoints = 0;
            int unreachedPoints = 0;
            for (DestinationCard card : cards)
            {
                RouteCalculator rCalc = new RouteCalculator(routes);
                boolean destinationReached = rCalc.checkDestinationReached(p,card);
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

    private int calculateRoutePoints(Player player)
    {
        ServerGame game = model.getGame(player);
        int points = 0;
        for ( Route route : game.getMap().getRoutes() )
            if(player.equals(route.getOwner())) points += route.getRouteValue();

        return points;
    }

    public TrainCard drawTrainCard(Player p) throws Exception
    {
        ServerGame game = model.getGame(p);
        ServerPlayer player = game.getPlayer(p);
        GameState gameState =game.getGameState();
        if (!game.myTurn(player))
        {
            throw new Exception("cannot draw train card if not your turn");
        }
        GameState.State state = gameState.getState();
        if (!state.equals(NO_ACTION_TAKEN) && !state.equals(DRAWN_ONE))
        {
            throw new Exception("cannot draw train card");
        }

        TrainCard card = game.drawTrainCard(player);

        //changing states
        GameState newGameState;
        if (state == NO_ACTION_TAKEN)
        {
            newGameState = new GameState(gameState.getTurn(), DRAWN_ONE);
        } else {
            newGameState = new GameState(getNextPlayer(p), NO_ACTION_TAKEN);
        }

        game.setGameState(newGameState);
        return card;
    }


    /**
     * Done for refactor.
     * @param p
     * @param r
     * @param cards
     * @return
     */
    public boolean claim(Player p, Route r, List<TrainCard> cards) throws DatabaseException
    {
        ServerGame game = model.getGame(model.getMyGame(p));
        if(!p.getName().equals(game.getGameState().getTurn()) || !game.getGameState().getState().equals(NO_ACTION_TAKEN))
        {
            throw new StateException("claim routes", game.getGameState().getTurn());
        }
        ServerPlayer player = game.getPlayer(p);
        Route real = game.getMap().getRouteById(r.toString());
        boolean claimed = player.getTrainCardHand().contains(cards);
        if(claimed && real.getOwner() == null)
        {
            for(TrainCard card: cards)
            {
               game.discardTrainCard(player, card);
            }
            real.setOwner(player);
            player.setTrainCount(player.getTrainCount() - real.getDistance());
            game.getGameState().setState(NO_ACTION_TAKEN);
            game.getGameState().setTurn(getNextPlayer(p));
        }
        else
        {
            claimed = false;
        }
        return claimed;
    }

    public void skipSecondDraw(Player p) {
        model.getGame(p).getGameState().setState(NO_ACTION_TAKEN);
        model.getGame(p).setTurn(getNextPlayer(p));
    }
}
