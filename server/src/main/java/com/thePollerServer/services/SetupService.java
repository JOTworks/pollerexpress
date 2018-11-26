package com.thePollerServer.services;

import com.shared.exceptions.database.DatabaseException;
import com.shared.exceptions.CommandFailed;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import pollerexpress.database.IDatabaseFacade;
import com.shared.models.Player;
import com.thePollerServer.utilities.Factory;

import java.util.List;


public class SetupService
{
    /**
     *
     * @param player
     * @param info
     * @throws CommandFailed
     */
    public static void  joinGame(Player player, GameInfo info) throws CommandFailed
    {
        IDatabaseFacade df = Factory.createDatabaseFacade();
        try
        {
            df.join(player, info);
        }
        catch(DatabaseException e)
        {
            throw new CommandFailed("joinGame");
        }
    }

    /**
     * @pre
     * @param player
     * @param info
     * @throws CommandFailed
     */
    public static void createGame(Player player, GameInfo info) throws CommandFailed
    {

        Game game = new Game(info);
        IDatabaseFacade df = Factory.createDatabaseFacade();
        try
        {
            df.create(player, game);
        }
        catch(DatabaseException e)
        {
            throw new CommandFailed("createGame");
        }

    }


}
