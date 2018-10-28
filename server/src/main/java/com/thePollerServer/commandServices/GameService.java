package com.thePollerServer.commandServices;

import com.shared.exceptions.database.DatabaseException;
<<<<<<< HEAD
import com.shared.models.DestinationCard;
import com.shared.models.Player;
import com.shared.models.interfaces.IDatabaseFacade;
import com.thePollerServer.utilities.Factory;

import java.util.List;

public class GameService
{
    //returns true if all
    public boolean discardDestinationCards(Player p, List<DestinationCard> discards)
    {
        int number = discards.size();
        IDatabaseFacade df = Factory.createDatabaseFacade();
        try
        {
            int allowed = df.getPlayerDiscards(p);
            if(number<= allowed)
            {
                df.discardDestinationCard(p, discards);
                return true;
            }
        }
        catch (DatabaseException e)
        {
            //do nothing
            return false;
        }
        return false;
    }
=======
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
>>>>>>> origin/serverChatBranch
}
