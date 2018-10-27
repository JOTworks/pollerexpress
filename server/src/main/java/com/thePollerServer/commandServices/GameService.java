package com.thePollerServer.commandServices;

import com.shared.exceptions.CommandFailed;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Chat;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.interfaces.IDatabaseFacade;
import com.thePollerServer.utilities.Factory;

public class GameService {

    /**
     * Abby
     * (DONE) Sends the chat on to the database facade.
     * @param chat
     * @param gameInfo
     * @throws DatabaseException
     */
    public void chat(Chat chat, GameInfo gameInfo) throws CommandFailed {

        IDatabaseFacade databaseFacade = Factory.createDatabaseFacade();
        try{

            databaseFacade.addChat(chat, gameInfo);
        }
        catch (DatabaseException e) {

            throw new CommandFailed("chatCommand");
        }
    }
}
