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

//<<<<<<< HEAD
//        Game game = new Game(info);
//        IDatabaseFacade df = Factory.createDatabaseFacade();
//        try
//        {
//            df.create(player, game);
//        }
//        catch(DatabaseException e)
//=======
        if(!model.createGame(player, info))
//>>>>>>> 8b0d0c07e6f0abecf090a37d53eaed8ed24ea3c7
        {
            throw new CommandFailed("createGame");
        }

    }


}
