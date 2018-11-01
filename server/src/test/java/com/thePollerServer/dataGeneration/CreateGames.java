package com.thePollerServer.dataGeneration;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import pollerexpress.database.IDatabaseFacade;
import com.shared.models.User;
import com.thePollerServer.utilities.Factory;

public class CreateGames
{
    public static void main(String[] argv)
    {
        IDatabaseFacade df = Factory.createDatabaseFacade();
        try
        {
            User u = new User("Torsten", "pass");
            df.register(u);
            df.create(u, new Game(new GameInfo("something", 3)));
            df.create(u, new Game(new GameInfo("somethingElse", 5)));
            df.create(u, new Game(new GameInfo("somethingWorse", 3)));
        }
        catch(DatabaseException e)
        {
            //i expect this to run ever time after the first
        }
    }
}
