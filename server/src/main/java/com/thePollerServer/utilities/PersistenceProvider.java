package com.thePollerServer.utilities;

import com.plugin.IDatabase;
import com.shared.exceptions.NotImplementedException;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Command;
import com.shared.models.Game;
import com.shared.models.Player;
import com.shared.models.User;

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

    public void saveGame(Game game) throws IOException
    {

        try{
            db.startTransaction();
            db.getGameDao().updateGame(game);
            db.endTransaction(true);

        } catch (IOException e) {
            throw e;
        }
    }

    public ArrayList getPlayersInGame(Game game) throws IOException
    {

        ArrayList<Player> players = new ArrayList<>();
        try
        {
            db.startTransaction();
            players = (ArrayList<Player>) db.getGameDao().getGame(game.getId()).getPlayers();
            db.endTransaction(true);
        } catch (IOException e) {
            throw e;
        }

        return players;
    }

    public void joinGame(Player player, Game game) throws IOException
    {

        try
        {
            db.startTransaction();
            db.getGameDao().getGame(game.getId()).addPlayer(player);
            db.endTransaction(true);
        } catch (IOException e) {
            throw e;
        }
    }

    public void addGame(Game game) throws IOException
    {

        try{
            db.startTransaction();
            db.getGameDao().addGame(game);
            db.endTransaction(true);
        } catch (IOException e) {
            throw e;
        }
    }

    public ArrayList<Game> getGameList() throws IOException
    {

        try{
            db.startTransaction();
            ArrayList<Game> gameList = (ArrayList<Game>) db.getGameDao().getAllGames();
            db.endTransaction(true);
            return gameList;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * @param game
     * @return all commands that have yet to be saved for this game
     */
    public ArrayList getCommandList(Game game) throws IOException {
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
    public void addCommand(Command command, Game game) throws IOException {

        try{
            db.startTransaction();
            if(getCommandList(game).size() >= delta)
            {
                // the second parameter is the game from server memory

                // write the game into the database
                saveGame(game);

                // throw away delta commands.
                db.getCommandDao().removeCommands(game.getId());
            }
            else
                {
                db.getCommandDao().addCommand(command, game.getId());
            }
            db.endTransaction(true);
        } catch (IOException e) {
            throw e;
        }
    }

    public void onServerStart() {
        throw new NotImplementedException("PersistenceProvider::onServerStart");
    }
}
