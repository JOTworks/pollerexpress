package com.pollerexpress.server.dataGeneration;

import com.pollerexpress.database.exceptions.DatabaseException;
import com.pollerexpress.models.Game;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.IDatabaseFacade;
import com.pollerexpress.models.User;
import com.pollerexpress.server.homeless.Factory;

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
