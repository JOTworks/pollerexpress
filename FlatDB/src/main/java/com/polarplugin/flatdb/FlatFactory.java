package com.polarplugin.flatdb;

import com.plugin.IDatabase;
import com.plugin.IPluginFactory;
import com.shared.exceptions.database.DatabaseException;

public class FlatFactory implements IPluginFactory
{

    @Override
    public IDatabase create() throws DatabaseException
    {
        return new FlatDatabase();
    }
}
