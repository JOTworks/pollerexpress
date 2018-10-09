package com.pollerexpress.server;

import com.pollerexpress.database.exceptions.DatabaseException;
import com.pollerexpress.models.Command;
import com.pollerexpress.models.CommandFailed;
import com.pollerexpress.models.Game;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.IDatabaseFacade;
import com.pollerexpress.models.Player;



public class SetupService
{
    public Command joinGame(Player player, GameInfo info) throws CommandFailed
    {
        IDatabaseFacade df = Factory.createDatabaseFacade();
        try
        {
            Game game = df.join(player, info);

            //now     public Command(String className, String methodName, Class<?>[] paramTypes, Object[] paramValues) {
            if (game != null)
            {
                Class<?>[] types = {Game.class};
                Object[] params = {game};
                Command command = new Command("SomeClass", "iJoinedAGame", types, params);
                return command;
            }
        }
        catch(DatabaseException e)
        {
            throw new CommandFailed("joinGame");
        }

        //do something like this maybe?

        throw new CommandFailed("join game");
    }

    public Command createGame(Player player, GameInfo info) throws CommandFailed
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

        return new Command("GameListService", "joinGame",new Class<?>[]{Player.class,GameInfo.class}, new Object[]{player, info} );//TODO make this fit jacks design.
    }



}
