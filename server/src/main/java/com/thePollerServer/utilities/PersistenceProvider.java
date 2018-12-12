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

/**
 * This class is a facade for the database.
 */
public class PersistenceProvider
{

    private int delta;
    private IDatabase db = Factory.create();

    public PersistenceProvider(int delta) throws DatabaseException
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
        try
        {
            db.startTransaction();
            db.getUserDao().addUser(user);
            db.endTransaction(true);
        } catch (IOException e) {
            throw e;
        }

    }

    /**
     * Gets a user with an updated game id and updated the user in the database
     * @param user user with an updated gameId
     * @throws IOException
     */
    public void joinGame(User user) throws IOException {

        try
        {
            db.startTransaction();
            db.getUserDao().updateUser(user);
            db.endTransaction(true);
        } catch (IOException e) {
            throw e;
        }
    }


    public void addGame(ServerGame game) throws IOException {

        try{
            db.startTransaction();
            db.getGameDao().addGame(game);
            db.endTransaction(true);
        } catch (IOException e) {
            throw e;
        }
    }

    public ArrayList<ServerGame> getGameList() throws IOException {

        try{
            db.startTransaction();
            ArrayList<ServerGame> gameList = (ArrayList<ServerGame>) db.getGameDao().getAllGames();
            db.endTransaction(true);
            return gameList;
        } catch (IOException e) {
            return new ArrayList<ServerGame>();
        }
    }

    /**
     * @param game
     * @return all commands that have yet to be saved for this game
     */
    public ArrayList<Command> getCommandList(ServerGame game) throws IOException {
        try{
            db.startTransaction();
            ArrayList<Command> commandList = (ArrayList<Command>) db.getCommandDao().getCommands(game.getId());
            db.endTransaction(true);
            return commandList;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     *
     * @param command
     * @param game
     * @throws IOException
     */
    public void addCommand(Command command, ServerGame game) throws IOException {

        try{
            db.startTransaction();
            if(getCommandList(game).size() >= delta)
            {
                // the second parameter is the game from server memory

                // write the game into the database
                db.getGameDao().updateGame(game);

                // throw away delta commands.
                db.getCommandDao().removeCommands(game.getId());
            }
            else{
                if(command.getMethodName().equals("createGame")) {
                    //don't want the create game command, change it to the join game command...
                    command.set_methodName("joinGame");
                }
                db.getCommandDao().addCommand(command, game.getId());
            }
            db.endTransaction(true);
        } catch (IOException e) {
            throw e;
        }
    }

    public void reset(){
        try {
            db.resetDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void onServerStart() throws IOException {
        ServerData SD = ServerData.instance();
        CommandFacade CF = CommandFacade.getInstance();
        CommandManager._instance().setActive(false);


        for (ServerGame game : getGameList()) {

            SD.addGame(game);
           for (Command command :getCommandList(game)) {
               try {
                   command.execute();
               } catch (CommandFailed commandFailed) {
                   commandFailed.printStackTrace();
               }

           }
        }
        CommandManager._instance().setActive(true);
        for (ServerGame game : getGameList()) {
           for (Player p : game.getPlayers())
            CF.recync(p);
        }
    }
}

