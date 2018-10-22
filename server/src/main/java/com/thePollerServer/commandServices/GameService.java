package com.thePollerServer.commandServices;

import com.shared.models.DestinationCard;
import com.shared.models.Player;

import java.util.List;

public class GameService
{
    //returns true if all
    public boolean discardDestinationCards(Player p, List<DestinationCard> discards)
    {
        int number = discards.size();

        return true;
    }
}
