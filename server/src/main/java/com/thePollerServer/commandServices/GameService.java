package com.thePollerServer.commandServices;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Chat;
import com.shared.models.GameInfo;

import pollerexpress.database.DatabaseFacade;

public class GameService {

    public void chat(Chat chat, GameInfo gameInfo) throws DatabaseException {

        DatabaseFacade databaseFacade = new DatabaseFacade();
        databaseFacade.chat(chat, gameInfo);
    }

    /*
    * In this class, you should rebuild the command and put
    * it on the command manager.
    *
    * Client-side GameService and chat(Command chatCommand) are
    * the method and class you will define with the command.
    *
    * Cool.
    *
    * Just do things the way they have been done.
    * */
}
