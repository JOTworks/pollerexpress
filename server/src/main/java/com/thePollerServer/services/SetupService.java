package com.thePollerServer.services;

import com.shared.exceptions.database.DatabaseException;
import com.shared.exceptions.CommandFailed;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.thePollerServer.Model.ServerData;


public class SetupService
{
    static ServerData model = ServerData.instance();
    /**
     *
     * @param player
     * @param info
     * @throws CommandFailed
     */
    public static void  joinGame(Player player, GameInfo info) throws CommandFailed
    {
        if(!ServerData.instance().joinGame(player, info))
        {
            throw new CommandFailed("joinGame");
        }


    }

    /**
     * @pre
     * @param player
     * @param info
     * @throws CommandFailed
     */
    public static void createGame(Player player, GameInfo info) throws CommandFailed
    {

        if(!model.createGame(player, info))
        {
            throw new CommandFailed("createGame");
        }

    }


}
