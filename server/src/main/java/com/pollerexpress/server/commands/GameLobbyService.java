package com.pollerexpress.server.commands;

import com.pollerexpress.database.exceptions.DatabaseException;
import com.pollerexpress.models.Command;
import com.pollerexpress.models.CommandFailed;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.IDatabaseFacade;
import com.pollerexpress.models.Player;
import com.pollerexpress.server.homeless.Factory;

public class GameLobbyService
{
    /* this does not belong in game list service */
    /*this belongs in gamelobbyservice */
    public static Command leaveGame(Player player, GameInfo info) throws CommandFailed
    {
        IDatabaseFacade df  = Factory.createDatabaseFacade();
        try
        {
            df.leave(player, info);//this should always work but just in case
        }
        catch(DatabaseException e)
        {
            throw new CommandFailed("leaveGame");
        }
        return null;
    }
}
