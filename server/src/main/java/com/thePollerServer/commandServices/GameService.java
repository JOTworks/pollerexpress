package com.thePollerServer.commandServices;

import com.shared.exceptions.database.DatabaseException;
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
}
