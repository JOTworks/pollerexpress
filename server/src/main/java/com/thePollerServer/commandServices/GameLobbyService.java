package com.thePollerServer.commandServices;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Command;
import com.shared.exceptions.CommandFailed;
import com.shared.models.GameInfo;
import com.shared.models.interfaces.IDatabaseFacade;
import com.shared.models.Player;
import com.thePollerServer.utilities.Factory;

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
