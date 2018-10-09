package pollerexpress.database;

import com.pollerexpress.models.Authtoken;
import com.pollerexpress.models.ErrorResponse;
import com.pollerexpress.models.Game;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.IDatabaseFacade;
import com.pollerexpress.models.LoginResponse;
import com.pollerexpress.models.Player;
import com.pollerexpress.models.User;

import java.util.ArrayList;
import com.pollerexpress.database.exceptions.DataNotFoundException;
import com.pollerexpress.database.exceptions.DatabaseException;
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
                db.close(false);
                return lr;
            }


        }
        catch (DatabaseException e) {
        }
        db.close(false);
        return new LoginResponse(null,null, new ErrorResponse("Bad Password/user name", null, null));
    }

    /**
     * Registers new users in the game.
     * @param user
     * @return
     * @throws DatabaseException
     */
    public LoginResponse register(User user)
    {
        try
        {
            db.open();
            db.getUserDao().write(user);
            db.close(true);
        }
        catch(DatabaseException e)
        {
            return new LoginResponse(null, null, new ErrorResponse(String.format("%s is already used", user.name), null, null));
        }
        return this.login(user);
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
            db.getGameDao().joinGame(player, info);
            db.close(true);
            db.open();
            Game game = db.getGameDao().read(info);
            db.close(false);
            return game;
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
        db.open();
        boolean valid = db.getAuthtokenDao().validate(token);
        db.close(false);
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
}