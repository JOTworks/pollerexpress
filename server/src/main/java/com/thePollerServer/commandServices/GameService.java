package com.thePollerServer.commandServices;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Chat;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import pollerexpress.database.IDatabaseFacade;
import com.thePollerServer.utilities.Factory;

import java.util.List;



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
public class GameService
{
    public void chat(Chat chat, GameInfo gameInfo) throws DatabaseException {
        IDatabaseFacade df = Factory.createDatabaseFacade();
        //df.chat(chat, gameInfo);
    }

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
}
