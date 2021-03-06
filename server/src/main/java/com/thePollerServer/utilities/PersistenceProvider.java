package com.thePollerServer.utilities;

import com.plugin.IDatabase;
import com.plugin.models.ServerGame;
import com.shared.exceptions.NotImplementedException;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Command;
import com.shared.exceptions.CommandFailed;
import com.shared.models.Player;
import com.thePollerServer.Model.ServerData;
import com.thePollerServer.command.CommandFacade;
import com.thePollerServer.command.CommandManager;
import com.shared.models.User;
import com.plugin.models.ServerGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a facade for the database.
 */
public class PersistenceProvider
{

    private int delta;
    private IDatabase db = Factory.create();

    public PersistenceProvider(int delta) throws IOException
    {
        this.delta = delta;
    }

    /**
     * Registers user
     * @param user
     * @throws IOException
     */
    public void register(User user) throws IOException
    {
        boolean commit = false;
        try
        {
            db.startTransaction();
            db.getUserDao().addUser(user);
            commit = true;
        } catch (IOException e) {
            throw e;
        }
        finally
        {
            db.endTransaction(commit);
        }

    }

    /**
     * Gets a user with an updated game id and updated the user in the database
     * @param user user with an updated gameId
     * @throws IOException
     */
    public void joinGame(User user) throws IOException {

        boolean commit = false;
        System.out.println("running the persistance provider");
        try
        {
            db.startTransaction();
            db.getUserDao().updateUser(user);
            commit = true;
        } catch (IOException e) {
            throw e;
        }
        finally
        {
            db.endTransaction(commit);
        }
    }


    public void addGame(ServerGame game) throws IOException {

        boolean commit = false;
        try
        {
            db.startTransaction();
            db.getGameDao().addGame(game);
            commit = true;
        } catch (IOException e) {
            throw e;
        }
        finally
        {
            db.endTransaction(commit);
        }
    }

    public ArrayList<ServerGame> getGameList() throws IOException {
        boolean commit = false;
        try{
            db.startTransaction();
            ArrayList<ServerGame> gameList = (ArrayList<ServerGame>) db.getGameDao().getAllGames();
            commit = true;
            return gameList;
        } catch (IOException e) {
            return new ArrayList<ServerGame>();
        }
        finally
        {
            db.endTransaction(commit);
        }
    }

    /**
     * @param game
     * @return all commands that have yet to be saved for this game
     */
    public List<Command> getCommandList(ServerGame game) throws IOException {
        try{
            db.startTransaction();
            List<Command> commandList =  db.getCommandDao().getCommands(game.getId());

            return commandList;
        } catch (IOException e) {
            throw e;
        }
        finally
        {
            db.endTransaction(false);
        }
    }

    /**
     *
     * @param command
     * @param game
     * @throws IOException
     */
    public void addCommand(Command command, ServerGame game) throws IOException {

        boolean commit = false;
        try
        {

            db.startTransaction();
            if(db.getCommandDao().getCommands(game.getId()).size() >= delta)
            {
                // the second parameter is the game from server memory
                // write the game into the database
                db.getGameDao().updateGame(game);
                db.getCommandDao().removeCommands(game.getId());
                commit = true;
            }
            else
                {
                if(command.getMethodName().equals("createGame"))
                {
                    //don't want the create game command, change it to the join game command...
                    command.set_methodName("joinGame");
                }
                db.getCommandDao().addCommand(command, game.getId());
                commit = true;
            }

        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            db.endTransaction(commit);
        }
    }

    public void reset(){
        try {
            db.resetDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteGame(ServerGame game) throws IOException
    {
        boolean commit = false;
        try
        {
            db.startTransaction();
            db.getGameDao().deleteGame(game);
            db.getCommandDao().removeCommands(game.getId());
        }
        finally
        {
            db.endTransaction(commit);
        }
    }

    public void onServerStart() throws IOException
    {
        ServerData SD = ServerData.instance();
        CommandFacade CF = CommandFacade.getInstance();
        CommandManager._instance().setActive(false);

        db.startTransaction();
        List<User> users = db.getUserDao().getAllUsers();
        db.endTransaction(false);

        for(User u : users)
        {
            SD.addUser(u);

        }

        CommandManager._instance().setActive(false);
        List<ServerGame> games = getGameList();
        if(!(games==null))
        {
            SD.addGames(games);
            for (ServerGame game : SD.getServerGames() )
            {
                for (Player p : game.getPlayers())
                {
                    SD.setUserGame(p, game);
                }
            }
            for (ServerGame game : getGameList()) {

                SD.addGame(game);
                List<Command> commands = getCommandList(game);
                for (Command command : commands) {
                    try {
                        command.execute();
                    } catch (CommandFailed commandFailed) {
                        commandFailed.printStackTrace();
                    }

                }
            }

            CommandManager._instance().setActive(true);

            for (ServerGame game : SD.getServerGames() )
            {
                for (Player p : game.getPlayers())
                {
                    User u = SD.getUser(p.getName());
                    u.setGameID(game.getId());
                    CF.recync(p);
                }
            }
        }




    }
}