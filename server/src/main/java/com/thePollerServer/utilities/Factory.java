package com.thePollerServer.utilities;

import com.plugin.IDatabase;
import com.plugin.IPluginFactory;
import com.shared.exceptions.database.DatabaseException;

public class Factory
{
    static IPluginFactory myDBFactory = new IPluginFactory()
    {
        @Override
        public IDatabase create() throws DatabaseException
        {
            throw new DatabaseException("No Database Exists");
        }
    };

    public static void set(IPluginFactory factory)
    {
            myDBFactory = factory;
    }
    public static IDatabase create() throws DatabaseException
    {
        return myDBFactory.create();
    }
}
