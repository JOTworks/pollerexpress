package pollerexpress.database;

import com.shared.exceptions.NotImplementedException;
import com.shared.models.Authtoken;

import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.Chat;
import com.shared.models.cardsHandsDecks.TrainCard;
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
            return game;
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
            db.open();
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
            db.open();
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

    @Override
    public void setPreGameState(int numPlayers) {

        GameState.State state = READY_FOR_GAME_START;

        switch(numPlayers) {

            case 1 :
                state = WAITING_FOR_ONE_PLAYER;
                break;
            case 2 :
                state = WAITING_FOR_THREE_PLAYERS;
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



        throw new NotImplementedException("DatabaseFacade.setPreGameState");
    }

    @Override
    public void setGameState(GameState gameState) {
        throw new NotImplementedException("DatabaseFacade.setGameState");

    }

    @Override
    public void setGameState(GameState.State state) {
        throw new NotImplementedException("DatabaseFacade.setGameState");
    }

    @Override
    public void updatePreGameState() {
        throw new NotImplementedException("DatabaseFacade.updatePreGameState");
    }

    @Override
    public GameState getGameState() {
        throw new NotImplementedException("DatabaseFacade.getGameState");

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
}