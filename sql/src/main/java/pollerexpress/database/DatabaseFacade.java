package pollerexpress.database;

import com.shared.exceptions.CommandFailed;
import com.shared.models.Authtoken;
import com.shared.models.ChatMessage;
import com.shared.models.Command;
import com.shared.models.reponses.ErrorResponse;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.interfaces.IDatabaseFacade;
import com.shared.models.reponses.LoginResponse;
import com.shared.models.Player;
import com.shared.models.User;

import java.util.ArrayList;
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

    /**
     * Abby
     * We want to add the chat command to the database.
     * @param chatCommand The command to add to the database.
     * @throws DatabaseException
     */
    @Override
    public void addChat(Command chatCommand) throws DatabaseException {

        /*Would we need a chat DAO?
        * Would I need to modify the Database class's
        * createTable method so that it created a table of chats?
        *
        * I'm thinking that the answer to both of those questions is "Yes."
        *
        * At this very moment, though, I'm going to leave the database
        * stuff alone and go back to working on the command stuff.*/

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

}