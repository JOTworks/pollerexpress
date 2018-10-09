package com.pollerexpress.server.commands;

import com.pollerexpress.database.exceptions.DatabaseException;
import com.pollerexpress.models.Command;
import com.pollerexpress.models.CommandFailed;
import com.pollerexpress.models.Game;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.IDatabaseFacade;
import com.pollerexpress.models.Player;
import com.pollerexpress.server.homeless.Factory;


public class SetupService
{
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

    public static void createGame(Player player, GameInfo info) throws CommandFailed
    {
        //when it creates a game it must ensure that the games id is unique... so it shouldn't depend on being able to send the information back...
        //also what do i do when a command fails..

        //do this...
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
