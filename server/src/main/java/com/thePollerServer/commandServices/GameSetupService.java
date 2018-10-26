package com.thePollerServer.commandServices;

import com.shared.exceptions.CommandFailed;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Command;
import com.shared.models.Player;
import com.shared.models.interfaces.IDatabaseFacade;
import com.thePollerServer.utilities.Factory;

import java.sql.Timestamp;

/**
 * Abby
 * I'm not yet 100% sure about the precise purpose of this class...
 * But I think it's necessary because that's we've been writing
 * "GameSetupService" in our umls.
 */
public class GameSetupService {

    public static Command chat(Command chatCommand) throws CommandFailed
    {
        IDatabaseFacade df  = Factory.createDatabaseFacade();
        try
        {
            df.addChat(chatCommand);
        }
        catch(DatabaseException e)
        {
            throw new CommandFailed("chat");
        }
        return null;
    }

}
