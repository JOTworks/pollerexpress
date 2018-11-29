package pollerexpress.database;

import com.shared.models.Authtoken;

import com.shared.models.City;
import com.shared.models.Point;
import com.shared.models.Route;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.Chat;
import com.shared.models.cardsHandsDecks.TrainCard;
import com.shared.models.cardsHandsDecks.TrainCardHand;
import com.shared.models.reponses.ErrorResponse;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.reponses.LoginResponse;
import com.shared.models.Player;
import com.shared.models.User;

import java.util.ArrayList;
import java.util.List;

import com.shared.exceptions.database.DataNotFoundException;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.states.GameState;

import pollerexpress.database.utilities.DeckBuilder;
import pollerexpress.database.utilities.MapBuilder;

import static com.shared.models.states.GameState.State.NO_ACTION_TAKEN;
import static com.shared.models.states.GameState.State.READY_FOR_GAME_START;
import static com.shared.models.states.GameState.State.WAITING_FOR_FIVE_PLAYERS;
import static com.shared.models.states.GameState.State.WAITING_FOR_FOUR_PLAYERS;
import static com.shared.models.states.GameState.State.WAITING_FOR_ONE_PLAYER;
import static com.shared.models.states.GameState.State.WAITING_FOR_THREE_PLAYERS;
import static com.shared.models.states.GameState.State.WAITING_FOR_TWO_PLAYERS;

public class DatabaseFacade implements IDatabaseFacade
{
    Database db;
    public DatabaseFacade()
    {
        this.db = new Database();
    }

    /**
     * Log ins an existing user giving them an authtoken to use in their future transactions for this session.
     * @pre user.name user.password is a valid pair in the database
     * @post the return is successful
     * @param user an existing user in the database
     * @return the result of the login attempt
     * @throws DataNotFoundException
     */
    public LoginResponse login(User user)
    {
        try
        {
            db.open();
            User fromDb = db.getUserDao().read(user.name);
            if (user.password.equals(fromDb.password))
            {
                ArrayList<GameInfo> info = db.getGameDao().getJoinableGames();
                Authtoken auth = new Authtoken(fromDb);
                fromDb.token = auth;

                db.getAuthtokenDao().write(auth);
                LoginResponse lr = new LoginResponse(auth, info, null);
                db.close(true);
                return lr;
            }


        }
        catch (DatabaseException e)
        {
        }
        finally
        {
            db.close(false);
        }
        return new LoginResponse(null,null, new ErrorResponse("Bad Password/user name", null, null));
    }

    /**
     * Registers new users in the game.
     * @param user
     * @return
     * @throws DatabaseException
     */
    public void register(User user) throws DatabaseException
    {
        try
        {
            db.open();
            db.getUserDao().write(user);
            db.close(true);
        }
        catch(DatabaseException e)
        {
            db.close(false);
            throw e;
        }
        db.close(false);
        return;
    }

    /**
     *
     * @param player
     * @param info
     * @return
     */
    public Game join(Player player, GameInfo info) throws DatabaseException
    {
        try
        {
            db.open();
            boolean can_join = db.getGameDao().joinGame(player, info);
            db.close(can_join);
            if(can_join)
            {
                try
                {
                    db.open();
                    Game game = db.getGameDao().read(info);
                    return game;
                }
                finally
                {
                    db.close(false);
                }
            }
            throw new DatabaseException();
        }
        catch(DatabaseException e)
        {
            db.close(false);
            throw e;
        }
    }

    /**
     *
     * @param player
     * @param info
     */
    @Override
    public void leave(Player player, GameInfo info) throws DatabaseException
    {
        try
        {
            db.open();
            db.getGameDao().leaveGame(player,info);
            db.close(true);
        }
        catch(DatabaseException e)
        {
            db.close(false);
            throw e;
        }
    }

    @Override
    public void create(Player player, Game game) throws DatabaseException
    {
        try
        {
            db.open();
            db.getGameDao().write(game);
            db.close(true);
        }
        catch(DatabaseException e)
        {
            db.close(false);
            throw e;
        }
    }

    @Override
    public boolean validate(Authtoken token) throws DatabaseException
    {
        boolean valid = false;
        try
        {
            db.open();
             valid = db.getAuthtokenDao().validate(token);
        }
        finally
        {
            db.close(false);
        }
        return valid;
    }

    /**
     * Abby
     * We want to add the chat to the database.
     * @param chat
     * @param gameInfo
     * @throws DatabaseException
     */
    @Override
    public void chat(Chat chat, GameInfo gameInfo) throws DatabaseException {

        try
        {
            db.open();
        }
        finally
        {
            db.close(false);
        }
    }

    @Override
    public Game getGame(GameInfo info) throws DatabaseException
    {
        try
        {
            db.open();
            Game game = db.getGameDao().read( info.getId() );
            db.close(false);
            return game;
        }
        catch(DatabaseException e)
        {
            throw e;
        }
        finally
        {
            if(db.isOpen()){db.close(false);}
        }
    }

    @Override
    public Player getPlayer(String user) throws DatabaseException
    {
        try
        {
            db.open();
            Player player = db.getUserDao().read( user);
            return player;
        }
        catch(DatabaseException e)
        {
            throw e;
        }
        finally
        {
            db.close(false);
        }
    }

    @Override
    public GameInfo getGameInfo(String id) throws DatabaseException
    {
        try
        {
            db.open();
            GameInfo game = db.getGameDao().read(id).getGameInfo();
            return game;
        }
        catch (DatabaseException e)
        {
            throw e;
        }
        finally
        {
            db.close(false);
        }
    }

    @Override
    public Player[] getPlayersInGame(GameInfo info) throws DatabaseException
    {
        try
        {
            db.open();
            Player[] players = db.getUserDao().getPlayersInGame(info);
            return players;
        }
        finally
        {
            db.close(false);
        }
    }

    @Override
    public void decrementTrainCars(Player p, int cars) throws DatabaseException {
        try{
            db.open();
            int carCount = db.getUserDao().getPlayerTrainCars(p);
            db.getUserDao().setPlayerTrainCars(p,carCount - cars);
            db.close(true);
        } finally {
            if(db.isOpen()) {
                db.close(false);
            }
        }
    }


    @Override
    public List<DestinationCard> drawDestinationCards(Player player, int canDiscard) throws DatabaseException
    {
        try
        {
            db.open();
            GameInfo info = db.getGameDao().read(player.getGameId()).getGameInfo();

            List<DestinationCard> cards = new ArrayList<>();
            for(int i = 0; i  < 3; ++i)//TODO get rid of magic numbers
            {
                cards.add( db.getDestinationCardDao().drawCard(player) );
            }
            db.getUserDao().setPlayersDiscards(player, canDiscard);//TODO set the players discard to something.
            db.close(true);
            return cards;
        }
        finally
        {
            if(db.isOpen())
            {
                db.close(false);
            }
        }
    }

    @Override
    public void discardDestinationCard(Player player, List<DestinationCard> cards) throws DatabaseException
    {
        //TODO implement this.
        try
        {
            db.open();
            for( DestinationCard card: cards)
            {
                db.getDestinationCardDao().discardCard(player, card);
            }
            db.close(true);
        }
        finally
        {
            if(db.isOpen())
            {
                db.close(false);
            }
        }
    }


    @Override
    public int getPlayerDiscards(Player player) throws DatabaseException
    {
        try
        {
            db.open();
            return db.getUserDao().getPlayersDiscards(player);
        }
        finally
        {
            if(db.isOpen())
            {
                db.close(false);
            }
        }
    }


    @Override
    public void makeBank(GameInfo info) throws DatabaseException
    {
        try
        {
            db.open();
            DeckBuilder deckBuilder = new DeckBuilder(db);
            deckBuilder.makeBank(info);
            db.close(true);
        }
        finally
        {
            if(db.isOpen()) db.close(false);
        }
    }

    @Override
    public int getDestinationDeckSize(GameInfo gi) throws DatabaseException {
        try{
            db.open();
            int deckSize = db.getDestinationCardDao().getDeckSize(gi);
            db.close(true);
            return deckSize;
        }
        finally
        {
            if(db.isOpen()) db.close(false);
        }
    }

    @Override
    public int getTrainDeckSize(GameInfo gi) throws DatabaseException {
        try{
            db.open();
            int deckSize = db.getTrainCardDao().getDeckSize(gi);
            db.close(true);
            return deckSize;
        }
        finally
        {
            if(db.isOpen()) db.close(false);
        }
    }

    @Override
    public void shuffleDestinationDeck(GameInfo gi) throws DatabaseException {
        try{
            db.open();
            DeckBuilder deckBuilder = new DeckBuilder(db);
            deckBuilder.shuffleDestinationDeck(gi);
            db.close(true);
        }
        finally
        {
            if(db.isOpen()) db.close(false);
        }
    }

    @Override
    public void shuffleTrainDeck(GameInfo gi) throws DatabaseException {
        try{
            db.open();
            DeckBuilder deckBuilder = new DeckBuilder(db);
            deckBuilder.shuffleTrainDeck(gi);
            db.close(true);
        }
        finally
        {
            if(db.isOpen()) db.close(false);
        }
    }

    @Override
    public List<DestinationCard> getDestinationHand(Player player) throws DatabaseException {
        try{
            db.open();
            List<DestinationCard> hand = db.getDestinationCardDao().getHand(player);
            db.close(true);
            return hand;
        }
        finally
        {
            if(db.isOpen()) db.close(false);
        }
    }

    @Override
    public List<TrainCard> getTrainHand(Player player) throws DatabaseException {
        try{
            db.open();
            List<TrainCard> hand = db.getTrainCardDao().getHand(player);
            db.close(true);
            return hand;
        }
        finally
        {
            if(db.isOpen()) db.close(false);
        }
    }

    @Override
    public void resetVisible(GameInfo info) throws DatabaseException {
        try{
            db.open();
            db.getTrainCardDao().resetFaceUp(info);
            db.close(true);
        }
        finally
        {
            if(db.isOpen()) db.close(false);
        }
    }

    @Override
    public TrainCard[] getVisible(GameInfo info) throws DatabaseException
    {
        try
        {
            db.open();
            TrainCard visible[] = db.getTrainCardDao().getFaceUp(info);
            return visible;
        }
        finally
        {
            if(db.isOpen()) db.close(false);
        }
    }

    @Override
    public TrainCard getVisible(Player p, int i) throws DatabaseException
    {
        try
        {
            db.open();
            GameInfo info = db.getGameDao().read( p.getGameId() ).getGameInfo();
            TrainCard visible = db.getTrainCardDao().getFaceUp(info)[i];
            return visible;
        }
        finally
        {
            if(db.isOpen()) db.close(false);
        }
    }
    @Override
    public TrainCard drawVisible(Player p, int i) throws DatabaseException
    {
        try
        {

            GameInfo gi = getGameInfo(p.getGameId());
            db.open();
            int deckSize = db.getTrainCardDao().getDeckSize(gi);

            TrainCard visible = db.getTrainCardDao().drawFaceUp(p, i);

            db.close(true);
            return visible;
        }
        finally
        {
            if(db.isOpen()) db.close(false);
        }
    }

    public boolean isInThisGame(Player p, GameInfo game)
    {
        return true;
    }

    /**
     * Draws a given number of train cards for a given player
     * @param p the player for whom the cars are drawn
     * @param number the number of cards to draw
     * @return the list of drawn cards
     * @throws DatabaseException
     */
    @Override
    public List<TrainCard> drawTrainCards(Player p, int number) throws DatabaseException
    {
        try
        {


            GameInfo gi = getGameInfo(p.getGameId());
            db.open();
            int deckSize = db.getTrainCardDao().getDeckSize(gi);

            List<TrainCard> cards = new ArrayList<>();
            while (number > 0)
            {
                cards.add(db.getTrainCardDao().drawCard(p));
                number -= 1;
            }

            db.close(true);
            return cards;
        }
        finally
        {
            if(db.isOpen()) db.close(false);
        }
    }

    public TrainCard drawTrainCard(Player p) throws DatabaseException {
        try
        {


            GameInfo gi = getGameInfo(p.getGameId());
            db.open();
            int deckSize = db.getTrainCardDao().getDeckSize(gi);

            TrainCard card = db.getTrainCardDao().drawCard(p);

            db.close(true);
            return card;
        }
        finally
        {
            if(db.isOpen()) db.close(false);
        }
    }

    @Override
    public void setupPlayers(GameInfo game) throws DatabaseException {
        try {
            Player[] players = getPlayersInGame(game);
            db.open();
            for (Player p : players) {
                db.getUserDao().setPlayerPoints(p, 0);
                db.getUserDao().setPlayerTrainCars(p, 40);
            }
            db.close(true);
        } finally {
            if(db.isOpen()) db.close(false);
        }
    }

    @Override
    public void setPreGameState(int numPlayers, GameInfo gameInfo) throws DatabaseException
    {

        try
        {
            GameState.State state = READY_FOR_GAME_START;

            switch(numPlayers) {

                case 1 :
                    state = WAITING_FOR_ONE_PLAYER;
                    break;
                case 2 :
                    state = WAITING_FOR_TWO_PLAYERS;
                    break;
                case 3 :
                    state = WAITING_FOR_THREE_PLAYERS;
                    break;
                case 4 :
                    state = WAITING_FOR_FOUR_PLAYERS;
                    break;
                case 5 :
                    state = WAITING_FOR_FIVE_PLAYERS;
                    break;
            }

            db.open();

            db.getGameDao().updateSubState(state, gameInfo);

            db.close(true);
        }
        finally
        {
            if(db.isOpen()) db.close(false);
        }

    }

    @Override
    public void updatePreGameState(GameInfo gameInfo) throws DatabaseException {

        try{

            db.open();

            GameState.State curState = db.getGameDao().getSubState(gameInfo);

            if(curState.next().equals(READY_FOR_GAME_START)){
                Player[] Players = db.getUserDao().getPlayersInGame(gameInfo);
                db.getGameDao().updateTurn(Players[0].getName(), gameInfo);
                db.getGameDao().updateSubState(NO_ACTION_TAKEN, gameInfo);
            }else {
                db.getGameDao().updateSubState(curState.next(), gameInfo);
            }
            db.close(true);
        }
        finally
        {
            if(db.isOpen()) db.close(false);
        }
    }

    @Override
    public void setGameState(GameState gameState, GameInfo gameInfo) throws DatabaseException {

        try
        {
            db.open();

            // update the database with the name of the active player
            db.getGameDao().updateTurn(gameState.getTurn(), gameInfo);

            // update the database with a new game state
            db.getGameDao().updateSubState(gameState.getState(), gameInfo);

            db.close(true);
        }
        finally
        {
            if(db.isOpen()) db.close(false);
        }

    }

    @Override
    public void setGameState(GameState.State state, GameInfo gameInfo) throws DatabaseException {

        try
        {
            db.open();

            // update the database with a new game state
            db.getGameDao().updateSubState(state, gameInfo);


            db.close(true);
        }
        finally
        {
            if(db.isOpen()) db.close(false);
        }

    }

    @Override
    public GameState getGameState(GameInfo gameInfo) throws DatabaseException {

        try
        {
            db.open();

            GameState gameState = new GameState(db.getGameDao().getTurn(gameInfo), db.getGameDao().getSubState(gameInfo));

            db.close(true);

            return gameState;
        }
        finally
        {
            if(db.isOpen()) db.close(false);
        }

    }

    @Override
    public void setColor(Player p, int i)
    {

        try
        {
            db.open();
            db.getUserDao().setColor(p, i);
            db.close(true);
        }
        catch (DatabaseException e)
        {

        }
        finally
        {
            if(db.isOpen()) db.close(false);
        }

    }

    @Override
    public TrainCardHand getTrainHandAsHand(Player p) throws DatabaseException
    {
        try
        {
            db.open();
            TrainCardHand hand =new TrainCardHand();
            hand.setTrainCards(db.getTrainCardDao().getHand(p));
            return hand;
        }
        finally
        {
            db.close(false);
        }
    }

    @Override
    public void makeRoutes(GameInfo gi) throws DatabaseException {
        try
        {
            db.open();
            new MapBuilder(db).makeGameRoutes(gi);
            db.close(true);
        }
        finally
        {
            if(db.isOpen()){db.close(false);}
        }
    }

    @Override
    public Route getRoute(Route r) throws DatabaseException
    {
        try
        {
            db.open();
            Route route = new MapBuilder(db).getRoute(r.getId());
            return route;
        }
        finally
        {
            db.close(false);
        }
    }

    @Override
    public Route getGameRoute(Route r, GameInfo gi) throws DatabaseException {
        try
        {
            db.open();
            Route route = new MapBuilder(db).getGameRoute(r.getId(), gi);
            return route;
        }
        finally
        {
            db.close(false);
        }
    }

    @Override
    public void claimRoute(Route r, Player p) throws DatabaseException
    {
        try
        {
            db.open();
            db.getRouteDao().claimRoute(r, p);
            db.close(true);
        }
        finally
        {
            if(db.isOpen()){db.close(false);}
        }
    }
}