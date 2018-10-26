package pollerexpress.database;

import com.shared.models.Authtoken;
import com.shared.models.DestinationCard;
import com.shared.models.reponses.ErrorResponse;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.interfaces.IDatabaseFacade;
import com.shared.models.reponses.LoginResponse;
import com.shared.models.Player;
import com.shared.models.User;

import java.util.ArrayList;
import java.util.List;

import com.shared.exceptions.database.DataNotFoundException;
import com.shared.exceptions.database.DatabaseException;
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
            db.getUserDao();//TODO set the players discard to something.
            db.close(true);
            return cards;
        } catch(DatabaseException e) {
            throw e;
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

}