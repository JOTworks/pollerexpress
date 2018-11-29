package com.thePollerServer.services;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Command;
import com.shared.exceptions.CommandFailed;
import com.shared.models.GameInfo;
import com.shared.models.Player;

public class GameLobbyService
{
    /* this does not belong in game list service */
    /*this belongs in gamelobbyservice */
    public static Command leaveGame(Player player, GameInfo info) throws CommandFailed
    {
        //TODO implement leaveGame;
        return null;
    }
}
